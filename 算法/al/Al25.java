package com.ccc.fizz.al;

/**
 *输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 * **/
public class Al25 {

    public static void main(String[] args) {
        getLeastNumbers(new int[]{0,0,2,3,2,1,1,2,0,4}, 4);
        System.out.println("a");
    }

    public static int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0) {
            return new int[]{};
        }

        if (k >= arr.length) {
            return arr;
        }

        sort(arr, 0, arr.length - 1, k);

        int[] out = new int[k];

        for (int i = 0; i < out.length; i++) {
            out[i] = arr[i];
        }

        return out;
    }

    public static void sort(int[] arr, int start, int end, int k) {
        int left = start, right = end;
        int index = start;
        while (left < right) {
            while (left < right && arr[right] >= arr[index]) {
                right--;
            }

            while (left < right && arr[left] <= arr[index]) {
                left++;
            }

            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }

        int temp = arr[left];
        arr[left] = arr[index];
        arr[index] = temp;

        if (left == k) {
            return;
        } else if (left > k) {
            sort(arr, start, left - 1, k);
        } else if (left < k) {
            sort(arr, left + 1, end, k);
        }
    }
}
