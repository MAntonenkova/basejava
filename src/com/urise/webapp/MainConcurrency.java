package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    //private int counter;
    private AtomicInteger atomicCounter = new AtomicInteger();
    // private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                //           throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();

        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        //   List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(() ->
                    //     Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    latch.getCount();
                }
            });
            //    thread.start();
            //     threads.add(thread);
        }

      /*  threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        );*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(mainConcurrency.atomicCounter.get());

  /*      final String lock1 = "lock1";
        final String lock2 = "lock2";
        deadLock(lock1, lock2);
        deadLock(lock2, lock1);*/

    }

/*
    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }
*/

    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
   /*     lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }*/
        atomicCounter.incrementAndGet();
//                wait();
//                readFile
//                ...
//        }
    }
}
