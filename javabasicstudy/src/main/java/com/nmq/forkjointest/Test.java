package com.nmq.forkjointest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author niemengquan
 * @create 2019/8/16
 * @modifyUser
 * @modifyDate
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Integer[] calculates = new Integer[100];
        for (int i = 1;i <= calculates.length; i++){
            calculates[i-1] = i;
        }
        BatchCalculateTask batchCalculateTask = new BatchCalculateTask(calculates);
        long t1 = System.currentTimeMillis();
        ForkJoinTask<Integer> result = forkJoinPool.submit(batchCalculateTask);
        System.out.println(result.get());
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);

    }
}
