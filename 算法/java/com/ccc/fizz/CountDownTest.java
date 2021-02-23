package com.ccc.fizz;

import java.util.Date;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CountDownTest {
    public static void main(String[] args) throws Exception{
        time1(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("程序开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static void time1(int concurrency, final Runnable action) throws Exception{
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch down = new CountDownLatch(concurrency);

        for (int i = 0 ; i < concurrency ; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ready.countDown();
                    System.out.println("线程" + finalI +"准备完毕！");
                    try {
                        start.await();
                        System.out.println("线程" + finalI +"开始！");
                        new Thread(action).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        down.countDown();
                        System.out.println("线程" + finalI +"结束！");
                    }
                }
            }).start();
        }

        System.out.println("等待线程准备");
        Long time1 = new Date().getTime();
        ready.await();
        Long time2 = new Date().getTime();
        System.out.println("线程准备时间为：" + (time2 - time1) / 1000);

        start.countDown();
        System.out.println("程序准备开始。。。");

        down.await();
        System.out.println("程序结束！");
    }
}
