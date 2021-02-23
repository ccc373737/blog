package com.ccc.fizz;

import com.ccc.fizz.master.base.item.entity.Item;

public class Singleton1 {
    private static Item item = new Item();

    private Singleton1() {}

    public static synchronized Item getInstance() {
        return item;
    }

}
