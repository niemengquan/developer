package com.taotao.service;

/**
 * Created by niemengquan on 2017/8/25.
 */
public interface ItemParamItemService {
    /**
     * 根据商品id查询规格参数表，生成相应的html信息
     * @param itemId
     * @return
     */
    String getItemParamItemByItemId(Long itemId);
}
