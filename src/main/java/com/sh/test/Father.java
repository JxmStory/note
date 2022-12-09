package com.sh.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Father {

    public Father() {
        System.out.println("this is father");
    }

    public void say() {
        System.out.println("father");
    }

    public static void main(String[] args) {
        Class<Son> clazz = Son.class;
        try {
            // getMethod 可以获取自身、父类所有public方法
            Method method = clazz.getMethod("say");
            method.invoke(clazz.newInstance());
            // getDeclaredMethod 可以获取自身所有方法（包括private和protect），但是获取不到父类方法
            Method m2 = clazz.getDeclaredMethod("say");
            m2.invoke(clazz.newInstance());
        } catch (NoSuchMethodException | InvocationTargetException
                 | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}

class Son extends Father {
    public Son() {
        System.out.println("this is son");
    }

    public void sonSay() {
        System.out.println("son");
    }
}
