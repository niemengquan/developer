package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by niemengquan on 2017/8/7.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public EasyUiListResult getItemList(Integer page, Integer rows) throws Exception {

        TbItemExample example=new TbItemExample();
        //设置分页
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(tbItems);
        long total = pageInfo.getTotal();
        EasyUiListResult result=new EasyUiListResult(((Long)total).intValue(),tbItems);
        return result;
    }
}
