package com.ccc.fizz.customer;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TellerManager implements Runnable {
    private ExecutorService exec;

    private CustomerLine customers;

    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();

    private Queue<Teller> doOtherTellers = new LinkedList<>();

    private int adjustmentPeriod;

    private static Random random = new Random();

    public TellerManager(ExecutorService e, CustomerLine c, int a) {
        this.exec = e;
        this.customers = c;
        this.adjustmentPeriod = a;
        Teller teller = new Teller(customers);
        exec.execute(teller);
        workingTellers.add(teller);
    }

    public void adjustTellerNumber() {
        if (customers.size() / workingTellers.size() > 2) {
            if (doOtherTellers.size() > 0) {
                Teller teller = doOtherTellers.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller);
                return;
            }
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTellers.add(teller);
            return;
        }

        if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2) {
            reassignOneTeller();
        }

        if (customers.size() == 0) {
            while (workingTellers.size() > 0) {
                reassignOneTeller();
            }
        }
    }

    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomeThing();
        doOtherTellers.offer(teller);
    }

    @Override
    public void run() {
        try {
            while (! Thread.interrupted()) {
                //调整时间间隔
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber();

                System.out.print(customers + "{");

                for (Teller workingTeller : workingTellers) {
                    System.out.print(workingTeller.toString());
                }

                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println(this + " InterruptedException");
        }
        System.out.println(this + " Terminating");
    }
}
