package com.ccc.fizz;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockQueueTest {
    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(1024);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
        Thread.sleep(4000);
    }
}
class Producer implements Runnable{
    protected ArrayBlockingQueue queue = null;
    public Producer(ArrayBlockingQueue queue) {
        this.queue = queue;
    }
    public void run() {
        try {
            queue.put("1");
            System.out.println("put"+1);
            Thread.sleep(1000);
            queue.put("2");
            System.out.println("put"+2);
            Thread.sleep(1000);
            queue.put("3");
            System.out.println("put"+3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Consumer implements Runnable{
    protected ArrayBlockingQueue queue = null;
    public Consumer(ArrayBlockingQueue queue) {
        this.queue = queue;
    }
    public void run() {
        try {
            System.out.println("take"+queue.take());
            System.out.println("take"+queue.take());
            System.out.println("take"+queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}