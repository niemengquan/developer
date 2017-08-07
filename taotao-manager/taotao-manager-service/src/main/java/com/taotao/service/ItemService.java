package com.taotao.service;

import com.taotao.common.pojo.EasyUiListResult;

/**
 * 商品列表服务接口
 * Created by niemengquan on 2017/8/7.
 */
public interface ItemService {
    /**
     * 获取商品的列表
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    EasyUiListResult getItemList(Integer page,Integer rows) throws Exception;
}
