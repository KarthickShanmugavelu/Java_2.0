package com.handsons.multithreading;

public class MultiThreadMain {
    public static void main(String[] args) {
        var thread = Thread.currentThread();
        printThreadInfo(thread);
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
