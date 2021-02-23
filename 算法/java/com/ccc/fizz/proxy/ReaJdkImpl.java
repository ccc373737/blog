package com.ccc.fizz.proxy;

public class ReaJdkImpl implements ReaJdkObj {
    @Override
    public void coding() {
        System.out.println("reellllll!......");
    }

    @Override
    public final void rest() {
        coding();
        System.out.println("resttttttttt..sadsa");
    }
}
