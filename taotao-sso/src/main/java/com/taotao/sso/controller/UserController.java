package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by niemengquan on 2017/9/7.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param,@PathVariable Integer type,String callBack){
        try {
            TaotaoResult result = this.userService.checkData(param, type);
            if(callBack!=null){
                //jsonp 调用
                MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callBack);
                return  mappingJacksonValue;
            }else {
                return result;
            }
        }catch (Exception err){
           return  TaotaoResult.build(500, ExceptionUtil.getStackTrace(err));
        }
    }
    @RequestMapping("/register")
    @ResponseBody
    public TaotaoResult createUser(TbUser user){
        try{
            TaotaoResult result = this.userService.createUser(user);
            return result;
        }catch (Exception err){
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(err));
        }
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response,String username, String password){
        try {
            return this.userService.userLogin(request,response,username,password);
        }catch (Exception err){
            err.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(err));
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result;
        try {
            result=this.userService.getUserByToken(token);
        }catch (Exception err){
            err.printStackTrace();
            result=TaotaoResult.build(500,ExceptionUtil.getStackTrace(err));
        }
        return jsonpReturn(callback, result);
    }

    private Object jsonpReturn(String callback, TaotaoResult result) {
        if(StringUtils.isEmpty(callback)){
           return result;
        }else {
            //jsonp调用
            MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

    @RequestMapping("/logout/{tocken}")
    @ResponseBody
    public Object logoutByToken(@PathVariable String token,String callback){
        TaotaoResult result;
        try {
            result=this.userService.logoutByToken(token);
        }catch (Exception err){
            err.printStackTrace();
            result=TaotaoResult.build(500,ExceptionUtil.getStackTrace(err));
        }
        return jsonpReturn(callback, result);
    }
}
