package com.nmq.custom.classloader;

import com.nmq.custom.util.EncryptUtil;

import java.io.File;

/**
 * @author 聂孟泉
 * @create 2018-11-19
 * @modifyUser
 * @modifyDate
 */
public class RunTest {
    public static void main(String[] args) throws Exception{
        File src = new File("C:\\淘淘商城\\developer\\javabasicstudy\\target\\classes\\Car.class");
        File des = new File("D:\\2015\\des\\Car.class");
        EncryptUtil.encrypt(src, des);
    }
}
