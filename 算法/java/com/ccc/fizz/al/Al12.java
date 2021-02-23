package com.ccc.fizz.al;

public class Al12 {
    public static void main(String[] args) {
        System.out.println(myPow(2.000000, -2));
    }

    public static double myPow(double x, int n) {
        if (n < 0) {
            x = 1 / x;
            n = 0 - n;
        }

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return x;
        }

        double target = x;
        for (int i = 0; i < n - 1; i++) {
            target = target * x;
        }
        return target;
    }
}
