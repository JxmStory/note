package com.sh.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        cdOrder.await();
                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
            cdOrder.countDown();
            System.out.println("裁判" + Thread.currentThread().getName() + "已发送口令，正在等待所有选手到达终点");
            cdAnswer.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    private void desc() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        /**
         * 使当前线程阻塞直到锁计数器state到0
         * 如果state==0则直接返回
         * 否则将当前线程以共享模式加入到同步阻塞队列
         */
        countDownLatch.await();
        /**
         * 使当前线程阻塞直到锁计数器state到0或超过等待时间
         * 如果state==0则直接返回true
         * 否则将当前线程以共享模式加入到同步阻塞队列并自旋获取锁
         * 自旋过程中如果state!=0且未过等待时间会将当前线程挂起剩余等待时间
         * 等待唤醒或超时变为就绪状态重新进入自旋
         * 自旋过程中如果state==0则返回true，如果state!=0但超过等待时间则返回false
         * 如果timeout<=0会尝试获取锁，获取不到立马返回false
         */
        countDownLatch.await(3000, TimeUnit.MILLISECONDS);
        /**
         * 使CAS方式将锁计数器state减1
         * 如果修改成功则调用doReleaseShared方法尝试唤醒头节点的下个节点
         */
        countDownLatch.countDown();


        /**
         * 关于共享锁的释放过程 两种方法都会进入到 doReleaseShared
         *
         * 1.线程获取不到锁时会以共享模式加入到同步阻塞队列中
         *   然后开始自旋获取锁，获取不到会将当前线程挂起等待唤醒，被唤醒后继续自旋
         *   获取到锁后将当前节点node置为头节点head，然后调用doReleaseShared继续唤醒后续节点
         * 2.释放锁的时候会调用doReleaseShared方法
         *
         * doReleaseShared方法中
         * 自旋获取头节点head，当头节点不是尾节点且状态是SIGNAL时，CAS设置头节点状态标识为0，
         * 设置成功后调用unparkSuccessor(h)方法唤醒头节点的下一个节点。
         * 被唤醒的线程开始自旋获取锁，获取到锁后将当前节点置为头节点，然后调用doReleaseShared继续唤醒后续节点
         * 
         * 在自旋中如果头节点未发生过变化（1.无后续节点或头节点非SIGNAL状态 2.已唤醒下个节点但下个节点还未成功重置头节点）
         * 此时会退出自旋
         * 
         * 在自旋过程中头节点已结变化（说明已经唤醒下个节点且下个节点已成功获取到锁将自己置为头节点）
         * 此时会获取新的头节点继续进行后续节点的唤醒 ------- 与此同时，被唤醒的那个节点在拿到锁后也会调用doReleaseShared方法唤醒后续节点
         * 
         * 至到后续某个节点获取不到锁无法重置头节点从而退出自旋
         *
         */
//        private void doReleaseShared() {
//            for (;;) {
//                Node h = head;
//                if (h != null && h != tail) {
//                    int ws = h.waitStatus;
//                    if (ws == Node.SIGNAL) {
//                        if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
//                            continue;            // loop to recheck cases
//                        unparkSuccessor(h);
//                    }
//                    else if (ws == 0 &&
//                            !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
//                        continue;                // loop on failed CAS
//                }
//                if (h == head)                   // loop if head changed
//                    break;
//            }
//        }
    }
}