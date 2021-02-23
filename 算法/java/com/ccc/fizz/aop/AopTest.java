package com.ccc.fizz.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {
    public static void main(String[] args) {
        ApplicationContext context =  new ClassPathXmlApplicationContext("beanTest.xml");
        Task task = (Task) context.getBean("singleTask");
        task.getMsg();
    }
}
