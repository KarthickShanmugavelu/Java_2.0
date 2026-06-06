package com.handsons.multithreading.complatable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AllOfInCompletableFutureDemo {

    private void simulateDelay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public CompletableFuture<String> fetchWeather(){
        return CompletableFuture.supplyAsync(()->{
            System.out.println("fetchWeather: "+Thread.currentThread().getName());
            simulateDelay(2000);
            return "Today will be Sunny upto 100`F";
        });
    }

    public CompletableFuture<String> fetchNewsHeadlines(){
        return CompletableFuture.supplyAsync(()->{
            System.out.println("fetchNewsHeadlines: "+Thread.currentThread().getName());
            simulateDelay(3000);
            return "JDK 26 released";
        });
    }

    public CompletableFuture<String> fetchStockPrice(){
        return CompletableFuture.supplyAsync(()->{
            System.out.println("fetchNewsHeadlines: "+Thread.currentThread().getName());
            simulateDelay(1500);
            return "The NIFTY 50 closed at 23,366.70 (down 0.21%)";
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AllOfInCompletableFutureDemo demo = new AllOfInCompletableFutureDemo();
        CompletableFuture<String> weather = demo.fetchWeather();
        CompletableFuture<String> news = demo.fetchNewsHeadlines();
        CompletableFuture<String> stocks = demo.fetchStockPrice();
        System.out.println(weather.get() +" \n"+news.get()+" \n"+stocks.get());
        CompletableFuture<Void> allOfResult = CompletableFuture.allOf(weather, news, stocks);
       allOfResult.thenRun(()->{
           try {
               System.out.println("=========================================");
               System.out.println(weather.get() +" \n"+news.get()+" \n"+stocks.get());
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           } catch (ExecutionException e) {
               throw new RuntimeException(e);
           }
       });

    }
}

