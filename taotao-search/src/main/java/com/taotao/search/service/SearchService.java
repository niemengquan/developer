package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by niemengquan on 2017/8/22.
 */
public interface SearchService {

    /**
     * 初始化淘淘商城商品的索引库
     * @return
     */
    TaotaoResult initSolrIndexFull();
}
