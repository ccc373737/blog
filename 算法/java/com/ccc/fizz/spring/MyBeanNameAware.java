package com.ccc.fizz.spring;

import org.springframework.beans.factory.BeanNameAware;

public class MyBeanNameAware implements BeanNameAware {
    @Override
    public void setBeanName(String name) {
        System.out.println("reste1111" + name);
    }
}
