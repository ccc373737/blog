package com.ccc.fizz.customer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankTest {
    static final int MAX_LINE_SIZE = 50;

    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();

        CustomerLine line = new CustomerLine(MAX_LINE_SIZE);

        exec.execute(new CustomerGenerator(line));

        exec.execute(new TellerManager(exec, line, ADJUSTMENT_PERIOD));

        TimeUnit.SECONDS.sleep(100);

        exec.shutdownNow();

    }
}
