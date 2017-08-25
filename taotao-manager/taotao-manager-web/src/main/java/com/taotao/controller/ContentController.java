package com.taotao.controller;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content){
        return  this.contentService.insertContent(content);
    }

    @RequestMapping("/query/list")
    @ResponseBody
    public EasyUiListResult getContentList(String categoryId,@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows){
        EasyUiListResult contentList = this.contentService.getContentList(categoryId, page, rows);
        return contentList;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentByIds(Long[] ids){
        return this.contentService.deleteContentParamsByIds(ids);
    }

}
