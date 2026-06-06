package com.handsons.multithreading.complatable.future;

import com.handsons.multithreading.complatable.future.dto.Employee;
import com.handsons.multithreading.complatable.future.repository.EmployeeRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CombineDependentCompletableFuturesDemo {

    public CompletableFuture<Employee> getEmployeeMatchingInputId(){
        return CompletableFuture.supplyAsync(()->{
            System.out.println("getEmployeeMatchingInputId(): "+Thread.currentThread().getName());
            try {
                return EmployeeRepository.fetchEmployees().stream()
                        .filter(employee -> "50-162-3249".equalsIgnoreCase(employee.getEmployeeId()))
                        .findAny().orElse(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Integer> getRatings(Employee employee){
        return CompletableFuture.supplyAsync(()->{
            System.out.println("getRatings(): "+Thread.currentThread().getName());
            return employee.getRating();
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        Long start = System.currentTimeMillis();
        Employee employee1 = EmployeeRepository.fetchEmployees().stream()
                .filter(employee -> "50-162-3249".equalsIgnoreCase(employee.getEmployeeId()))
                .findAny().orElse(null);
        Integer rating=employee1.getRating();
        Long end = System.currentTimeMillis();
        System.out.println("Sequential: Rating: "+rating+" Time taken: "+(end-start));
        CombineDependentCompletableFuturesDemo demo = new CombineDependentCompletableFuturesDemo();
        start = System.currentTimeMillis();
        CompletableFuture<Integer> rating2 = demo.getEmployeeMatchingInputId().thenCompose(demo::getRatings);
        end = System.currentTimeMillis();
        System.out.println("thenCompose(): Rating: "+rating2.get()+" Time taken: "+(end-start));
    }
}
