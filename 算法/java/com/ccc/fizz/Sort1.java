package com.ccc.fizz;

public class Sort1 {
    public static void main(String[] args) {


    }

    public void sort(int[] arr, int low, int hi) {
        if (low == hi) {
            return;
        }

        int mid = (low + hi) / 2;
        sort(arr, low, mid);
        sort(arr, mid + 1, hi);
        merge(arr, low, mid, hi);
    }

    public void merge(int[] arr, int low, int mid, int hi) {
        int[] temp = new int[arr.length];
        for (int i : arr) {
            temp[i] = arr[i];
        }

        int x = low;
        int y = mid + 1;
        while (x <= mid && y <=hi) {
            if (temp[x] <= temp[y]) {
                arr[low++] = temp[x++];
            } else {
                arr[low++] = temp[y++];
            }
        }

        while (x > mid) {
            arr[low++] = temp[y++];
        }

        while (y > hi) {
            arr[low++] = temp[x++];
        }
    }
}
