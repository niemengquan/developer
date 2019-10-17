package com.nmq.javaagent;

        import java.lang.instrument.Instrumentation;

/**
 * @author niemengquan
 * @create 2019/8/19
 * @modifyUser
 * @modifyDate
 */
public class HelloWorldAgent {

    public static void premain(String arg, Instrumentation instrumentation){
        System.out.println("自定义的javaagent 装载成功，参数：" +arg);
    }
}
