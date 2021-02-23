package com.ccc.fizz.jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    static class Oom{}

    public static void main(String[] args) {
        List<Oom> list = new ArrayList<>();

        while (true) {
            list.add(new Oom());
        }
    }
}
