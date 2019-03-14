package com.sh.test;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description:
 * @Date: 2018-11-30 17:02
 * @Auther: 季小沫的故事
 */
public class Producer implements Runnable {

    private final Vector sharedQueue;
    private final int SIZE;

    public Producer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        for (int i = 0; i < 7; i++) {
            System.out.println("Produced:" + i);
            try {
                produce(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void produce(int i) throws InterruptedException {

        //wait if queue is full
        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                System.out.println("Queue is full " + Thread.currentThread().getName()
                        + " is waiting , size: " + sharedQueue.size());
                sharedQueue.wait(); //wait() 释放当前的锁 进入等待状态
            }
        }

        //producing element and notify consumers
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            sharedQueue.notifyAll(); //notify/notifyAll() 只唤醒其他线程 不释放锁
        }
    }
}

