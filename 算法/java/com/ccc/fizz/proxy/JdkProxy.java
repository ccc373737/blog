package com.ccc.fizz.proxy;

import org.mapstruct.TargetType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {
    private Object target;

    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public void test() {
        System.out.println("Hello World ProxyJDK");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        test();
        method.invoke(target, args);
        return null;
    }
}
