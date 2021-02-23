package com.ccc.fizz.restaurant;

public class Order {
    private static int counter = 0;

    private final int id = counter++;

    private final Customer customer;

    private final WaitPerson waitPerson;

    private final Food food;

    public Order(Customer c, WaitPerson w, Food f) {
        this.customer = c;
        this.waitPerson = w;
        this.food = f;
    }

    public Food item() {
        return food;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id:" + id +
                ", customer:" + customer +
                ", waitPerson:" + waitPerson +
                ", food:" + food +
                '}';
    }
}
