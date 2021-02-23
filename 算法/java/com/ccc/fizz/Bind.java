package com.ccc.fizz;
class Father {
    public String name = "aaa";

    public String getName() {
        return name;
    }
}

class Son extends Father {
    public String name = "bbb";

    public String getName() {
        return name;
    }
}

public class Bind {
    public static void main(String[] args) {
        Father obj = new Son();
        System.out.println(obj.name);
        System.out.println(obj.getName());
    }

}
