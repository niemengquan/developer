package com.taotao.controller;

import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Controller
public class ItemParamItemController {
    @Autowired
    private ItemParamItemService itemParamItemService;

    @RequestMapping("/item/{itemId}")
    public String showItempParam(@PathVariable Long itemId, Model model){
        String itemParamItemShowHtml=this.itemParamItemService.getItemParamItemByItemId(itemId);
        model.addAttribute("itemParam",itemParamItemShowHtml);
        return "item";
    }
}
