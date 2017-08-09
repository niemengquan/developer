package com.taotao.service;

import com.taotao.pojo.TbItemCat;

import java.util.List;

/**
 * Created by niemengquan on 2017/8/9.
 */
public interface ItemCatService {
    /**
     * 根据父id获取分类
     * @param parentId
     * @return
     * @throws Exception
     */
    List<TbItemCat> getItemCatList(Long parentId) throws Exception;
}
