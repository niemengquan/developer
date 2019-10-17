package com.nmq.concurrent.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * Created by niemengquan on 2018/1/10.
 */
public class DelayQueueExample {
    public static void main(String[] args) {
        DelayQueue<DelayElement> queue=new DelayQueue<DelayElement>();
        DelayElement element=new DelayElement(5000,"Cache 3 seconds");
        queue.put(element);
        element=new DelayElement(1000,"Cache 1 seconds");
        queue.put(element);
        element=new DelayElement(2000,"Cache 2 seconds");
        queue.put(element);
        element=new DelayElement(3000,"Cache 3 seconds");
        queue.put(element);
        try {
//            System.out.println(queue.poll());
            while (true)
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
