package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * Created by niemengquan on 2017/8/29.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {
        if(queryString!=null){
            try {
                //中文乱码转换
                queryString=new String(queryString.getBytes("iso-8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SearchResult search = this.searchService.search(queryString, page);
        //传递参数
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",search.getPageCount());
        model.addAttribute("itemList",search.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
