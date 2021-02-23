package com.ccc.fizz.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        Runnable r1 = new MyThread();
        Runnable r2 = new MyThread();
        Runnable r3 = new MyThread();

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(r1);
        exec.execute(r2);
        exec.execute(r3);
    }
}
