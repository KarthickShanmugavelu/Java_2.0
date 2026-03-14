package com.handsons.multithreading;

public class CustomThreadClass extends Thread{

    @Override
    public void run() {
        for(int i=1;i<=5;i++){
            System.out.println(" Custom Thread by extending Thread class ");
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
