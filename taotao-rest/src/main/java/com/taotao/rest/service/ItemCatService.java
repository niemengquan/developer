package com.taotao.rest.service;

import com.taotao.rest.pojo.CatResult;

/**
 * Created by niemengquan on 2017/8/25.
 */
public interface ItemCatService {
    /**
     * 获取商品的分类列表
     * @return
     */
    CatResult getItemCatList();
}
