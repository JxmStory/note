package com.sh.test;

import java.util.Random;

/**
 * 类初始化（五种主动引用）时：
 * 父类静态代码块-父类静态变量-子类静态代码块-子类静态变量
 *
 * 类实例化（new invoke）时：
 * 父类代码块-父类实例变量-父类构造方法-子类代码块-子类实例变量-子类构造方法
 */
public class LoadClass {

    // 测试时请注释其他行代码
    public static void main(String[] args) {
        // 引用编译期静态常量不会触发类的初始化
        System.out.println(Children.c);
        // 引用非编译期的静态常量会触发类的初始化
        System.out.println(Children.r);
        // 子类引用父类静态变量，不会触发子类初始化
        System.out.println(Children.a);
        // 子类引用父类静态方法，不会触发子类初始化
        System.out.println(Children.stcFun());
        // 创建对象数组不会触发实例化和初始化
        Children[] childrens = new Children[]{};


    }
}

class Parent {
    static int a = 0;
    static {
        System.out.print("p");
    }
    public static String stcFun() {return "abc";}
}

class Children extends Parent {
    // 已知的静态常量会在编译期放到jvm方法区的常量池中
    static final int c = 300;
    // 未知的静态常量则在类初始化后放到常量池中
    static final int r = new Random().nextInt(3);
    static {
        System.out.print("c");
        System.out.print(r);
    }
}