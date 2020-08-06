package com.backstage.controller;

public class Test {


    public static void main(String[] args) {
        int x = 3;
        System.out.println(x);
        System.out.println(x);
        show(x);
        System.out.println(x);
    }

    public static void show(int x) {
        x = 4;
        System.out.println("a"+x);
    }
}
