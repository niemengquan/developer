package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public TaotaoResult insertContent(TbContent content) {
        Date date=new Date();
        content.setCreated(date);
        content.setUpdated(date);
        this.contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUiListResult getContentList(String categoryId, Integer page, Integer rows) {
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(Long.valueOf(categoryId));
        //设置分页
        PageHelper.startPage(page,rows);
        List<TbContent> tbContents = this.contentMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbContent> pageInfo=new PageInfo<TbContent>(tbContents);
        long total = pageInfo.getTotal();
        EasyUiListResult result=new EasyUiListResult(((Long)total).intValue(),tbContents);
        return result;
    }

    @Override
    public TaotaoResult deleteContentParamsByIds(Long[] ids) {
        for(Long id :ids){
            this.contentMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }
}
