package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Controller
@RequestMapping(value = "/content/category")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EUTreeNode> resultList = this.contentCategoryService.getCategoryList(parentId);
        return resultList;
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId,@RequestParam(value = "name") String categoryName){
        return this.contentCategoryService.insertContentCategory(parentId,categoryName);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        return this.contentCategoryService.deleteContentCategory(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,@RequestParam(value = "name") String updateCategoryName){
        TbContentCategory contentCategory=new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(updateCategoryName);
        contentCategory.setUpdated(new Date());
        return this.contentCategoryService.updateContentCategoryByPrimaryKey(contentCategory);
    }

}
