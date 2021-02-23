package com.ccc.fizz.aop;

import org.springframework.context.annotation.Configuration;

public class singleTask implements Task {

    @Override
    public void getMsg() {
        System.out.println("MyTask");
    }
}
