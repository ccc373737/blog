package com.ccc.fizz.classloader;

import com.ccc.fizz.spring.SpringXmlTest;
import org.springframework.core.io.ClassPathResource;
import sun.misc.Launcher;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoaderTest {

    public static void main(String[] args) throws Exception{
        //Arrays.asList(System.getProperty("sun.boot.class.path").split(";")).stream().forEach(System.out::println);

        System.out.println(System.getProperty("java.ext.dirs"));

        Arrays.asList(System.getProperty("java.class.path").split(";")).stream().forEach(System.out::println);

        ClassPathResource classPathResource = new ClassPathResource("beanTest.xml");
        InputStream inputStream = classPathResource.getInputStream();

        Class<?> object = SpringXmlTest.class.getClassLoader().loadClass("SpringXmlTest");

        System.out.println(SpringXmlTest.class.getResource("beanTest.xml"));
        System.out.println(SpringXmlTest.class.getResource("/beanTest.xml"));
        System.out.println();
        System.out.println(SpringXmlTest.class.getClassLoader().getResource("beanTest.xml"));
        System.out.println(SpringXmlTest.class.getClassLoader().getResource("/beanTest.xml"));
    }
}
