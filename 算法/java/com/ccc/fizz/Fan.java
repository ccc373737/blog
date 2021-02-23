package com.ccc.fizz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Fan {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(22);
        list.add(33);

        Method method = list.getClass().getDeclaredMethod("add",Object.class);

        method.invoke(list,"test");
        method.invoke(list,42.9f);

        for (Object o : list) {
            System.out.println(o);
        }

        //accept(list);

        List<String>[] li3;//可以创建
        List[] temp = new List[10];
        li3 = (List<String> [])temp;

        Object[] objs = li3;
        objs[1] = new ArrayList<Integer>();
    }

    public <T> void accept(ArrayList<T> objList) {
        objList.add((T) "a");

    }
}
