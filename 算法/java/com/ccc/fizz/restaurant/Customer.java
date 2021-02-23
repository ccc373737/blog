package com.ccc.fizz.restaurant;

import java.util.concurrent.SynchronousQueue;

public class Customer implements Runnable{
    private static int counter = 0;

    private final int id = counter++;

    private final WaitPerson waitPerson;

    private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();

    public Customer(WaitPerson w) {
        this.waitPerson = w;
    }

    public void deliver(Plate p) throws InterruptedException {
        placeSetting.put(p);
    }

    @Override
    public void run() {
        for (int i = 0 ; i < 3 ; i++) {
            Food food = Food.getOneFood();
            try {
               waitPerson.placeOrder(this, food);
               System.out.println(this + "eating" + placeSetting.take());

            } catch (InterruptedException e) {
                System.out.println(this + "waiting for" + food + " interr");
                break;
            }
        }
        System.out.println(this + " finished meal, leaving");
    }

    public String toString() {
        return "Customer " + id;
    }
}
