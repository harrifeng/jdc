package org.hfeng.threads._01;

public class PrintABAB {
    public static void main(String[] args) {
        System.out.println("-------------");
        Object lock = new Object();
        Thread t1 = new Thread(new Printer(lock, "A"));
        Thread t2 = new Thread(new Printer(lock, "B"));
        t1.start();
        t2.start();
    }
}

class Printer implements Runnable {

    private String content;
    private Object lock;

    public Printer(Object l, String s) {
        lock = l;
        content = s;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            synchronized (lock) {
                System.out.println(content);
            }
        }
    }
}
