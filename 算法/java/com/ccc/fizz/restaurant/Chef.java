package com.ccc.fizz.restaurant;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Chef implements Runnable{
    private static int counter = 0;

    private final int id = counter++;

    private Restaurant restaurant;

    private Random random = new Random();

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (! Thread.interrupted()) {
                Order order = restaurant.orders.take();

                Food food = order.item();

                TimeUnit.MILLISECONDS.sleep(random.nextInt(500));

                Plate plate = new Plate(order, food);

                order.getWaitPerson().filledOrders.put(plate);

                System.out.println(this + " next");
            }
        } catch (InterruptedException e) {
            System.out.println(this + " InterruptedException");
        }
        System.out.println(this + " off duty");
    }

    public String toString() {
        return "Chef " + id;
    }
}
