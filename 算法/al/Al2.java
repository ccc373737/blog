package com.ccc.fizz.al;

/**
 * 给定一个二维数组，其每一行从左到右递增排序，从上到下也是递增排序。给定一个数，判断这个数是否在该二维数组中。
 *
 *  [1,4,7,11,15],
 *  [2,5,8,12,19],
 *  [3,6,9,16,22],
 *  [10,13,14,17,24],
 *  [18,21,23,26,30]
 *
 *  Given target = 5, return true.
 *  Given target = 20, return false.
 * **/
public class Al2 {

    public static void main(String[] args) {
        int[][] array = new int[][]{new int[]{1,4,7,11,15}, new int[]{2,5,8,12,19}, new int[]{3,6,9,16,22}, new int[]{10,13,14,17,24}, new int[]{18,21,23,26,30}};
        System.out.println(judge(20, array));
    }

    public static boolean judge(int target, int[][] array) {
        if (array == null || array.length == 0 || array[0].length == 0) {
            return false;
        }

        int start = 0;
        int end = array[0].length - 1;

        while (start <= array.length - 1 && end >= 0) {
            if (array[start][end] < target) {
                start++;
            } else if (array[start][end] > target) {
                end--;
            } else {
                return true;
            }
        }

        return false;
    }
}
