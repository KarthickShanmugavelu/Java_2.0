package com.handsons.multithreading.thread.and.runnable;

public class ManipulatingThreads {
    public static void main(String[] args) {
        System.out.println("Starting Main Thread");
        System.out.println("Thread name: " + Thread.currentThread().getName() + " Thread State: " + Thread.currentThread().getState());

        try {
            System.out.println("Main thread is paused for 1 second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Inside run of new thread. Thread name: " + threadName + " .Thread state: " + Thread.currentThread().getState());
            System.out.println(threadName + " will run for 10 iterations.");
            for (int i = 0; i < 10; i++) {
                System.out.println("  Thread name: " + threadName + ". Iteration number: " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + threadName + " is interrupted ");
                    return;
                }
            }
            System.out.println("Thread " + threadName + " completed");
        });
        System.out.println("Starting new thread: " + thread.getName());
        thread.start();

        long now = System.currentTimeMillis();
        while (thread.isAlive()) {
            System.out.println("Waiting for new thread " + thread.getName() + " to complete");
            try {
                Thread.sleep(1000);
                System.out.println("Checking state of new thread. Thread name: "+thread+" Thread state: "+thread.getState());
                if (System.currentTimeMillis() - now > 2000) {
                    thread.interrupt();
                    System.out.println("After interrupting new thread.Checking state of new thread. Thread name: "+thread+" Thread state: "+thread.getState());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("After completion of new thread. Thread name: " + thread.getName() + " .Thread state: " + thread.getState());
        System.out.println("Main thread completed");
        System.out.println("After completion of main thread. Thread name: " + Thread.currentThread().getName() + " .Thread state: " + Thread.currentThread().getState());
    }

}
