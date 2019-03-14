package com.sh.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Date: 2018-11-24 10:19
 * @Auther: 季小沫的故事
 */
public class ThreadTest implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ThreadTest.class);

    public static String ani;
    private String smile;

    public ThreadTest(String smile) {
        this.smile = smile;
    }

    public void run() {

        int i = 0;
        while (++i<10){

            log.info("线程{} ani={} smile={}",Thread.currentThread().getName(),ani,smile);

        }

    }


    public static void main(String[] args){

        ThreadTest test1 = new ThreadTest("songhuihui");
        System.out.println(test1.smile);
        test1.smile = "shh-smile";
        System.out.println(test1.smile);
//        ThreadTest test2 = new ThreadTest("chenchen");
//        test2.smile = "chen-smile";
//        new Thread(test1).start();
//        new Thread(test2).start();
    }
}
