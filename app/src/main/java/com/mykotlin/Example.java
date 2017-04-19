package com.mykotlin;

/**
 * 面试 提
 * Created by qiqi on 17/3/28.
 */

public class Example {
    //    String str = new String("good");
    String str = "good";
    char[] ch = {'a', 'b', 'c'};

    public static void main(String... s) {
        Example ex = new Example();
        ex.change(ex.str, ex.ch);
        System.out.println(ex.str + " and ");

        System.out.println(ex.ch);
//        good and
//        gbc
    }

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'g';
    }
}
