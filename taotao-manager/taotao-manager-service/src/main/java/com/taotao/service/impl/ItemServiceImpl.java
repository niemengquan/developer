package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by niemengquan on 2017/8/7.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    /**
     * solr 索引增加接口
     */
    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;
    @Value("${SEARCH_ADD_URL}")
    private String SEARCH_ADD_URL;
    @Value("${SEARCH_DELETE_URL}")
    private String SEARCH_DELETE_URL;
    @Override
    public EasyUiListResult getItemList(Integer page, Integer rows) throws Exception {

        TbItemExample example=new TbItemExample();
        example.setOrderByClause("created desc");
        //设置分页
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        //取分页信息
        PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(tbItems);
        long total = pageInfo.getTotal();
        EasyUiListResult result=new EasyUiListResult(((Long)total).intValue(),tbItems);
        return result;
    }



    @Override
    public TaotaoResult addItem(TbItem item, TbItemDesc itemDesc,String itemParams) {
        //1.保存商品信息
        //其中商品的title，sell_point,price,num,barcode,image,cid等信息有页面传入
        //这里只需补全商品的其他信息
        item.setId(IDUtils.genItemId());
        // '商品状态，1-正常，2-下架，3-删除',
        item.setStatus((byte)1);
        Date date=new Date();
        item.setCreated(date);
        item.setUpdated(date);
        //把数据保存到商品信息表中
        this.itemMapper.insert(item);
        //2.保存商品描述信息
        itemDesc.setItemId(item.getId());
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //将描述信息保存到商品描述表中
        this.itemDescMapper.insert(itemDesc);
        //3.保存商品的规格信息
        TbItemParamItem itemParamItem=new TbItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        this.itemParamItemMapper.insert(itemParamItem);
        try{
            TbItemCat tbItemCat = this.itemCatMapper.selectByPrimaryKey(item.getCid());
            Item indexItem=new Item();
            indexItem.setId(item.getId()+"");
            indexItem.setTitle(item.getTitle());
            indexItem.setSellPoint(item.getSellPoint());
            indexItem.setPrice(item.getPrice());
            indexItem.setImage(item.getImage());
            indexItem.setCategoryName(tbItemCat.getName());
            indexItem.setItemDes(itemDesc.getItemDesc());
            //保存商品的索引
            Map<String, String> param=new HashMap<>();
            param.put("itemJson", JsonUtils.objectToJson(indexItem));
            HttpClientUtil.doPost(SEARCH_BASE_URL+SEARCH_ADD_URL,param);
        }catch (Exception err){
            err.printStackTrace();
        }
        return TaotaoResult.ok();
    }
    @Override
    public TaotaoResult deleteItemByIds(String[] ids) {
        for(String id:ids){
            this.itemMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        //删除对应的索引信息
        try {
            for(String id:ids){
                Map<String, String> params=new HashMap<>();
                params.put("id",id);
                HttpClientUtil.doPost(SEARCH_BASE_URL+SEARCH_DELETE_URL,params);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}
