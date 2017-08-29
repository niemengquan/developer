package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by niemengquan on 2017/8/28.
 */
public interface RedisService {
    /**
     * 删除缓存中的分类内容
     * hashkey为：INDEX_CONTENT_REDIS_KEY
     * @param contentCid
     * @return
     */
    TaotaoResult syncTbContent(Long contentCid);
}