package com.nmq.java8.stream;

import java.util.Arrays;
import java.util.List;

/**
 * @author niemengquan
 * @create 2019/5/28
 * @modifyUser
 * @modifyDate
 */
public class StreamTest3 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");
        list.stream().forEach(System.out::println);
    }
}
