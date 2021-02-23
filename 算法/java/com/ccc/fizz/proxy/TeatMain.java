package com.ccc.fizz.proxy;

import java.util.Arrays;

public class TeatMain {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        ReaJdkObj bind = (ReaJdkObj) new JdkProxy().bind(new ReaJdkImpl());
        bind.coding();

//        System.out.println(bind.getClass().getSuperclass());
//        System.out.println(Arrays.toString(bind.getClass().getInterfaces()));
//
//        System.out.println("*****************************************");
//        RealCGlibObj cgBind = (RealCGlibObj) new CGlib().getCGProxy(RealCGlibObj.class);
//        cgBind.coding();
//        cgBind.rest();
    }
}
