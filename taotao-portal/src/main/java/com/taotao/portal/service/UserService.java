package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by niemengquan on 2017/9/8.
 */
public interface UserService {
    /**
     * 根据token从sso系统中获取用户的登录信息
     * @param token
     * @return
     */
    TbUser getUserByToken(String  token);

    /**
     *根据token注销登录
     * @param token
     * @return
     */
    TaotaoResult logoutByToken(String token);
}
