package com.ccc.fizz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AtomicTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Count count = new Count();
        for (int i = 0; i < 10000; i++) {
            service.execute(() -> count.add());
        }

        service.shutdown();//关闭service，即存在线程继续执行，而不能插入新的线程去执行
        try {
            service.awaitTermination(1, TimeUnit.DAYS);//等待所有线程执行完成，参数是最大等待时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(count.getCount());
    }
}

class Count {
    //private Integer count = 0;
    private AtomicInteger i = new AtomicInteger();
    //LongAdder
    public AtomicInteger getCount() {
        return i;
    }

    public void add() {
        i.incrementAndGet();
        //System.out.println(count);
    }
}