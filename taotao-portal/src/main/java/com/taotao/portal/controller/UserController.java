package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.portal.service.UserService;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niemengquan on 2017/9/8.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("/logout")
    public void logoutByToken(HttpServletRequest request,HttpServletResponse response){
        try{
            String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
            TaotaoResult result = this.userService.logoutByToken(token);
            response.sendRedirect(this.userService.SSO_BASE_URL+this.userService.SSO_PAGE_LOGIN);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
