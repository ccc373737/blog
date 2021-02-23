package com.ccc.fizz.customer;

import java.util.concurrent.ArrayBlockingQueue;

public class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int capacity) {
        super(capacity);
    }

    public String toString() {
        if (this.size() == 0) {
            return "Empty";
        }
        StringBuffer sb = new StringBuffer();

        for (Customer customer : this) {
            sb.append(customer);
        }
        return sb.toString();
    }
}
