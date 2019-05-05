package com.sh.thread;

import java.util.concurrent.*;

public class ExecutorTest {

    public static void main(String[] args) {

        /**
         * 自定义创建线程池 核心线程数为5 最大线程数为10
         * 线程空闲时长200 单位毫秒
         * 排队策略为基于数组的大小为5的队列
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        for(int i=0; i<15; i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行完的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();

        /**
         * 创建单个线程的线程池
         */
//        ExecutorService singleExecutor =  Executors.newSingleThreadExecutor();
//        for(int i=0; i<10; i++){
//            singleExecutor.submit(new MyTask(1));
//        }
//        singleExecutor.shutdown();

        /**
         * 创建固定大小的线程池
         */
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
//        for(int i=0; i<10; i++){
//            fixedThreadPool.submit(new MyTask(i));
//        }
//        fixedThreadPool.shutdown();

        /**
         * 创建缓存线程池
         */
//        ExecutorService cachedThreadExecutor = Executors.newCachedThreadPool();
//        for(int i=0; i<10; i++){
//            cachedThreadExecutor.submit(new MyTask(i));
//        }
//        cachedThreadExecutor.shutdown();

    }
}
