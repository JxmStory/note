package com.sh.test;

import java.util.Vector;

/**
 * @Description:
 * @Date: 2018-11-30 17:04
 * @Auther: 季小沫的故事
 */
public class ProducerConsumerSolution {

    public static void main(String[] args) {
        Vector sharedQueue = new Vector();
        int size = 4;
        Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
        prodThread.start();
        consThread.start();
    }
}

