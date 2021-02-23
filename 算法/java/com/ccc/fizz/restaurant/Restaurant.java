package com.ccc.fizz.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Restaurant implements Runnable{

    private List<WaitPerson> waitPersons = new ArrayList<>();

    private List<Chef> chefs = new ArrayList<>();

    private ExecutorService exec;

    private Random random = new Random();

    public BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    public Restaurant(ExecutorService exec, int nWaitPersons, int nChefs) {
        this.exec = exec;

        for (int i = 0 ; i < nWaitPersons ; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }

        for (int i = 0 ; i < nChefs ; i++) {
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    @Override
    public void run() {
        try {
            while (! Thread.interrupted()) {
                WaitPerson wp = waitPersons.get(random.nextInt(waitPersons.size()));

                Customer c = new Customer(wp);

                exec.execute(c);

                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " InterruptedException");
        }
        System.out.println("Restaurant closeing");
    }
}
