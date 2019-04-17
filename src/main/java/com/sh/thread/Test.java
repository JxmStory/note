package com.sh.thread;

import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) {

        /**
         * 创建线程池 核心线程数为5 最大线程数为10
         * 线程空闲时长200 单位毫秒
         * 排队策略为基于数组的大小为5的队列
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        ExecutorService singleExecutor =  Executors.newSingleThreadExecutor();
        for(int i=0; i<10; i++){
            singleExecutor.submit(new MyTask(1));
        }
        singleExecutor.shutdown();

//        for(int i=0; i<15; i++){
//            MyTask myTask = new MyTask(i);
//            executor.execute(myTask);
//            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
//                    executor.getQueue().size()+"，已执行完的任务数目："+executor.getCompletedTaskCount());
//        }
//        executor.shutdown();
    }
}
