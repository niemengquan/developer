package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

/**
 * Created by niemengquan on 2017/8/22.
 */
public interface SearchService {

    /**
     * 初始化淘淘商城商品的索引库
     * @return
     */
    TaotaoResult initSolrIndexFull();

    /**
     * 执行查询
     * @param queryStr 查询字符串
     * @param page 当前页数
     * @param rows
     * @return
     */
    SearchResult search(String queryStr,int page,int rows) throws Exception;

    /**
     * 保存一个商品的搜索信息
     * @param item
     * @return
     */
    TaotaoResult addSolrIndexForItem(Item item);

    TaotaoResult deleteSolrIndexById(String id);
}
