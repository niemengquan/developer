package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * Created by niemengquan on 2017/8/29.
 */
public interface SearchService {
    /**
     * 调用搜索工程提供的服务执行商品的搜索
     * @param queryString
     * @param page
     * @return
     */
    SearchResult search(String queryString,int page);
}
