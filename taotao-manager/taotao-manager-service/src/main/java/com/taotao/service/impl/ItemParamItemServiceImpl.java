package com.taotao.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by niemengquan on 2017/8/25.
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Override
    public String getItemParamItemByItemId(Long itemId) {
        //根据商品id查询商品的规格参数信息
        TbItemParamItemExample example=new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = this.itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(tbItemParamItems==null||tbItemParamItems.size()==0){
            return "";
        }
        //获取商品规格信息
        TbItemParamItem itemParamItem = tbItemParamItems.get(0);
        String paramData = itemParamItem.getParamData();
        //拼接html，共页面渲染
        List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
        StringBuffer sb=new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" style=\"margin:0 auto\" width=\"80%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("    <tbody>\n");
        for(Map map:jsonList){
            sb.append("        <tr>\n");
            sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
            sb.append("        </tr>\n");
            List<Map> list2 = (List<Map>) map.get("params");
            for(Map m2:list2) {
                sb.append("        <tr>\n");
                sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                sb.append("            <td>"+m2.get("v")+"</td>\n");
                sb.append("        </tr>\n");
            }
        }
        sb.append("    </tbody>\n");
        sb.append("</table>");
        return sb.toString();
    }
}
