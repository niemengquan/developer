package com.nmq.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author niemengquan
 * @create 2019/5/8
 * @modifyUser
 * @modifyDate
 */
public class StreamTest1 {
    public static void main(String[] args) {
        Student student1 = new Student("zhangshan",90);
        Student student2 = new Student("lisi",100);
        Student student3 = new Student("wangwu",80);
        Student student4 = new Student("zhangshan",90);
        Student student5 = new Student("zhangshan",70);

        List<Student> students = Arrays.asList(student1, student2, student3, student4,student5);

        List<Student> collect = students.stream().collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("-------------------");
        // 取出所有的学生名字
        List<String> collect1 = students.stream().map(Student::getName).collect(Collectors.toList());
        System.out.println(collect1);
        System.out.println("-------------------");

        Map<String, Map<Integer, List<Student>>> collect2 = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy(student -> student.getScore())));
        System.out.println(collect2);
    }
}
