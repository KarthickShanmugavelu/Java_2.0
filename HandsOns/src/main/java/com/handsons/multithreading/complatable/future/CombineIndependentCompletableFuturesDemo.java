package com.handsons.multithreading.complatable.future;

import com.handsons.multithreading.complatable.future.dto.Employee;
import com.handsons.multithreading.complatable.future.repository.EmployeeRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CombineIndependentCompletableFuturesDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        Long start = System.currentTimeMillis();
        Map<String,Long> empGenderAndCountMap = EmployeeRepository.fetchEmployees().stream()
                .collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()));
        List<String> emailsList = EmployeeRepository.fetchEmployees().stream().map(Employee::getEmail).collect(Collectors.toList());
        System.out.println(empGenderAndCountMap+" "+emailsList);
        Long end = System.currentTimeMillis();
        System.out.println("Sequential time taken: "+(end - start));


        start = System.currentTimeMillis();
        CompletableFuture<Map<String,Long>> empMapFuture = CompletableFuture.supplyAsync(()->{
            System.out.println("empMapFuture: "+Thread.currentThread().getName());
            try {
               return EmployeeRepository.fetchEmployees().stream()
                        .collect(Collectors.groupingBy(
                                Employee::getGender,Collectors.counting()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<List<String>> emailFuture = CompletableFuture.supplyAsync(()->{
            try {
                return EmployeeRepository.fetchEmployees().stream()
                        .map(Employee::getEmail)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> thenCombineResult = empMapFuture.thenCombine(emailFuture, (empMap, emails) -> empMap + " " + emails);
        System.out.println(thenCombineResult.get());
        end = System.currentTimeMillis();
        System.out.println("thenCombine time taken: "+(end - start));
    }
}
