package com.taotao.rest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    /**
     * 使用redis增加缓存机制
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(long contentCid) {
        //首先从缓存中获取内容
        try{
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, contentCid + "");
            if(!StringUtils.isEmpty(result)){
                //把字符串转换为list返回
                return JsonUtils.jsonToList(result,TbContent.class);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        List<TbContent> tbContents = this.contentMapper.selectByExample(example);
        try {
            //把结果放入缓存逻辑
            String cacheResult = JsonUtils.objectToJson(tbContents);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY,contentCid+"",cacheResult);
        }catch (Exception err){
            err.printStackTrace();
        }
        return tbContents;
    }
}
