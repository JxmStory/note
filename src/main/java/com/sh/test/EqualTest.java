package com.sh.test;

import com.sh.common.Color;
import com.sh.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Date: 2018-11-27 17:06
 * @Author: micomo
 */
public class EqualTest {

    /**
     *  ==          基本类型数据比较的是值  引用（对象）比较的是地址
     *  equals      比较的是引用（对象）地址，但是String Date Double Integer对equals进行了重写，比较的是值
     * @date: 2018-11-27 17:24
     * @auther: 季小沫的故事
     */

    private static final Logger logger = LoggerFactory.getLogger(EqualTest.class);

    public static void equalString() {

        int a = 5;
        int b = 5;


        String s1 = "abc";
        String s2 = "aaa";
        String s3 = "aaa";//s2 s3引用的是同一个对象 "aaa"
        String s4 = new String("aaa");//s4的引用指向的是新建的对象

        if(s1 == s2) {
            logger.info("{}", "s1 == s2");
        } else {
            logger.info("{}", "s1 != s2");
        }

        if(s2 == s3){
            logger.info("s2==s3");
        } else {
            logger.info("s2/s3");
        }

        if(s2.equals(s3)) {
            logger.info("s2 s3是一个对象");
        } else {
            logger.info("s2 s3不是一个对象");
        }

        if(s3 == s4){
            logger.info("s3 == s4");
        } else {
            logger.info("s3 != s4");
        }

        if(s3.equals(s4)){
            logger.info("String 的 equals 方法比较的是值 虽然s3 s4不是同一对象 但是他们的值相同 所有equals为true");
        }
        String str1 = new String("str");
        String str2 = new String("str");
        if(str1 == str2) {
            System.out.println("str1 == str2");
        } else {
            System.out.println("str1 != str2");
        }
        if(str1.equals(str2)){
            System.out.println("str1.equals.str2");
        }

        User u1 = new User();
        User u2 = new User();//u1和u2不是一个对象
        User u3 = u1;//把u1的引用给u3  所以u1和u3是同一个对象
        if (u1 == u2) {
            logger.info("u1 u2是一个对象");
        } else {
            logger.info("u1 u2不是一个对象");
        }
        if(u1 == u3) {
            logger.info("u1==u3");
        } else {
            logger.info("u1!=u3");
        }
    }


    /**
     * 所有包装类型对象值的比较，全部使用equals判断
     * eg：对于Integer对象赋值，数值在-128~127之间的对象是在IntegerChe.chache产生，会复用已有对象
     *     这个区间值的判断可以用==，在此之外的值的对象会在堆上产生，并不会复用。
     */
    public static void equalInteger() {
        Integer a = 100, b = 100;
        Integer c = 1000, d = 1000;
        System.out.println(a == b);
        System.out.println(c == d);
        System.out.println(a.equals(b));
        System.out.println(c.equals(d));
        System.out.println(1000 == 1000);
        System.out.println(1 == 1.0);
        System.out.println(c.hashCode()+"|"+d.hashCode());
        System.out.println((1*0.3) == 0.3);
        System.out.println((3*0.1) == 0.3);
        System.out.println(3*0.1);
    }

    public static void main(String[] args) {
        equalString();
//        equalInteger();
//        String shh1 = "shh";
//        String shh2 = new String("shh");
//        System.out.println(shh1==shh2); // false 使用new表示新建了一个对象 ==比较两个对象地址
//        System.out.println(shh1.equals(shh2)); //true String的equals方法被重写 比较的是字符串每个字符是否相等
//        Color color = Color.valueOf("RED");
//        System.out.println(color.getValue());
        Integer a = 10;
        Integer b = Integer.valueOf(10);
        Integer c = new Integer(10);
        int d = 10;
        Integer e = a;
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(a==d);
        System.out.println(a==e);
        System.out.println(b==c);
        System.out.println(b==d);
        System.out.println(b==e);
        System.out.println(c==d);
        System.out.println(c==e);
        System.out.println(d==e);
    }
}
