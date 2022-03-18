package com.sh.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @Description:
 * @Date: 2018-11-30 17:02
 * @Author: micomo
 */
public class Producer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

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
            try {
                produce(i);
            } catch (InterruptedException ex) {
                logger.info("异常：{}", ex);
            }
        }
    }

    private void produce(int i) throws InterruptedException {

        logger.info("生产者：第{}次加工开始", i);
        //wait if queue is full
        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                logger.info("生产者：队列已满，暂定加工");
                sharedQueue.wait(); //wait() 释放当前的锁 进入等待状态
            }
        }

        //producing element and notify consumers
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            logger.info("生产者：添加队列成功");
            sharedQueue.notifyAll(); //notify/notifyAll() 只唤醒其他线程 不释放锁
        }

        logger.info("生产者：第{}次加工结束", i);
    }
}

