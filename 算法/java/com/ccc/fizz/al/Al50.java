package com.ccc.fizz.al;

public class Al50 {
    public static void main(String[] args) {
        getAds(new StringBuffer(), 3, 3);
    }

    public static void getAds(StringBuffer strs, int left, int right) {
        if (left == 0 && right == 0) {
            System.out.println(strs.toString());
        }

        if (left > right) {
            return;
        }

        if (left > 0) {
            getAds(strs.append("("), left - 1, right);
            strs.deleteCharAt(strs.length() - 1);
        }

        if (right > 0) {
            getAds(strs.append(")"), left, right - 1);
            strs.deleteCharAt(strs.length() - 1);
        }
    }
}
