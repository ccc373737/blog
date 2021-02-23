package com.ccc.fizz.al;

import java.util.HashSet;
import java.util.Set;

/**
 *从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 *
 * **/
public class Al43 {
    //[5,2,1,2,4]
    public boolean isStraight(int[] nums) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (num == 0) {
                continue;
            }

            if (set.contains(num)) {
                return false;
            }

            set.add(num);

            if (num > max) {
                max = num;
            }

            if (num < min) {
                min = num;
            }
        }

        return max - min + 1 <= 5;
    }
}
