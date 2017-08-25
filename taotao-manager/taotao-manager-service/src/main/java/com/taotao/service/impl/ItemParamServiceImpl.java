package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品规格service
 * Created by niemengquan on 2017/8/24.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{
    @Autowired
    private TbItemParamMapper itemParamMapper;


    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        TbItemParamExample example=new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> tbItemParams = this.itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否有查询结果
        if(tbItemParams!=null && tbItemParams.size()>0){
            return TaotaoResult.ok(tbItemParams.get(0));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        //信息补全
        Date date=new Date();
        itemParam.setCreated(date);
        itemParam.setUpdated(date);
        //保存
        this.itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUiListResult getItemParamList(Integer page, Integer rows) throws Exception {
        TbItemParamExample example=new TbItemParamExample();
        //设置分页
        PageHelper.startPage(page,rows);
        List<TbItemParam> tbItemParams = this.itemParamMapper.selectByExampleWithBLOBsAndItemCat(example);
        //取分页信息
        PageInfo<TbItemParam> pageInfo=new PageInfo<TbItemParam>(tbItemParams);
        long total = pageInfo.getTotal();
        EasyUiListResult result=new EasyUiListResult(((Long)total).intValue(),tbItemParams);
        return result;
    }

    @Override
    public TaotaoResult deleteItemParamsByIds(String[] ids) {
        for(String id:ids){
            this.itemParamMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TbItemParam getItemParamById(String id) {
        return this.itemParamMapper.selectByPrimaryKeyWithItemCat(Long.valueOf(id));
    }

    @Override
    public TaotaoResult updateItemParamById(TbItemParam itemParam) {
        Date date=new Date();
        itemParam.setUpdated(date);
        this.itemParamMapper.updateByPrimaryKeyWithBLOBs(itemParam);
        return TaotaoResult.ok();
    }
}
