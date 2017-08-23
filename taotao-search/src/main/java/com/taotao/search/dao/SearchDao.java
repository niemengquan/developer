package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by niemengquan on 2017/8/23.
 */
public interface SearchDao {
    /**
     * 搜索solr索引库
     * @param query
     * @return
     * @throws Exception
     */
    SearchResult search(SolrQuery query) throws Exception;
}
