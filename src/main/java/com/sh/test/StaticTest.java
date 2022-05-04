package com.sh.test;

/**
 * 类初始化（五种主动引用）时：
 * 父类静态代码块-父类静态变量-子类静态代码块-子类静态变量
 *
 * 类实例化（new invoke）时：
 * 父类代码块-父类实例变量-父类构造方法-子类代码块-子类实例变量-子类构造方法
 */
public class StaticTest extends Person {

    int a = 110;
    static int b = 112;

    public static void main(String[] args) {
        staticFunction();
    }

    static StaticTest staticTest = new StaticTest();

    static {
        System.out.println("1");
    }

    static int c = 119;

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
