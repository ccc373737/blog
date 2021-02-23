package com.ccc.fizz.al.slid;

public class MainTet {
    public static void main(String[] args) {
        PrefixTest test1 = new PrefixTest();
        //[[4,10,15,24,26],[0,9,12,20],[5,18,22,30]][2,2,2,1,2,2,1,2,2,2]
        //2
        System.out.println(test1.maxFreq("abcde", 2,3,3));
    }
}
