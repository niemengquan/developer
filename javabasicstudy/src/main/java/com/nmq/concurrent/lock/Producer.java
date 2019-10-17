package com.nmq.concurrent.lock;


/**
     * 模拟生产者
     */
 public class Producer implements Runnable{
        private Buffer buffer;

        public Producer(Buffer buffer) {
            this.buffer = buffer;
        }
        public void run() {
            while(true){
                buffer.put();
            }
        }
    }