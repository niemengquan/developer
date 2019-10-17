package com.nmq.forkjointest;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * 批量执行加法运算，
 * 利用fork join 框架对数据计算进行风阻，编程分组插入，最终获取结果
 * @author niemengquan
 * @create 2019/8/16
 * @modifyUser
 * @modifyDate
 */
public class BatchCalculateTask extends RecursiveTask<Integer>{
    /**
     * 待执行的数组
     */
    Integer[] calculates;

    public BatchCalculateTask(Integer[] calculates) {
        this.calculates = calculates;
    }

    @Override
    protected Integer compute() {
        if(calculates.length <3){
            return computeDirectly();
        } else {
            // 如果执行运算的数据大于等于三个，进行分组
            int size = calculates.length;
            BatchCalculateTask aTask = new BatchCalculateTask(Arrays.copyOfRange(calculates,0,size/2));
            BatchCalculateTask bTask = new BatchCalculateTask(Arrays.copyOfRange(calculates,size/2,size));
            //并发执行
            invokeAll(aTask,bTask);
            // 将结果汇总
            return aTask.join() + bTask.join();
        }
    }

    /**
     * 直接进行加法运算
     * @return
     */
    private Integer computeDirectly() {
        if(calculates.length>1){
            System.out.println(Thread.currentThread().getName()+"\t执行加法运算:" + calculates[0] +"+" + calculates[1] +"=" +(calculates[0] + calculates[1]));
        } else {
            System.out.println(Thread.currentThread().getName()+"\t" +calculates[0]);
        }
        return calculates.length>1? calculates[0] + calculates[1]:calculates[0];
    }
}
