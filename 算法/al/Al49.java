package com.ccc.fizz.al;

import java.util.Arrays;

/**
 * 给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，其中 B 中的元素 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
 *
 * **/
public class Al49 {
    //对称遍历
    public int[] constructArr(int[] a) {
        int[] target = new int[a.length];
        Arrays.fill(target, 1);

        for (int i = 1; i < target.length; i++) {
            target[i] = target[i-1] * a[i-1];
        }

        int temp = 1;
        for (int i = target.length - 2; i >= 0; i--) {
            temp *= a[i+1];
            target[i] *= temp;
        }

        return target;
    }

}
