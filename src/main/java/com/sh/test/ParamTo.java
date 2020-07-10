package com.sh.test;

import com.sh.entity.User;

/**
 * @Description: 其实java只有值传递，引用传递其实也是传递了对象引用的拷贝（传递对象地址）
 * @Date: 2018-12-06 10:21
 * @Author: micomo
 */
public class ParamTo {

    // 仅返回值类型不同的重载是不允许的
    public String abc(String username, int age){
        return "abc";
    }

    // 重载的方法名相同 参数和返回值类型可以不同
    public int abc(int age, String username){
        return 1;
    }

    //传递的是对象引用的拷贝 即有两个引用指向内存中同一对象
    public static void updateUser(User user){
        user.setUsername("funck");
        System.out.println(user.hashCode());
    }

    //传递的是对象引用的拷贝 但是String是final修饰的 重新赋值会新创建对象
    public static void updateStr(String str){
        str = "aaa";
        System.out.println(str.hashCode());
    }

    //基本数据类型传递的是值 任何修改都不会改变外部的值
    public static void updateInt(int num){
        num = num + 100;
    }

    //数组、集合、对象传递的都是引用的拷贝 修改的都是同一个内存中的内容
    public static void updateArray(String[] arr){
        arr[1] = "ccc";
    }

    public static void main(String[] args){

        User user = new User();
        user.setUsername("abvca");
        System.out.println(user.hashCode());
        updateUser(user);
        System.out.println(user.getUsername());

        String str = "abcdefg";
        System.out.println(str.hashCode());
        updateStr(str);
        System.out.println(str);

        int num = 1;
        updateInt(num);
        System.out.println(num);

        String[] arr = {"aaa","bbb"};
        updateArray(arr);
        System.out.println(arr[1]);

    }
}
