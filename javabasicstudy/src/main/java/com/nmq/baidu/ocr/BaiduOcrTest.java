package com.nmq.baidu.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by niemengquan on 2018/1/29.
 */
public class BaiduOcrTest {
    //设置APPID/AK/SK
    public static final String APP_ID = "10761746";
    public static final String API_KEY = "WBhTrCvA5Mfbe8qPqG0dEXsG";
    public static final String SECRET_KEY = "mkP1UDqKtXqwyfqWnAkmLLqTbfpGAURV";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        /*client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理*/

        // 调用接口
//        String path = "D:\\2015\\python\\question_1.jpg";
        String path = "D:\\2015\\baidu_api\\ocr\\weibo_1.jpg";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //高精度的接口调用
        JSONObject resTwo = client.basicAccurateGeneral(path, new HashMap<String, String>());
        System.out.println(resTwo.toString(2));
    }
}
