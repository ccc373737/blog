package com.ccc.fizz.proxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGlib implements MethodInterceptor {
    private Object target;

    private Enhancer enhancer = new Enhancer();

    public Object getCGProxy(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        target = clazz.newInstance();

        //设置父类用于生成子类
        enhancer.setSuperclass(clazz);
        //设置回调 即拦截器本身
        enhancer.setCallback(this);

        //创建代理对象
        return enhancer.create();
    }

    public void temp() {
        System.out.println("CglibProxy....asdkl");
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        temp();
        method.invoke(target, objects);
        return null;
    }
}
