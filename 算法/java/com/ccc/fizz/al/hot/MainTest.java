package com.ccc.fizz.al.hot;

public class MainTest {
    public static void main(String[] args) {
        String a = "abcccba";
        System.out.println(a.substring(0, 6));

        HotTest2 test = new HotTest2();
        test.search(new int[]{4,5,6,7,0,1,2}, 0);
        System.out.println();
    }
}
