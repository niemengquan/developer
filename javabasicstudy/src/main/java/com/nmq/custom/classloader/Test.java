package com.nmq.custom.classloader;

/**
 * @author 聂孟泉
 * @create 2018-11-19
 * @modifyUser
 * @modifyDate
 */
public class Test {
    public static void main(String[] args) throws Exception{
//        Class<?> car = Class.forName("Car");
//        car.newInstance();
//        System.out.println(car.getClassLoader());
        CustomClassCloader loader = new CustomClassCloader("AAAAAAAAABBBBB");
        loader.setBasPath("D:\\2015\\des\\");
        Class<?> clazz = loader.loadClass("Car"); //指定加载Car类
        System.out.println(clazz.getClassLoader());//输出加载类Car的加载器
        Car object =(Car) clazz.newInstance();//创建Car类对象，会调用构造方法
        System.out.println(object);
    }
}
