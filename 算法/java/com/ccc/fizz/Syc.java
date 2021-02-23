package com.ccc.fizz;

import java.util.concurrent.*;

public class Syc {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(1));

        //ExecutorService pool1 = Executors.newScheduledThreadPool()

        System.out.println(-1 << 29);
        System.out.println(0 << 29);
        System.out.println(1 << 29);
        System.out.println(2 << 29);
        System.out.println(3 << 29);
        System.out.println(-536870912 | 0);
        System.out.println(-536870910 & 536870911);
        System.out.println(-536870912 & ~536870911);


        /*1111111111111111111111111111111111100000000000000000000000000000
          00011111111111111111111111111111

        1111111111111111111111111111111111100000000000000000000000000011
        0000000000000000000000000000000000011111111111111111111111111111*/

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    System.out.println("线程一执行-----------" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("线程二执行-----------" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程三执行-----------" + Thread.currentThread().getName());
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程四执行-----------" + Thread.currentThread().getName());
            }
        });

        /*pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程五执行-----------" + Thread.currentThread().getName());
            }
        });*/

    }
}
/*1111111111111111111111111111111111100000000000000000000000000000
0000000000000000000000000000000000011111111111111111111111111111
0000000000000000000000000000000000100000000000000000000000000000

1000000000000000000000000000000000100000000000000000000000000000
1111111111111111111111111111111111000000000000000000000000000000*/
