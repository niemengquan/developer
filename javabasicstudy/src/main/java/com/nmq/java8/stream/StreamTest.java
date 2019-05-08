package com.nmq.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stream的分组和分区操作
 * @author niemengquan
 * @create 2019/5/8
 * @modifyUser
 * @modifyDate
 */
public class StreamTest {
    public static void main(String[] args) {
        Student student1 = new Student("zhangshan",90);
        Student student2 = new Student("lisi",100);
        Student student3 = new Student("wangwu",80);
        Student student4 = new Student("zhangshan",90);

        List<Student> students = Arrays.asList(student1, student2, student3, student4);
//        students.forEach(item-> System.out.println(item.getName()));
        // 按照student的name进行分组得到Map<String,List<Student>>
//        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(item -> item.getName()));
        Map<String, List<Student>> collect = students.stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println(collect);

        // 根据分数进行分组
        Map<Integer, List<Student>> collect1 = students.stream().collect(Collectors.groupingBy(Student::getScore));
        System.out.println(collect1);

        // 求平均值
        Map<String, Double> collect3 = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.averagingLong(Student::getScore)));
        System.out.println(collect3);
        // 根据分数进行分区
        Map<Boolean, List<Student>> collect2 = students.stream().collect(Collectors.partitioningBy(item -> item.getScore() > 90));
        System.out.println(collect2);

    }
}
