package com.ccc.fizz.dispatch;

public class StaticDispatch {

    static abstract class Human{}

    static class Man extends Human{}

    static class Woman extends Human{}

    public void sayHello(Human guy) {
        System.out.println("hello guy");
    }

    public void sayHello(Man guy) {
        System.out.println("hello Man");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello Woman");
    }

    public static void main(String[] args) {
        //静态类型指Human man，指声明该变量时使用的类型
        //运行时类型指该变量指向的类型new Man()

        //使用子类的sayHello方法必须声明Man man = new Man()
        //静态类型的变化仅在使用时发生，并且最终的类型在编译器可知
        //运行时类型变化的结果在运行期才可确定
        Human man = new Man();

        Human woman = new Woman();

        StaticDispatch dis = new StaticDispatch();

        dis.sayHello(man);
        dis.sayHello(woman);
    }
}
