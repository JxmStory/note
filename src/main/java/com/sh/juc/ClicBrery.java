package com.sh.juc;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClicBrery {

    static class Runner implements Runnable {
        private CyclicBarrier barrier;
        private String name;

        public Runner(CyclicBarrier barrier, String name) {
            this.barrier = barrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000 * (new Random()).nextInt(5));
                System.out.println(name + "准备好了");
                // await方法使当前线程阻塞（挂起）
                barrier.await();
                // 当上面的await执行后 下面两个都阻塞中 直到其他线程await的数量达到cyclicbarrier定义的数量后 才会执行下面的语句（但已经没有意义了）
                barrier.await();
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name + "GO!!!");
        }
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(new Thread(new Runner(barrier, "张三")));
        executor.execute(new Thread(new Runner(barrier, "李四")));
        executor.execute(new Thread(new Runner(barrier, "王五")));
        executor.shutdown();
    }
}