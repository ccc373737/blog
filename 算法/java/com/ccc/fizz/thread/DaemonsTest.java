package com.ccc.fizz.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DaemonsTest implements Runnable{
    public static void main(String[] args) {
        for (int i = 0 ; i < 10 ; i++) {
            //这时所以线程都是后台线程，mian执行完不影响此线程的运行
            Thread thread = new Thread(new DaemonsTest());
            //如果设置了daemon，那么线程变为非后台线程，只要main执行完，那么所有线程结束
            //thread.setDaemon(true);
            thread.start();
        }
        System.out.println("main finished");
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread() + "" + this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
