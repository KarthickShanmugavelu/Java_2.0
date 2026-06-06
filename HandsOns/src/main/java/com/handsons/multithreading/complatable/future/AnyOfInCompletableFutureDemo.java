package com.handsons.multithreading.complatable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AnyOfInCompletableFutureDemo {

    public CompletableFuture<String> stockPriceApi1(){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(1000);
            System.out.println("stockPriceApi1(): "+Thread.currentThread().getName());
            return "Nifty 50: 23,366.70 (down approx. 0.21%)";
        });
    }

    public CompletableFuture<String> stockPriceApi2(){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(500);
            System.out.println("stockPriceApi2(): "+Thread.currentThread().getName());
            return "BSE Sensex: 74,243.34 (down approx. 0.16%)";
        });
    }

    public CompletableFuture<String> stockPriceApi3(){
        return CompletableFuture.supplyAsync(()->{
            simulateDelay(2500);
            System.out.println("stockPriceApi3(): "+Thread.currentThread().getName());
            return "Nifty Bank: 54,496.25 (up approx. 0.35%)";
        });
    }

    private void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AnyOfInCompletableFutureDemo demo = new AnyOfInCompletableFutureDemo();
        CompletableFuture<String> api1 = demo.stockPriceApi1();
        CompletableFuture<String> api2 = demo.stockPriceApi2();
        CompletableFuture<String> api3= demo.stockPriceApi3();
        CompletableFuture<Object> anyOfResult = CompletableFuture.anyOf(api3, api1, api2);
        System.out.println(anyOfResult.get());
    }
}
