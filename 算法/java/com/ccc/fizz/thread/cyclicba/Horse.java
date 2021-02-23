package com.ccc.fizz.thread.cyclicba;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Horse implements Runnable {
    private static int counter = 0;

    private final int id = counter++;

    private static Random rand = new Random(37);

    private int strides = 0;

    private static CyclicBarrier cyclicBarrier;

    public Horse(CyclicBarrier b) {
        cyclicBarrier = b;
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += rand.nextInt(3);
                }
                cyclicBarrier.await();  
            }
        } catch (Exception e) {
            System.out.println("horse Exception");
        }
    }

    public String toString() {
        return "Horse: " + id;
    }

    public String tracks() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < getStrides() ; i++) {
            sb.append("*");
        }
        sb.append(id);
        return sb.toString();
    }
}
