package com.sh.thread;

public class MyTask implements Runnable {

    private int taskNum;

    public MyTask(int num){
        this.taskNum = num;
    }

    @Override
    public void run(){
        System.out.println("正在执行任务：" + taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(taskNum + "线程执行结束");
    }
}
