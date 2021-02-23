package com.ccc.fizz.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SpringXmlTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ApplicationContext context =  new ClassPathXmlApplicationContext("beanTest.xml");
        //BeanFactory bf = new XmlBeanFactory(new ClassPathResource("beanTest.xml"));

        //MyBean bean = (MyBean) bf.getBean("MyBean");
        Person person = (Person) context.getBean("person");

        System.out.println(person.getMoney().getMon());

        TestEvent event = new TestEvent("hello", "msg");
        context.publishEvent(event);
    }
}
