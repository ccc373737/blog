package com.ccc.fizz;

import com.ccc.fizz.master.base.item.entity.Item;

public class Singleton2 {

    private volatile static Item item = null;

    private Singleton2() {};

    public static Item getInstance() {
        if (item == null) {
            synchronized (Singleton2.class) {
                if (item == null) {
                    item = new Item();
                }
            }
        }
        return item;
    }
}
