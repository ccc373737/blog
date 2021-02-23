package com.ccc.fizz.threadlocal;

public class ThreadLocalDemo {

    /*private static final ThreadLocal<Person> HOLDER = new ThreadLocal<Person>() {
        //ThreadLocal第一次调用get时触发
        @Override
        protected Person initialValue() {
            System.out.println("init Method......");
            return new Person("A Baby", 0);
        }
    };*/

    private static final ThreadLocal<Person> HOLDER = new ThreadLocal<Person>();

    public static Person getContext() {
        Person person = HOLDER.get();

        if (person == null) {
            person = new Person(Thread.currentThread().getName(), 7);
            HOLDER.set(person);
        }
        return person;
    }

    public static void setUp(Person person) {
        HOLDER.set(person);
    }

    public static boolean isExist() {
        Person person = HOLDER.get();
        return person == null;
    }

    public static void clear() {
        HOLDER.remove();
    }
}
