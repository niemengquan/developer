package com.nmq.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * 模拟生产和消费的对象
 * Created by niemengquan on 2018/1/10.
 */
public class Buffer {
    private int maxSize;
    private LinkedList<String> taskQueue;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.taskQueue = new LinkedList<String>();
    }
    //生产方法
    public void put(){
        try{
            synchronized (taskQueue){
                while (taskQueue.size()==maxSize){
                    System.out.print(Thread.currentThread().getName()+": wait \n");
                    //队列已满，不能继续生产
                    taskQueue.wait();//在taskQueue对象上阻塞线程
                }
                String pollDate = sdf.format(new Date());
                taskQueue.add(pollDate);
                System.out.println("生产数据："+pollDate);
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+":put:"+taskQueue.size()+"\n");
                taskQueue.notifyAll();
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    //消费方法
    public void take(){
        try{
            synchronized (taskQueue){
                while (taskQueue.size()==0){
                    System.out.print(Thread.currentThread().getName()+": wait \n");
                    taskQueue.wait();//队列为空，阻塞消费
                }
                String pollDate = taskQueue.poll();
                System.out.println("消费数据："+pollDate);
                Thread.sleep(1000);
                System.out.print(Thread.currentThread().getName()+": take:"+taskQueue.size()+ "\n");
                taskQueue.notifyAll();
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
}
