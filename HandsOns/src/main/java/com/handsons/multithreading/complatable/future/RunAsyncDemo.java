package com.handsons.multithreading.complatable.future;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handsons.multithreading.complatable.future.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsyncDemo {

    public Void saveEmployees(File file){
        ObjectMapper objectMapper = new ObjectMapper();
        Long start = System.currentTimeMillis();
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Employee> employeeList =  objectMapper.readValue(file, new TypeReference<List<Employee>>(){});
                    System.out.println("Method: saveEmployees. Thread: "+Thread.currentThread().getName());
                    System.out.println("Method: saveEmployees. Employee Size: "+employeeList.size());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            Long end = System.currentTimeMillis();
            System.out.println("Time taken to read 1000 employees using runAsyncMethod of " +
                    "CompletableFuture that uses Global ForkJoin Thread Pool: "+ (end-start));
            return runAsyncFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Void saveEmployeesWithCustomExecutorAndLambda(File file){
        ObjectMapper objectMapper = new ObjectMapper();
        Long start = System.currentTimeMillis();
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(()->{
                try {
                    List<Employee> employeeList =  objectMapper.readValue(file, new TypeReference<List<Employee>>(){});
                    System.out.println("Method: saveEmployees. Thread: "+Thread.currentThread().getName());
                    System.out.println("Method: saveEmployees. Employee Size: "+employeeList.size());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        ,executor);
        try {
            Long end = System.currentTimeMillis();
            System.out.println("Time taken to read 1000 employees using runAsyncMethod of " +
                    "CompletableFuture that uses Executor Thread Pool: "+ (end-start));
            return runAsyncFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Long start = System.currentTimeMillis();
        List<Employee> employeeList =  objectMapper.readValue(new File("employees.json"), new TypeReference<List<Employee>>(){});
        Long end = System.currentTimeMillis();
        System.out.println("Time taken to read "+employeeList.size()+" employees sequentially " +(end-start));
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployees(new File("employees.json"));
        runAsyncDemo.saveEmployeesWithCustomExecutorAndLambda(new File("employees.json"));
    }
}
