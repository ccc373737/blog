package com.ccc.fizz.al;
/**
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。
 * 请找出数组中任意一个重复的数字
 *
 * Input:
 * {2, 3, 1, 0, 2, 5}
 *
 * Output:
 * 2
 * **/
public class Al1 {
    public static void main(String[] args) {

        System.out.println(getRepeat(new int[]{0, 3, 0, 4, 2, 4}));

    }

    public static Integer getRepeat(int[] list) {

        if (list.length <= 0) {
            return null;
        }

        Integer[] temp = new Integer[list.length];

        for (int i = 0; i < list.length; i++) {
            if (temp[list[i]] != null) {
                return list[i];
            }
            temp[list[i]] = list[i];
        }

        return null;
    }
}
