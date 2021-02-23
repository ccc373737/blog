package com.ccc.fizz.al.greedy;

public class GMain {
    public static void main(String[] args) {
        GreedyTest1 test = new GreedyTest1();
        //[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]
        //[[-2147483646,-2147483645],[2147483646,2147483647]]
        System.out.println(test.monotoneIncreasingDigits(332));
    }
}
