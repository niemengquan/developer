package com.nmq.java8.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author niemengquan
 * @create 2019/5/9
 * @modifyUser
 * @modifyDate
 */
public class MyComparatorTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("CHINA","JAPAN","CANADA","AMERICAN");

//        Collections.sort(list, Comparator.comparingInt(String::length).reversed());
        /**
         * 类型推断失败案例
         *  Collections.sort(list, Comparator.comparingInt(item -> item.length()).reversed());
         *  reversed()方法导致的：以上代码编译报错item -> item.length()  因为编译器无法推断item的类型是String类型，
         *  原因是comparingInt的参数是 ? super T 。是T类型的父类型，编译器推断成了Object类型
         */
        Collections.sort(list, Comparator.comparingInt((String item) -> item.length()).reversed());


        System.out.println(list);
    }
}
