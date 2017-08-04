package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niemengquan on 2017/8/4.
 */
@Controller
public class IndexController {
    /**
     * 重新映射系统首页
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 展示其他页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showpage(@PathVariable String page) {
        return page;
    }
}
