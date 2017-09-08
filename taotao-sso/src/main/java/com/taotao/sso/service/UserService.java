package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niemengquan on 2017/9/7.
 */
public interface UserService {
    /**
     * 检查数据是否可用
     * @param checkContent
     * @param type
     * @return
     */
    TaotaoResult checkData(String checkContent,Integer type);

    /**
     * 创建用户
     * @param user
     * @return
     */
    TaotaoResult createUser(TbUser user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response, String username, String password);

    /**
     * 通过token查询用户信息
     * @param token
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 根据token退出登录
     * @param token
     * @return
     */
    TaotaoResult logoutByToken(HttpServletRequest request, HttpServletResponse response,String token);
}
