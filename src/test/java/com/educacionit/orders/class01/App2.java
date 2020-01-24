package com.educacionit.orders.class01;

import java.util.Arrays;
import java.util.List;

public class App2 {

    public static void main (String[] args) {

        // LIST NUMBERS.
        List<Integer> list = Arrays.asList (10, 63, 852, 987, 565,
                                            52, 1, 77, 123, 999);

        // GET LESS THAN 100. (Traditional way)
        for (Integer i: list) {
            if (i < 100) {
                System.out.println (i);
            }
        }
        System.out.println ("**************************");

        // GET LESS THAN 100. (Functional way)
        list.forEach (e -> {
            if (e < 100) {
                System.out.println (e);
            }
        });
        System.out.println ("**************************");
        list.stream ().filter (e -> e<100).forEach (System.out::println);
    }
}
