package com.ccc.fizz.customer;

import java.util.concurrent.TimeUnit;

public class Teller implements Runnable, Comparable<Teller>{

    private static int counter = 0;

    private final int id = counter++;

    private int customerServed = 0;

    private CustomerLine customers;

    private boolean servingCustomerLine = true;

    public Teller(CustomerLine line) {
        this.customers = line;
    }

    @Override
    public int compareTo(Teller o) {
        return customerServed < o.customerServed ? -1 : (customerServed == o.customerServed ? 0 : 1);
    }

    @Override
    public void run() {
        try {
            while (! Thread.interrupted()) {
                Customer customer = customers.take();
                //模拟服务时间
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());

                synchronized (this) {
                    customerServed++;
                    while (! servingCustomerLine) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + " InterruptedException");
        }
        System.out.println(this + " Terminating");
    }

    public synchronized void doSomeThing() {
        System.out.println("Teller " + this.id + "已服务" + customerServed + "个");
        customerServed = 0;
        servingCustomerLine = false;
    }

    public synchronized  void serveCustomerLine() {
        servingCustomerLine = true;
        notifyAll();
    }

    @Override
    public String toString() {
        return "Teller{" +
                "id=" + id +
                '}';
    }


}
