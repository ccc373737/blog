package com.ccc.fizz.threadlocal;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

public class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start......");
        // 休眠3s
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {}

        ThreadLocalDemo.getContext();
    }
}
