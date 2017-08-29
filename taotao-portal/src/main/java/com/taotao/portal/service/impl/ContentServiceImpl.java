package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niemengquan on 2017/8/28.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;
    @Override
    public String getContentListForBigAdShow() {
        //调用rest服务获取大广告位的数据
        String taotaoResultJson = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        TaotaoResult taotaoResult = TaotaoResult.formatToList(taotaoResultJson, TbContent.class);
        //获取内容列表信息
       List<TbContent> tbContentList=(List<TbContent>) taotaoResult.getData();
        List<Map> resultList = new ArrayList<>();
       //封装成jsp视图页面要求的pojo列表
       for(TbContent content:tbContentList){
           Map<String,Object> map=new HashMap<>();
           map.put("src",content.getPic());
           map.put("height", 240);
           map.put("width", 670);
           map.put("srcB",content.getPic2());
           map.put("widthB", 550);
           map.put("heightB", 240);
           map.put("href", content.getUrl());
           map.put("alt", content.getSubTitle());
            resultList.add(map);
       }
        return JsonUtils.objectToJson(resultList);
    }
}
