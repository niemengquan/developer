package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

/**
 * 调用taotao-rest服务的接口
 * Created by niemengquan on 2017/9/1.
 */
public interface ItemService {
    TbItem getItemId(long itemId);
    String getItemDescById(long itemId);
    String getItemParam(long itemId);
}
