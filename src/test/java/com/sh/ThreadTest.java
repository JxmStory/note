package com.sh;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sh.thread.MyTask;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ThreadTest {

    @Test
    public void fixExcetors() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5, threadFactory);
        fixedThreadPool.submit(new MyTask(1));
        fixedThreadPool.submit(new MyTask(2));
        fixedThreadPool.submit(new MyTask(3));
        fixedThreadPool.submit(new MyTask(4));
        fixedThreadPool.submit(new MyTask(5));
        fixedThreadPool.submit(new MyTask(6));
        fixedThreadPool.submit(new MyTask(7));
        fixedThreadPool.submit(new MyTask(8));


        try {
            // 不在接受新线程，等所有线程执行完关闭线程池
            fixedThreadPool.shutdown();

            /**
             * awaitTermination() 在阻塞时间内除非所有线程都执行完毕才会提前返回true
             * 如果到了规定的时间，线程池中的线程并没有全部结束返回false
             */
            if (!fixedThreadPool.awaitTermination(3, TimeUnit.SECONDS)) {
                // 10s内没有执行完所有线程，立即停止并取消后续线程
                fixedThreadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            fixedThreadPool.shutdownNow();
            e.printStackTrace();
        }
    }

}
