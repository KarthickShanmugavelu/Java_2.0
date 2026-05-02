package com.handsons.multithreading.thread.and.runnable;

import java.util.concurrent.TimeUnit;

public class MultiThreadMain {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        printThreadInfo(thread);
        thread.setName("MainGuy");
        thread.setPriority(Thread.MAX_PRIORITY);
        printThreadInfo(thread);

        CustomThreadClass customThreadClass = new CustomThreadClass();
        customThreadClass.start();

        Runnable myRunnable = ()->{
            for(int i=0;i<8;i++){
                System.out.println(" Custom Thread using Runnable interface ");
                try{
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();

        for(int i=0;i<4;i++){
            System.out.println(" MainThread ");
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printThreadInfo(Thread thread){
        System.out.println("-------------------------------");
        System.out.println("Thread class: "+thread.getClass().getName());
        System.out.println("Thread ID: "+thread.getId());
        System.out.println("Thread Name: "+thread.getName());
        System.out.println("Thread Priority: "+thread.getPriority());
        System.out.println("Thread State: "+thread.getState());
        System.out.println("Thread Group: "+thread.getThreadGroup());
        System.out.println("Is Thread Alive :"+thread.isAlive());
        System.out.println("-------------------------------");
    }
}
