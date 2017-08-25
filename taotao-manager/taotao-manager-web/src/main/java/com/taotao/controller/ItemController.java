package com.taotao.controller;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niemengquan on 2017/8/7.
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 查询商品列表信息
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUiListResult getItemList(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "30") Integer rows){
        try {
            EasyUiListResult itemList = itemService.getItemList(page, rows);
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EasyUiListResult();
    }

    /**
     * 保存一条商品信息
     * @param item
     * @param itemDesc
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem item, TbItemDesc itemDesc,String itemParams){
        TaotaoResult result=this.itemService.addItem(item,itemDesc,itemParams);
        return result;
    }


}
