package com.ccc.fizz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class reentrantLock {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        ExecutorService pool = Executors.newCachedThreadPool();

        pool.execute(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("线程一----------" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                System.out.println("111111111111");
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                lock.tryLock();
                System.out.println("线程二----------" + Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        });
    }
}
