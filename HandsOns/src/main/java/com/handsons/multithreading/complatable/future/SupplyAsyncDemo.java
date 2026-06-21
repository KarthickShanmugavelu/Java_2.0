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

public class SupplyAsyncDemo {

    public List<Employee> getEmployeeListWithDefaultThreadPool() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newCachedThreadPool();
        CompletableFuture<List<Employee>> completableFuture = CompletableFuture.supplyAsync(()->{
            ObjectMapper objectMapper = new ObjectMapper();
            List<Employee> employeeList = null;
            try {
                employeeList = objectMapper.readValue(new File("employees.json"),new TypeReference<List<Employee>>(){});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return employeeList;
        },executor);
        return completableFuture.get();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        ObjectMapper objectMapper = new ObjectMapper();
        Long start = System.currentTimeMillis();
        List<Employee> employeeList = objectMapper.readValue(new File("employees.json"),new TypeReference<List<Employee>>(){});
        Long end = System.currentTimeMillis();
        System.out.println("Timetaken to read "+employeeList.size()+" sequentially is "+(end-start));
        start=System.currentTimeMillis();
        List<Employee> employeeList1 = supplyAsyncDemo.getEmployeeListWithDefaultThreadPool();
        end=System.currentTimeMillis();
        System.out.println("Timetaken to read "+employeeList1.size()+" sequentially is "+(end-start));

    }
}
