package com.ccc.fizz.customer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CustomerGenerator implements Runnable{
    private CustomerLine customers;

    private static Random random = new Random();

    public CustomerGenerator(CustomerLine line) {
        this.customers = line;
    }

    @Override
    public void run() {
        try {
            while (! Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
                customers.put(new Customer(random.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator InterruptedException");
        }
        System.out.println("CustomerGenerator Terminating");
    }
}
