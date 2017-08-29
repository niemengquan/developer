package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbItem;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by niemengquan on 2017/8/22.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrClient solrClient;
    @Autowired
    private SearchDao searchDao;

    @Override
    public TaotaoResult initSolrIndexFull() {

        try {
            //查询商品列表
            List<Item> items = itemMapper.getItemList();
            //把商品信息写入索引库
            for (Item item : items) {
                //创建一个SolrInputDocument 对象
                SolrInputDocument document = setSolrInputFields(item);
                //写入索引库
                solrClient.add(document);
            }
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

        return TaotaoResult.ok();
    }

    @Override
    public SearchResult search(String queryStr, int page, int rows) throws Exception {
        //创建查询对象
        SolrQuery query=new SolrQuery();
        //设置查询条件
        query.setQuery(queryStr);
        //设置分页
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_keywords");
        //设置高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\"/>");
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = searchDao.search(query);
        //计算查询结果总页数
        long recordCount = searchResult.getRecordCount();
        long pageCount=recordCount/rows;
        if(pageCount%rows >0){
            pageCount++;
        }
        searchResult.setPageCount(pageCount);
        searchResult.setCurPage(page);
        return searchResult;
    }

    @Override
    public TaotaoResult addSolrIndexForItem(Item item) {
        try {
            SolrInputDocument document = setSolrInputFields(item);
            //写入索引库
            solrClient.add(document);
            solrClient.commit();
        }catch (Exception err){
            err.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(err));
        }
        return TaotaoResult.ok();
    }

    private SolrInputDocument setSolrInputFields(Item item) {
        //创建一个SolrInputDocument 对象
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", item.getId());
        document.setField("item_title", item.getTitle());
        document.setField("item_sell_point", item.getSellPoint());
        document.setField("item_price", item.getPrice());
        document.setField("item_image", item.getImage());
        document.setField("item_category_name", item.getCategoryName());
        document.setField("item_desc", item.getItemDes());
        return document;
    }

    @Override
    public TaotaoResult deleteSolrIndexById(String id) {
        try {
            this.solrClient.deleteById(id);
            this.solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        } catch (IOException e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
