package com.ccc.fizz.customer;

public class Customer {
    private final int serviceTime;

    public Customer(int st) {
        this.serviceTime = st;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "【" + serviceTime + '】';
    }
}
