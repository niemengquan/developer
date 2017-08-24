package com.taotao.controller;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niemengquan on 2017/8/24.
 */
@Controller
@RequestMapping("/item/param/")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据商品的分类返回分类的规格模板信息
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable long itemCatId){
        TaotaoResult result = this.itemParamService.getItemParamByCid(itemCatId);
        return result;
    }

    /**
     * 保存商品规格模板
     * @param cid
     * @param paramData
     * @return
     */
    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable long cid,String paramData){
        TbItemParam itemParam=new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        TaotaoResult result = this.itemParamService.insertItemParam(itemParam);
        return result;
    }

    /**
     * 获取商品规格参数列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUiListResult getItemList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows){
        try {
            EasyUiListResult itemList = itemParamService.getItemParamList(page, rows);
            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EasyUiListResult();
    }
    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteItemParamsByIds(String[] ids){
        TaotaoResult result = this.itemParamService.deleteItemParamsByIds(ids);
        return result;
    }

    /**
     * 编辑商品规格参数
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String getItemParamEditPage(@PathVariable String id, Model model){
        TbItemParam tbItemParam=this.itemParamService.getItemParamById(id);
        model.addAttribute("tbItemParam",tbItemParam);
        return "item-param-edit";
    }
    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult insertItemParam(TbItemParam itemParam){
        TaotaoResult result = this.itemParamService.updateItemParamById(itemParam);
        return result;
    }
}
