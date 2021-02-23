package com.ccc.fizz.al;

/**
 *输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 * **/
public class Al27 {

    public static void main(String[] args) {
        System.out.println(minNumber(new int[]{3, 30, 34, 5, 9}));
    }

    public static String minNumber(int[] nums) {
        String[] temp = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            temp[i] = String.valueOf(nums[i]);
        }

        sort(temp, 0, nums.length - 1);

        StringBuffer sb = new StringBuffer();
        for (String num : temp) {
            sb.append(num);
        }
        return sb.toString();
    }

    //快速排序变形 比较arr[right] + index和index + arr[right]大小 注意是拼接
    public static void sort(String[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int left = start, right = end;
        int index = start;
        while (left < right) {
            while (left < right && (arr[right] + arr[index]).compareTo(arr[index] + arr[right]) >= 0) {
                right--;
            }

            while (left < right && (arr[left] + arr[index]).compareTo(arr[index] + arr[left]) <= 0) {
                left++;
            }

            String temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }

        String temp = arr[left];
        arr[left] = arr[index];
        arr[index] = temp;

        sort(arr, start, left - 1);
        sort(arr, left + 1, end);
    }
}
