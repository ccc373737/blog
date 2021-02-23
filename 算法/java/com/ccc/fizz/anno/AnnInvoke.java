package com.ccc.fizz.anno;

import java.lang.reflect.Method;

public class AnnInvoke {
    public static void main(String[] args) {
        AnnUtils.class.getAnnotations();//类上的注解 如果有
        Method[] declaredMethods = AnnUtils.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {

            Ann annotation = declaredMethod.getAnnotation(Ann.class);//方法上的注解
            System.out.println(annotation.id() + "------" + annotation.desc());//注解属性
        }


    }
}
