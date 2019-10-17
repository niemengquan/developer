package com.nmq.concurrent.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by niemengquan on 2018/1/10.
 */
public class Buffer {
    private final Lock lock=new ReentrantLock();
    private final Condition notFull=lock.newCondition();
    private final Condition notEmpty=lock.newCondition();
    private int maxSize;
    private LinkedList<String> taskQueue;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.taskQueue=new LinkedList<String>();
    }
    public void put(){
        try{
            lock.lock();
            while (taskQueue.size()==maxSize){
                //队列已满
                System.out.print(Thread.currentThread().getName()+": wait \n");
                notFull.await();//阻塞生产线程
            }
            String pollData=sdf.format(new Date());
            taskQueue.add(pollData);
            System.out.println("生产数据："+pollData);
            Thread.currentThread().sleep(1000);
            System.out.print(Thread.currentThread().getName()+": put:"+taskQueue.size()+ "\n");
            notEmpty.signalAll();//唤醒消费线程
        }catch (Exception err){
            err.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void take(){
        try {
            lock.lock();
            while (taskQueue.size()==0){
                //队列为空，阻塞消费线程
                System.out.print(Thread.currentThread().getName()+": wait \n");
                notEmpty.await();
            }
            String takeData = taskQueue.poll();
            System.out.println("消费数据："+takeData);
            Thread.currentThread().sleep(1000);
            System.out.print(Thread.currentThread().getName()+": take:"+taskQueue.size()+ "\n");
            //唤醒生产线程
            notFull.signalAll();
        }catch (Exception err){
            err.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
