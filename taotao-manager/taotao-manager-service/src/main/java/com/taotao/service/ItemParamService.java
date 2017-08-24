package com.taotao.service;

import com.taotao.common.pojo.EasyUiListResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * Created by niemengquan on 2017/8/24.
 */
public interface ItemParamService {
    /**
     * 根据商品分类id查询
     * @param cid
     * @return
     */
    TaotaoResult getItemParamByCid(long cid);

    /**
     * 保存一条商品的规格参数信息
     * @param itemParam
     * @return
     */
    TaotaoResult insertItemParam(TbItemParam itemParam);

    /**
     * 查询规格参数列表：规格字段只显示分组名称
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    EasyUiListResult getItemParamList(Integer page,Integer rows) throws Exception;

    /**
     * 根据id列表批量删除规格参数
     * @param ids
     * @return
     */
    TaotaoResult deleteItemParamsByIds(String[] ids);

    /**
     * 根据id获取商品规格参数
     * @param id
     * @return
     */
    TbItemParam getItemParamById(String id);

    /**
     * 根据id更新规格参数
     * @param itemParam
     * @return
     */
    TaotaoResult updateItemParamById(TbItemParam itemParam);
}
