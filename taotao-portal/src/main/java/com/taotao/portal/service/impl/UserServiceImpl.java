package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by niemengquan on 2017/9/8.
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_USER_TOKEN}")
    public String SSO_USER_TOKEN;
    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;
    @Value("${SSO_USER_LOGOUT}")
    public String SSO_USER_LOGOUT;


    @Override
    public TbUser getUserByToken(String token) {
        try{
            //调用taotao-sso系统根据token获取用户信息
            String userJsonStr = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(userJsonStr, TbUser.class);
            if(taotaoResult.getStatus()==200){
                TbUser user = (TbUser) taotaoResult.getData();
                return user;
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return null;
    }

    @Override
    public TaotaoResult logoutByToken(String token) {
        try{
            //调用taotao-sso系统根据token获取用户信息
            String userJsonStr = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_LOGOUT + token);
            TaotaoResult taotaoResult = TaotaoResult.format(userJsonStr);
            if(taotaoResult.getStatus()==200){
              return taotaoResult;
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return null;
    }
}
