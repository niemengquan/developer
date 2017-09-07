package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by niemengquan on 2017/9/7.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper ;
    @Value("${REIDS_USER_SESSION_KEY}")
    private String REIDS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult checkData(String checkContent, Integer type) {

        //参数有效性验证
        if(StringUtils.isEmpty(checkContent)){
            return TaotaoResult.build(400,"校验内容不能为空");
        }
        if(type==null){
            return TaotaoResult.build(400,"校验内容类型不能为空");
        }
        if(type!=1&&type!=2&&type!=3){
            return TaotaoResult.build(400,"校验内容类型错误");
        }
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //校验数据是否可用：type：1、2、3分别代表username、phone、email
        if(1==type){
            criteria.andUsernameEqualTo(checkContent);
        }else if(2==type){
            criteria.andPhoneEqualTo(checkContent);
        }else {
            criteria.andEmailEqualTo(checkContent);
        }
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if(null!=tbUsers && tbUsers.size()>0){
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码使用md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        this.userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response,String username, String password) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
        List<TbUser> tbUsers = this.userMapper.selectByExample(example);
        if(null==tbUsers||tbUsers.size()==0){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户登录信息到redis中（先清空密码）
        TbUser tbUser = tbUsers.get(0);
        tbUser.setPassword(null);
        //把用户信息写入redis
        this.jedisClient.set(REIDS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(tbUser));
        //设置redis key的过期时间
        this.jedisClient.expire(REIDS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);

        //添加写cookie的逻辑，cookie的有效期是关闭浏览器失效
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        //根据token从redis中获取用户信息
        String json = this.jedisClient.get(REIDS_USER_SESSION_KEY + ":" + token);
        if(StringUtils.isEmpty(json)){
            return TaotaoResult.build(400,"此session已经过期，请重新登录");
        }
        //更新redis key的过期时间
        this.jedisClient.expire(REIDS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);

        //返回用户信息
        return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));
    }

    @Override
    public TaotaoResult logoutByToken(String token) {
        this.jedisClient.del(REIDS_USER_SESSION_KEY + ":" + token);
        return TaotaoResult.ok("OK");
    }
}
