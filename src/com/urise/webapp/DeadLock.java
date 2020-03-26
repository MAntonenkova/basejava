package com.urise.webapp;

public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        MyRunner myRunner = new MyRunner();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myRunner.forFirstThread();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myRunner.forSecondThread();
            }
        });

        thread1.start();
        thread2.start();
    }
}
