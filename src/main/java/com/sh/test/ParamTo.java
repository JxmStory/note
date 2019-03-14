package com.sh.test;

import com.sh.entity.User;

/**
 * @Description:
 * @Date: 2018-12-06 10:21
 * @Auther: 季小沫的故事
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

    public static void updateUser(User user){


        user.setUsername("funck");
        System.out.println(user.hashCode());
    }


    public static void main(String[] args){

        User user = new User();
        System.out.println(user.hashCode());
        user.setUsername("abvca");
        updateUser(user);
        System.out.println(user.getUsername());

    }
}
