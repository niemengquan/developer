package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niemengquan on 2017/8/29.
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Value("${SEARCH_QUERY_URL}")
    private String SEARCH_QUERY_URL;
    @Override
    public SearchResult search(String queryString, int page) {
        try{
            //使用httpclient调用taotao-search服务
            Map<String, String> param=new HashMap<>();
            param.put("q",queryString);
            param.put("page",page+"");
            String resultJson = HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_QUERY_URL, param);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(resultJson, SearchResult.class);
            if(taotaoResult.getStatus()==200){
                SearchResult searchResult= (SearchResult) taotaoResult.getData();
                return  searchResult;
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return null;
    }
}
