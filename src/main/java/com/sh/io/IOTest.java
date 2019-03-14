package com.sh.io;

import java.io.IOException;
import java.sql.SQLOutput;

/**
 * @Auther: admin
 * @Date: 2019/1/16 15:59
 * @Description:
 */
public class IOTest {

    public static void main(String args[]){
        int len;
        try {
            System.out.println("请输入：");
            while((len = System.in.read()) != -1){
                System.out.print((char)len);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
