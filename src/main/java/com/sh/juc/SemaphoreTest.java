package com.sh.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private static final Logger logger = LoggerFactory.getLogger(SemaphoreTest.class);

    private static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
//        test1();
        test2();
    }

    private void desc() throws InterruptedException {
        /*
         * 返回此信号量可用的当前许可数。
         */
        int num = semaphore.availablePermits();

        /*
         * 从该信号量获取一个许可，阻塞直到有一个可用或线程被中断。
         * 若获取许可失败（剩余许可数不足或CAS设置新的许可数失败），则将当前线程放入同步阻塞队列中。
         * 公平模式下会先判断队列中是否有排队节点，有则排队，无则尝试获取许可。
         */
        semaphore.acquire();
        /*
         * 从该信号量获取n个许可，阻塞直到有n个可用或线程被中断。
         * 若获取许可失败（剩余许可数不足或CAS设置新的许可数失败），则将当前线程放入同步阻塞队列中。
         * 公平模式下会先判断队列中是否有排队节点，有则排队，无则尝试获取许可。
         */
        semaphore.acquire(3);
        /*
         * 释放许可证，将其返回给此信号量
         * CAS将此信号量的许可数加1，成功后开始自旋不断获取head节点，
         * 当head节点状态为signal时，唤醒head的下个节点线程
         */
        semaphore.release();
        /*
         * 释放许可证，将其返回给此信号量
         * CAS将此信号量的许可数加n，成功后开始自旋不断获取head节点，
         * 当head节点状态为signal时，唤醒head的下个节点线程
         */
        semaphore.release(3);
        /**
         * 尝试获取许可，获取成功将该信号量许可数减1，返回true
         * 获取失败直接返回false，当前线程不会排队
         */
        semaphore.tryAcquire();
    }

    public static void test1() {

        logger.info("===模拟30个人上只有3个坑位的厕所===");
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    logger.info("【{}】进去-----------", Thread.currentThread().getName());
                    Thread.sleep(5000);
                    semaphore.release();
                    logger.info("【{}】出来", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "threadName-" + i).start();
        }
    }

    public static void test2() {
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("a+++++++++++++++");
                Thread.sleep(3000);
                logger.info("a---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire(3);
                logger.info("b+++++++++++++++");
                Thread.sleep(5000);
                logger.info("b---------------");
                semaphore.release(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("c+++++++++++++++");
                Thread.sleep(5000);
                logger.info("c---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("d+++++++++++++++");
                Thread.sleep(10000);
                logger.info("d---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("e+++++++++++++++");
                Thread.sleep(5000);
                logger.info("e---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("f+++++++++++++++");
                Thread.sleep(5000);
                logger.info("f---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("g+++++++++++++++");
                Thread.sleep(5000);
                logger.info("g---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                semaphore.acquire();
                logger.info("h+++++++++++++++");
                Thread.sleep(5000);
                logger.info("h---------------");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

//    private void doAcquireSharedInterruptibly(int arg) throws InterruptedException {
//        final Node node = addWaiter(Node.SHARED);             // 1. 将当前的线程封装成 Node 加入到 Sync Queue 里面
//        boolean failed = true;
//
//        try {
//            for (; ; ) {
//                final Node p = node.predecessor();            // 2. 获取当前节点的前继节点 (当一个n在 Sync Queue 里面, 并且没有获取 lock 的 node 的前继节点不可能是 null)
//                if (p == head) {
//                    int r = tryAcquireShared(arg);            // 3. 判断前继节点是否是head节点(前继节点是head, 存在两种情况 (1) 前继节点现在占用 lock (2)前继节点是个空节点, 已经释放 lock, node 现在有机会获取 lock); 则再次调用 tryAcquireShared 尝试获取一下
//                    if (r >= 0) {
//                        setHeadAndPropagate(node, r);         // 4. 获取 lock 成功, 设置新的 head, 并唤醒后继获取  readLock 的节点
//                        p.next = null; // help GC
//                        failed = false;
//                        return;
//                    }
//                }
//
//                if (shouldParkAfterFailedAcquire(p, node) &&  // 5. 调用 shouldParkAfterFailedAcquire 判断是否需要中断(这里可能会一开始 返回 false, 但再次进去后直接返回 true(主要和前继节点的状态是否是 signal))
//                        parkAndCheckInterrupt()) {            // 6. 现在lock还是被其他线程占用 那就睡一会, 返回值判断是否这次线程的唤醒是被中断唤醒
//                    throw new InterruptedException();         // 7. 若此次唤醒是 通过线程中断, 则直接抛出异常
//                }
//            }
//        } finally {
//            if (failed) {                                     // 8. 在整个获取中出错(比如线程中断/超时)
//                cancelAcquire(node);                          // 9. 清除 node 节点(清除的过程是先给 node 打上 CANCELLED标志, 然后再删除)
//            }
//        }
//    }

    /*
     * 获取许可失败后，当前线程以共享模式封装成Node节点加入同步队列队尾
     * 然后开始自旋获取前节点，并判断前节点是否是head节点
     * 如果前节点是head节点，尝试获取锁，获取成功更新head节点退出方法
     *
     * 如果前节点是head节点，但是获取锁失败，或者前节点不是head节点
     * 则调用parkAndCheckInterrupt()方法挂起当该线程
     *
     * 等待后续被唤醒重新开始自旋，知道满足前节点是head，并获取到锁
     *
     */


    // 总共3个许可 各线程申请许可数  a:1 b:3 c:1 d:1 e:1 f:1 g:1 h:1

    // a拿到一个许可，b获取失败加入队列并开始自旋后挂起，此时c、d并发获取到许可，e、f、g、h加入队列
    // a释放后通知b,b唤醒开始自旋尝试获取许可，但是许可不足重新挂起
    // c释放后通知b,b唤醒开始自旋尝试获取许可，但是许可不足重新挂起
    // d释放后通知b,b唤醒开始自旋尝试获取许可，获取成功更新head
    // b释放后通知e,e唤醒开始……

}
