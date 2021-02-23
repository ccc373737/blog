package com.ccc.fizz.restaurant;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class WaitPerson implements Runnable{
    private static int counter = 0;

    private final int id = counter++;

    private Restaurant restaurant;

    public BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<>();

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void placeOrder(Customer customer, Food food) throws InterruptedException {
        restaurant.orders.put(new Order(customer, this, food));
    }

    @Override
    public void run() {
        try {
            while (! Thread.interrupted()) {
                Plate plate = filledOrders.take();

                System.out.print(this + "received" + plate + " delivering to " + plate.getOrder().getCustomer());

                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " InterruptedException");
        }
        System.out.println(this + " Terminating");
    }

    public String toString() {
        return "WaitPerson " + id;
    }
}
