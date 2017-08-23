package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * solr索引库的商品搜索DAO
 * Created by niemengquan on 2017/8/23.
 */
@Repository
public class SearchDaoImpl implements SearchDao{
    @Autowired
    private SolrClient solrClient;

    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        SearchResult result=new SearchResult();
        //根据查询条件搜索索引库
        QueryResponse queryResponse = solrClient.query(query);
        //获取查询的结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //获取查询结果的总条数
        result.setRecordCount(solrDocumentList.getNumFound());

        //商品列表
        List<Item> itemList=new ArrayList<>();
        //取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //从结果集中取得商品信息，并封装成为一个Item对象
        for(SolrDocument solrDocument:solrDocumentList){
            //创建一个商品对象
            Item item=new Item();
            item.setId((String) solrDocument.get("id"));
            //获取高亮显示的结果:设置title的高亮显示
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title="";
            if(list!=null&&list.size()>0){
                //说明查询命中
                title=list.get(0);
            }else {
                title=(String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSellPoint((String) solrDocument.get("item_sell_point"));
            item.setCategoryName((String) solrDocument.get("item_category_name"));
            //添加的商品列表
            itemList.add(item);
        }
        result.setItemList(itemList);
        return result;
    }


}
