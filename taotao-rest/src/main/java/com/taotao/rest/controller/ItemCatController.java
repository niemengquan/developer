package com.taotao.rest.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 第一种实现
     * 返回所有的商品的分类:支持jsonp调用
     * 加上：produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8" 解决中文乱码问题
     * @param callback
     * @return
     */
    @RequestMapping(value = "/itemcat/all",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        CatResult itemCatList = this.itemCatService.getItemCatList();
        //把pojo装换为json字符串
        String json=JsonUtils.objectToJson(itemCatList);
        //拼装返回值
        String result=callback+"("+json+");";

        return result;
    }
    /**
     * 第二种实现:这种封装jsonp的调用方式是在Spring4.1的版本加入的
     * 返回所有的商品的分类:支持jsonp调用
     * 加上：produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8" 解决中文乱码问题
     * @param callback
     * @return
     */
    @RequestMapping(value = "/itemcat/list")
    @ResponseBody
    public Object getItemCatListForSpring(String callback){
        CatResult itemCatList = this.itemCatService.getItemCatList();
        MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(itemCatList);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }
}
