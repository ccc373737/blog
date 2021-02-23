package com.ccc.fizz.dispatch;

public class FieldDis {

    static class Father {
        private int money = 1;

        public Father() {
            money = 2;
            showMoney();
        }

        public void showMoney() {
            System.out.println("father money:" + money);
        }
    }

    static class Son extends Father {
        public int money = 3;

        public Son() {
            money = 4;
            showMoney();
        }

        public void showMoney() {
            System.out.println("son money:" + money);
        }
    }

    public static void main(String[] args) {
        Father guy = new Son();
        System.out.println("has....." + guy.money);
    }
}
