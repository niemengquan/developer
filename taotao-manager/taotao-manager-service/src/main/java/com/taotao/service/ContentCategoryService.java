package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
public interface ContentCategoryService {
    /**
     * 获取内容分类列表
     * @param parentId
     * @return
     */
    List<EUTreeNode> getCategoryList(Long parentId);

    /**
     * 保存一条内容分类
     * @param parentId 父id
     * @param categoryName 分类名称
     * @return 返回该内容分类的主键
     */
    TaotaoResult insertContentCategory(long parentId,String categoryName);

    /**
     * 删除指定的分类。
     * 需要判断parentid对应的记录下是否有子节点。如果没有子节点。
     * 需要把parentid对应的记录的isparent改成false。
     * @param id
     * @return
     */
    TaotaoResult deleteContentCategory(long id);

    /**
     * 根据主键更新分类
     * @param contentCategory
     * @return
     */
    TaotaoResult updateContentCategoryByPrimaryKey(TbContentCategory contentCategory);
}
