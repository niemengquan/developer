package com.taotao.controller;

import com.taotao.pojo.TbItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niemengquan on 2017/8/9.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 获取分类列表
     * @param parentId
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @ResponseBody
    public List categoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId) throws Exception {
        List<Map<String,Object>> catList=new ArrayList();
        //查询数据
        List<TbItemCat> itemCatList = itemCatService.getItemCatList(parentId);
        for(TbItemCat tbItemCat:itemCatList){
            Map node=new HashMap();
            node.put("id",tbItemCat.getId());
            node.put("text",tbItemCat.getName());
            //如果父节点的话就设置为关闭状态，如果是叶子节点的话就是open状态
            node.put("state",tbItemCat.getIsParent()?"closed":"open");
            catList.add(node);
        }
        return catList;
    }
}
