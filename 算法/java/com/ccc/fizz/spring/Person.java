package com.ccc.fizz.spring;

public class Person {

    private String name;

    private int age;

    private Money money;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }
}
