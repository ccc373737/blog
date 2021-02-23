package com.ccc.fizz.al;

/**
 * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 *
 * **/
public class Al36 {
    public static void main(String[] args) {
        char a = 'a';
        char b = 'b';
        StringBuffer sb= new StringBuffer();
        sb.append(a).append(b);
        System.out.println(sb.toString());

    }
    public int[] singleNumbers(int[] nums) {
        int target = 0;
        for (int num : nums) {
            target ^= num;
        }
        //这个target即为不重复的两个数a ^ b
        //由于^的操作不同取1，那么a ^ b中必然有一位为1，且用此位分别去a和b做与操作，必然得到不同的结果
        //1101 ^ 1010 = 0111
        //1101 & 1 = 1101
        //1010 & 1 = 1010
        //于是两个不同的数就可以根据为1的这位判断出来，而对于其他相同的数，做与操作必然得到相同的结果
        int mask = 1;
        while ((target & mask) != 1) {
            mask <<= 1;
        }

        int a = 0;
        int b = 0;

        for (int num : nums) {
            if ((num & mask) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }
        return new int[]{a,b};
    }
}
