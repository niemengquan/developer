package com.nmq.concurrent.lock;

/**
 * Created by niemengquan on 2018/1/10.
 */
public class Test {
    public static void main(String[] args) {
        Buffer buffer=new Buffer(10);
        Producer producer=new Producer(buffer);
        Consumer consumer=new Consumer(buffer);
        //创建线程执行生产和消费
        for(int i=0;i<10;i++){
            new Thread(producer,"producer-"+i).start();
        }
        for(int i=0;i<3;i++){
            new Thread(consumer,"consumer-"+i).start();
        }
        System.out.println("started");
    }
}
