package com.sh.test;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description:
 * @Date: 2018-11-30 17:04
 * @Auther: 季小沫的故事
 */
public class Consumer implements Runnable {

    private final Vector sharedQueue;
    private final int SIZE;

    public Consumer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
                System.out.println("Consumer: " + consume());
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int consume() throws InterruptedException {

        //wait if queue is empty
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedQueue.size());
                sharedQueue.wait();
            }
        }

        //otherwise consume element and notify waiting producer
        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            return (Integer) sharedQueue.remove(0);
        }
    }
}

