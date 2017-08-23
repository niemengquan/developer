package com.taotao.search.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by niemengquan on 2017/8/22.
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 导入商品数据到索引库
     */
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems() {
        TaotaoResult result = searchService.initSolrIndexFull();
        return result;
    }

    /**
     * solr索引库的商品搜索服务
     *
     * @param queryStr
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String queryStr, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows) {
        if (StringUtils.isEmpty(queryStr)) {
            return TaotaoResult.build(400, " 查询条件不能为空");
        }
        SearchResult searchResult = null;
        try {
            //使用utf-8进行编码：解决中文乱码问题
            queryStr = new String(queryStr.getBytes("iso8859-1"), "utf-8");
            searchResult = this.searchService.search(queryStr, page, rows);
        } catch (Exception err) {
            err.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(err));
        }
        return TaotaoResult.ok(searchResult);
    }

}
