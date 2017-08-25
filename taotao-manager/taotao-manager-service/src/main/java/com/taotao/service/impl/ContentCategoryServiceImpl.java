package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = this.contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList=new ArrayList<>();
        for(TbContentCategory contentCategory:tbContentCategories){
            //创建一个节点
            EUTreeNode node=new EUTreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(long parentId, String categoryName) {
        TbContentCategory contentCategory=new TbContentCategory();
        contentCategory.setName(categoryName);
        contentCategory.setParentId(parentId);
        //'状态。可选值:1(正常),2(删除)',
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);//设置为非父节点
        contentCategory.setSortOrder(1);
        Date date=new Date();
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        //保存,并回显主键信息到pojo中
        this.contentCategoryMapper.insert(contentCategory);
        //查看父节点的isParent是否为ture,如果不是true修改为true（因为它已经是父节点了）
        TbContentCategory parentCategory = this.contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
            this.contentCategoryMapper.updateByPrimaryKey(parentCategory);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContentCategory(long id) {
        long parentId= this.contentCategoryMapper.selectByPrimaryKey(id).getParentId();
        //执行删除
        this.contentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = this.contentCategoryMapper.selectByExample(example);
        if(null==list||list.size()==0){
            //没有子节点了，更新父节点的isParent为false
            TbContentCategory updateContentCategory=new TbContentCategory();
            updateContentCategory.setId(parentId);
            updateContentCategory.setIsParent(false);
            //只更新isParent属性
            this.contentCategoryMapper.updateByPrimaryKeySelective(updateContentCategory);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategoryByPrimaryKey(TbContentCategory contentCategory) {
        this.contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return TaotaoResult.ok();
    }
}
