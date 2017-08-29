package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by niemengquan on 2017/8/28.
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;
    @Override
    public TaotaoResult syncTbContent(Long contentCid) {
        try {
            this.jedisClient.hdel(INDEX_CONTENT_REDIS_KEY,contentCid+"");
        }catch (Exception err){
            err.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(err));
        }
        return TaotaoResult.ok();
    }
}
