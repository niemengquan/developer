package com.taotao;

import com.taotao.common.utils.FastDFSClientUtils;

/**
 * Created by niemengquan on 2017/8/9.
 */
public class FastDFSTest {
    public static void main(String[] args) throws Exception {
        FastDFSClientUtils fastDfsClient=new FastDFSClientUtils("C:\\淘淘商城\\developer\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resources\\fdfs_client.conf");
        String result = fastDfsClient.uploadFile("C:\\Users\\admin\\Desktop\\meizu.jpg");
        System.out.println(result);
    }
}
