package com.taotao.service;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
public interface ContentService {

    /**
     * 保存内容分类的内容
     * @param content
     * @return
     */
    TaotaoResult insertContent(TbContent content);

    /**
     * 更具内容分类id查询该分类下的内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUiListResult getContentList(String categoryId, Integer page, Integer rows);

    /**
     * 删除执行的内容
     * @param ids
     * @return
     */
    TaotaoResult deleteContentParamsByIds(Long[] ids);

}
