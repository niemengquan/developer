package com.nmq.custom.util;

import java.io.*;

/**
 * @author 聂孟泉
 * @create 2018-11-19
 * @modifyUser
 * @modifyDate
 */
public class EncryptUtil {
    public static void encrypt(File src,File des)throws Exception{
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(des);
        int temp;
        while ((temp=in.read())!=-1){
            // 加密操作，位数取反操作。0变成1，1变成0
            temp = temp^0xff;
            out.write(temp);
        }
        try {
            in.close();
            out.close();
        }catch (Exception err){
            err.printStackTrace();
        }

    }
}
