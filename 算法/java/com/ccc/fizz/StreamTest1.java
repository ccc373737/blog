package com.ccc.fizz;

import com.ccc.fizz.com.ccc.funinterface.FunInter;
import com.ccc.fizz.master.base.user.entity.User;

import java.util.ArrayList;
import java.util.function.Consumer;

public class StreamTest1 {
    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(2l);
        user1.setPhone("18543125");

        User user2 = new User();
        user2.setId(1l);
        user2.setPhone("dfgfdg");

        User user3 = new User();
        user3.setId(4l);
        user3.setPhone("rerwtretry");

        ArrayList<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        list.sort((u1, u2) -> (int) (u1.getId() - u2.getId()));
        //Map<String, String> listTemp = list.stream().collect(Collectors.to));

        //Stream.iterate(0,x->x+1).limit(10).forEach(System.out::println);
        Consumer<String> t = (String a) -> {System.out.println("Hello World");};
        FunInter<String> t1 = (String a1) -> String.valueOf(a1);
        testa("aaaa", t1);

    }

    static String testa(String str, FunInter<String> con) {
        return con.ccc(str);
    }
}
