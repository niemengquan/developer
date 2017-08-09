package com.taotao.service;

import com.taotao.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by niemengquan on 2017/8/9.
 */
public interface PictureService {
    /**
     * 上传文件
     * @param picFile
     * @return
     */
     PictureResult uploadPic(MultipartFile  picFile);
}
