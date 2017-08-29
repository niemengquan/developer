package com.taotao.service;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

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

    /**
     * 保存一条商品信息
     * @param item
     * @param itemDesc
     * @param itemParams
     * @return
     */
    TaotaoResult addItem(TbItem item, TbItemDesc itemDesc,String itemParams);

    /**
     * 根据商品的id删除对应的商品
     * @param ids
     * @return
     */
    TaotaoResult deleteItemByIds(String[] ids);
}
