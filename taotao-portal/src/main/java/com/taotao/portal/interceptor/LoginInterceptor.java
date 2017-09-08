package com.taotao.portal.interceptor;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器
 * Created by niemengquan on 2017/9/8.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImpl userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //handler 执行之前执行

        //从cookie中获取cookie，判断用户是否登录
        String tt_token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //根据token获取用户信息
        TbUser tbUser = this.userService.getUserByToken(tt_token);
        if(null==tbUser){
            //没有用户信息，用户没有登录
            //跳转到登录页面，把用户请求的url作为参数传递给登录页面
            response.sendRedirect(this.userService.SSO_BASE_URL+this.userService.SSO_PAGE_LOGIN+"?redirect="+request.getRequestURL());
            //返回false
            return false;
        }
        //取得用户信息，放行
        //返回值决定handler是否执行。true：执行，false：不执行。
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            //handler 执行之后，返回ModelAndView之前执行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //返回ModelAndView 之后
    }
}
