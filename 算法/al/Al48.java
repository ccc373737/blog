package com.ccc.fizz.al;

import java.util.Arrays;

/**
 * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
 *
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
 *
 * 每个孩子至少分配到 1 个糖果。
 * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
 * 那么这样下来，老师至少需要准备多少颗糖果呢？
 *
 * **/
public class Al48 {
    public static void main(String[] args) {
        candy(new int[]{1,3,4,5,2});
    }

    public static int candy(int[] ratings) {
        int[] array = new int[ratings.length];
        Arrays.fill(array, 1);

        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i-1]) {
                array[i] = array[i-1] + 1;
            }
        }

        for (int end = ratings.length - 2; end >= 0; end--) {
            if (ratings[end] > ratings[end+1]) {
                array[end] = Math.max(array[end], array[end+1] + 1);
            }
        }

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            count += array[i];
        }
        return count;

    }
}
