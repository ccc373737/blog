package com.ccc.fizz.dispatch;

public class DynamicDispatch {

    static abstract class Human {
        protected void sayHello() {
            System.out.println("Hello Human");
        };
    }

    static class Man extends Human {

        @Override
        protected void sayHello() {
            System.out.println("Hello Man");
        }
    }

    static class Woman extends Human {

        @Override
        protected void sayHello() {
            System.out.println("Hello Woman");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
    }
}
