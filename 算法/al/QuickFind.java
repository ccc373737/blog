package com.ccc.fizz.al;

import java.util.Arrays;

public class QuickFind {
    public static void main(String[] args) {
        int[] array = new int[]{1,5,3,4,6,2,9,7,8};

        quick(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public static void quick(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int index = par(array, left, right);
        quick(array, 0, index - 1);
        quick(array, index + 1, right);
    }

    public static int par(int[] array, int left, int right) {
        int init = left;
        int pi = array[left];

        while (left < right) {
            //右边大于pi，右指针向左1位，注意是先向左移指针，后右移指针，这很重要，这会使左右指针指向比pi小的值，此时基准位置和左指针位置的互换才有意义
            while (left < right && array[right] >= pi) {
                right--;
            }

            //左边小于pi，左指针向右1位
            while (left < right && array[left] <= pi) {
                left++;
            }

            if (left < right) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
            }
        }

        array[init] = array[left];
        array[left] = pi;

        return left;
    }
}
