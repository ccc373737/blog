package com.ccc.fizz.dispatch;

public class FieldTest2 {

    static class Human{
        private void hello() {
            System.out.println("hello human");
        }
    }

    static class Man extends Human{
        public void hello() {
            System.out.println("hello man");
        }
    }

    public static void main(String[] args) {
        Human h = new Man();
        h.hello();
    }
}
