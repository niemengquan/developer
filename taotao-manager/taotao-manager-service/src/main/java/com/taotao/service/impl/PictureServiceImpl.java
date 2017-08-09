package com.taotao.service.impl;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClientUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * //成功时
 {
 "error" : 0,
 "url" : "http://www.example.com/path/to/file.ext"
 }
 //失败时
 {
 "error" : 1,
 "message" : "错误信息"
 }

 * Created by niemengquan on 2017/8/9.
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;
    @Override
    public PictureResult uploadPic(MultipartFile picFile) {
        PictureResult result=new PictureResult();
        //判断图片是否为空
        if(picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片为空");
            return result;
        }
        //上传到图片服务器
        try {
            //取出图片的名字
            String originalFilename = picFile.getOriginalFilename();
            //取扩展名不要"."
            String extName=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            FastDFSClientUtils client=new FastDFSClientUtils("classpath:resources/fdfs_client.conf");
            String url = client.uploadFile(picFile.getBytes(), extName);
            //把url响应给客户端
            result.setError(0);
            result.setUrl(IMAGE_SERVER_BASE_URL+url);
        }catch (Exception err){
            err.printStackTrace();
            result.setError(1);
            result.setMessage("图片上传失败");
        }
        return result;
    }
}
