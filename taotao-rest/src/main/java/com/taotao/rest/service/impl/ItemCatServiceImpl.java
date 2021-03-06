package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_CAT_KEY}")
    private String REDIS_ITEM_CAT_KEY;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult=new CatResult();
        String resultJson = jedisClient.hget(REDIS_ITEM_CAT_KEY, "0");
        if(!StringUtils.isEmpty(resultJson)){
            List<CatNode> catNodes = JsonUtils.jsonToList(resultJson, CatNode.class);
            catResult.setData(catNodes);
            return catResult;
        }
        List<?> catList = getCatList(0l);
        catResult.setData(catList);
        jedisClient.hset(REDIS_ITEM_CAT_KEY,"0",JsonUtils.objectToJson(catList));
        return catResult;
    }

    /**
     *
     * 根据父分类id递归查询分类列表
     * @param parentId
     * @return
     */
    private List<?> getCatList(Long parentId) {

        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = this.itemCatMapper.selectByExample(example);

        //创建返回值对象
        List resultList=new ArrayList();
        //向list中添加数据
        int count = 0;//控制
        for(TbItemCat itemCat:tbItemCats){
            CatNode catNode=new CatNode();
            //判断是否是父节点
            if(itemCat.getIsParent()){
                if(parentId==0){
                    catNode.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                }else{
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/"+itemCat.getId()+".html");
                //递归设置
                catNode.setItem(getCatList(itemCat.getId()));
                resultList.add(catNode);
                count ++;
                //第一层只取14条记录
                if (parentId == 0 && count >=14) {
                    break;
                }
            }else{
                //如果是叶子节点，也就是递归退出的点
                resultList.add("/products/"+itemCat.getId()+".html|"+itemCat.getName()+"");

            }
        }
        return resultList;
    }
}
