package com.ccc.fizz.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectCon {

    @Pointcut("execution(* *.getMsg(..))")
    public void test() {};

    @Before("test()")
    public void beforeTest() {
        System.out.println("AOP beforeTest...");
    }

    @After("test()")
    public void AfterTest() {
        System.out.println("AOP afterTest...");
    }
}
