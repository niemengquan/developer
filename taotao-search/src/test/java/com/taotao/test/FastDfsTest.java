package com.taotao.test;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by niemengquan on 2017/8/9.
 */
public class FastDfsTest {

    public static void main(String[] args) throws Exception{
    }

    @Test
    public void addDocument()throws Exception{
        //创建一个solr连接
        SolrClient solrClient=new HttpSolrClient("http://192.168.132.129:8080/solr/new_core/");
        //创建一个文档对象
        SolrInputDocument doc=new SolrInputDocument();
        doc.addField("id","taotao001");
        doc.addField("item_title","魅族手机全球第一");
        doc.addField("item_price","2499");
        // 把文档写入索引库
        solrClient.add(doc);
        //提交
        solrClient.commit();
        solrClient.close();

    }

    @Test
    public void deleteDocument()throws Exception{
        //创建一个solr连接
        SolrClient solrClient=new HttpSolrClient("http://192.168.132.129:8080/solr/new_core/");
        solrClient.deleteById("111121");
        solrClient.commit();
        solrClient.close();
    }
}
