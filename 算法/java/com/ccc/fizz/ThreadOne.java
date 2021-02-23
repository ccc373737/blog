package com.ccc.fizz;

import java.util.concurrent.TimeUnit;

public class ThreadOne {
    private static boolean stopStatus;

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopStatus) {
                    try {
                        Thread.sleep(1000);
                        i++;
                        System.out.println("ccccc:" + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(5);
        stopStatus = true;
    }
}
