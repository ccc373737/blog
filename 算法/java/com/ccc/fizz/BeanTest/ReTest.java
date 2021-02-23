package com.ccc.fizz.BeanTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReTest {

    public static void main(String[] args) throws Exception{
        List<Integer> list = new ArrayList<>();

        list.add(4);

        Method add = ArrayList.class.getMethod("add", Object.class);
        add.invoke(list, "Hello");

        for (Object obj : list) {
            System.out.println(obj);
        }
    }
}
