package com.ccc.fizz.al;

public class BinaryFind {
    public static void main(String[] args) {
//        int[] array = new int[]{1,5,6,9,14,18,21,23};
//        System.out.println(getIndex(array, 2));
//        System.out.println(getIndex(array, 6));
//        System.out.println(getIndex(array, 9));
//        System.out.println(getIndex(array, 21));
//        System.out.println(getIndex(array, 10));
//        System.out.println(getIndex(array, 23));
        int[] array = new int[]{1,1,1,2,2,2,4,5,6};
        System.out.println(getLeft(array, 0));
    }

    public static int getIndex(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            //查到对象 直接返回
            if (array[mid] == target) {
                return mid;
            //中值小于target，定义left，向右收缩
            } else if (array[mid] < target) {
                left = mid + 1;
            //中值大于target，定义right，向左收缩
            } else if (array[mid] > target) {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static int getRight(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            //>=target时，右指针不动，else 左指针不断逼近
            if (array[mid] == target) {
                right = mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else if (array[mid] > target) {
                right = mid;
            }
        }

        return array[left] == target ? left : -1;
    }

    public static int getLeft(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (array[mid] == target) {
                left = mid + 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else if (array[mid] > target) {
                right = mid;
            }
        }
        //边界检查，[1,1,2,2]中查找0，right会不断向左逼近，而left不动
        if (left == 0) {
            return -1;
        }
        return array[left - 1] == target ? left - 1 : -1;
    }
}
