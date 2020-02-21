package com.sh.thread;

public class MyTask extends Thread {

    private int taskNum;

    public MyTask(int num){
        super.setName("MyTask");
        this.taskNum = num;
    }

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + "正在执行任务:" + taskNum);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行结束:" + taskNum );
    }
}
