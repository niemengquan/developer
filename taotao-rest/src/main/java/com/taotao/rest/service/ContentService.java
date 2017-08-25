package com.taotao.rest.service;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
public interface ContentService {

    /**
     * 根据内容分类id查询分类下的内容的列表
     * @param contentCid
     * @return
     */
    List<TbContent> getContentList(long contentCid);
}
