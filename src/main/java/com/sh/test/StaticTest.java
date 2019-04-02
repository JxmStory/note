package com.sh.test;

public class StaticTest extends Person {

    int a = 110;
    static int b = 112;

    public static void main(String[] args) {
        staticFunction();
    }

    static {
        System.out.println("1");
    }

    static int c = 119;

    static StaticTest staticTest = new StaticTest();

    {
        System.out.println("2");
    }
    StaticTest(){
        System.out.println("3");
        System.out.println("a="+a+",b="+b);
    }

    public static void staticFunction(){
        System.out.println("4");
    }
}
