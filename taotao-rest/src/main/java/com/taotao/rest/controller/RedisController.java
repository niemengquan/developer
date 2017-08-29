package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niemengquan on 2017/8/28.
 */
@Controller
@RequestMapping(value = "/cache/sync")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/content/{contentCid}")
    @ResponseBody
    public TaotaoResult contentCacheSysc(@PathVariable Long contentCid){
        TaotaoResult result = this.redisService.syncTbContent(contentCid);
        return result;
    }
}
