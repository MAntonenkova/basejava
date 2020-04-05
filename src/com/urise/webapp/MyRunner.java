package com.urise.webapp;

class MyRunner {
    private final String line1 = "First Thread";
    private final String line2 = "Second Thread";

    void forFirstThread() {
        for (int i = 0; i < 1000; i++) {
            synchronized (line1) {
                synchronized (line2) {
                    System.out.println(line1);
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void forSecondThread() {
        for (int i = 0; i < 1000; i++) {
            synchronized (line2) {
                synchronized (line1) {
                    System.out.println(line2);
                }
            }
        }
    }
}
