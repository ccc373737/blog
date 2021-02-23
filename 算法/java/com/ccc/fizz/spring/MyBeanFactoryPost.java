package com.ccc.fizz.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyBeanFactoryPost implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("beanfactorypostprocessor invoke");

        int count = beanFactory.getBeanDefinitionCount();
        String[] name = beanFactory.getBeanDefinitionNames();

        System.out.println("bean的个数为" + count);
        System.out.println("name为" + Arrays.asList(name));
    }
}
