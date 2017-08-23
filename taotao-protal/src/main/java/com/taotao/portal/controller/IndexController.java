package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niemengquan on 2017/8/23.
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    private String showIndex(Model model){
        return "index";
    }
}
