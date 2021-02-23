package com.ccc.fizz.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class lockTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}
