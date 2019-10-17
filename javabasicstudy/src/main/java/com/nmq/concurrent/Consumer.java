package com.nmq.concurrent;

/**
     * 消费者
     */
 public   class Consumer implements Runnable{
        private Buffer buffer;

        public Consumer(Buffer buffer) {
            this.buffer = buffer;
        }

        public void run() {
            while (true){
                buffer.take();
            }
        }
    }