package com.taotao.controller;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by niemengquan on 2017/8/9.
 */
@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        PictureResult result = pictureService.uploadPic(uploadFile);
        //需要把java对象手工专程json数据，解决火狐浏览器不兼容的问题
        String jsonResult = JsonUtils.objectToJson(result);
        return jsonResult;
    }
}
