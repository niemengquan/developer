package com.taotao.portal.controller;

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niemengquan on 2017/8/23.
 */
@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;
    @RequestMapping("/index")
    private String showIndex(Model model){
        String contentListForBigAdShow = this.contentService.getContentListForBigAdShow();
        model.addAttribute("ad1",contentListForBigAdShow);
        return "index";
    }
}
