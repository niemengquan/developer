package com.taotao.common.pojo;

import java.util.List;

/**
 *easyui的datagrid的相应结果的数据格式:必须包含有total和rows属性
 *{total:”2”,rows:[{“id”:”1”,”name”,”张三”},{“id”:”2”,”name”,”李四”}]}
 * Created by niemengquan on 2017/8/7.
 */
public class EasyUiListResult {
    private Integer total;
    private List<?> rows;

    public EasyUiListResult(Integer total, List<?> rows) {
        this.total=total;
        this.rows=rows;
    }
    public EasyUiListResult(){
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
