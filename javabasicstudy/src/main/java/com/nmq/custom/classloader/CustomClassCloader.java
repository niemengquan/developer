package com.nmq.custom.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author 聂孟泉
 * @create 2018-11-19
 * @modifyUser
 * @modifyDate
 */
public class CustomClassCloader extends ClassLoader {
    /**
     *类加载器的名字，方便看测试结果
     */
    private String name;
    /**
     * 指定加载类的基本路径
     */
    private String basePath;
    /**
     * 加载文件的扩展名
     */
    private final  String FILETYPE = ".class";

    public CustomClassCloader(String name) {
        // 让系统类加载器成为该类加载器的父加载器
//        super();
        this.name = name;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data,0 ,data.length);
    }

    /**
     * 加载二进制文件
     * @param name 文件名，不含扩展名
     * @return
     */
    private byte[] loadClassData(String name) {
        InputStream in = null;
        byte[] data = null;
        ByteArrayOutputStream bos = null;
        try{
            name = name.replace(".","\\");
            in = new FileInputStream(basePath + name + FILETYPE);
            bos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = in.read())){
                //解密
                ch = ch^0xff;
                bos.write(ch);
            }
            data = bos.toByteArray();
        }catch (Exception err){
            err.printStackTrace();
        }
        finally {
            try {
               in.close();
               bos.close();
            }catch (Exception err){
                err.printStackTrace();
            }
        }
        return data;
    }
    public void setBasPath(String basPath) {
        this.basePath = basPath;
    }
}
