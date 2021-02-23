package com.ccc.fizz.restaurant;

import java.util.Random;

public enum Food {
    SALAD, SOUP, PAD_THAI, COFFEE, TEA, FRUIR, HUMMONS, LATTE;

    public static Food getOneFood() {
        Food[] foods = Food.values();
        return foods[new Random().nextInt(7)];
    }
}
