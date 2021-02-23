package com.ccc.fizz.al;

/**
 *  二进制中1的个数
 * **/
public class Al10 {
    public static int getOne(int target) {
        int count = 0;
        while (target != 0) {
            count += target & 1;
            target >>>= 1;
        }

        return count;
    }
}
