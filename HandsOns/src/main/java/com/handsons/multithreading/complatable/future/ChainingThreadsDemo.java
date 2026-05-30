package com.handsons.multithreading.complatable.future;

import com.handsons.multithreading.complatable.future.dto.Employee;
import com.handsons.multithreading.complatable.future.repository.EmployeeRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class ChainingThreadsDemo {
StringBuilder sb = new StringBuilder();
    public void sendRemainderToEmployeesSequentially() throws IOException {

        List<Employee> employeeList = EmployeeRepository.fetchEmployees();
        List<Employee> newJoiners = employeeList.stream()
                .filter(employee -> "TRUE".equalsIgnoreCase(employee.getNewJoiner()))
                .collect(Collectors.toList());
        List<Employee> newJoinersWithPendingTraining = newJoiners.stream()
                .filter(employee -> "TRUE".equalsIgnoreCase(employee.getLearningPending()))
                .collect(Collectors.toList());
        List<String> emails=newJoinersWithPendingTraining.stream()
                .map(employee -> employee.getEmail())
                .collect(Collectors.toList());
        emails.forEach(email->{
           // System.out.print("Sending training remainder email to: "+email);
            sb.append(email);
        });
        sb.setLength(0);
    }

    public void sendRemainderToEmployeeWithDefaultExecutor(){
        CompletableFuture.supplyAsync(()->{
            System.out.println("Fetching Employee: "+Thread.currentThread().getName());
            try {
                return EmployeeRepository.fetchEmployees();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).thenApplyAsync((employees)->{
            System.out.println("Filtering new joiners: "+Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equalsIgnoreCase(employee.getNewJoiner()))
                    .collect(Collectors.toList());
        }).thenApplyAsync((employees)->{
            System.out.println("Filtering new joiners with incomplete training: "+Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equalsIgnoreCase(employee.getLearningPending()))
                    .collect(Collectors.toList());
        }).thenApplyAsync((employees)->{
            System.out.println("Getting emails: "+Thread.currentThread().getName());
            return employees.stream()
                    .map(employee -> employee.getEmail())
                    .collect(Collectors.toList());
        }).thenAcceptAsync((emails)-> {
            emails.forEach(email->{
                //System.out.print("Sending training remainder email to: "+email)
                sb.append(email);
            });
        });
        sb.setLength(0);
    }

    public void sendRemainderToEmployeeWithCustomExecutor(){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture.supplyAsync(()->{
            System.out.println("Fetching Employee: "+Thread.currentThread().getName());
            try {
                return EmployeeRepository.fetchEmployees();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        },executor).thenApplyAsync((employees)->{
            System.out.println("Filtering new joiners: "+Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equalsIgnoreCase(employee.getNewJoiner()))
                    .collect(Collectors.toList());
        },executor).thenApplyAsync((employees)->{
            System.out.println("Filtering new joiners with incomplete training: "+Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equalsIgnoreCase(employee.getLearningPending()))
                    .collect(Collectors.toList());
        },executor).thenApplyAsync((employees)->{
            System.out.println("Getting emails: "+Thread.currentThread().getName());
            return employees.stream()
                    .map(employee -> employee.getEmail())
                    .collect(Collectors.toList());
        },executor).thenAcceptAsync((emails)-> {
            emails.forEach(email->{
                //System.out.print("Sending training remainder email to: "+email)
                sb.append(email);
            });
        },executor);
        sb.setLength(0);
        executor.shutdown();
    }

    public static void main(String[] args) throws IOException {
        ChainingThreadsDemo chainingThreadsDemo = new ChainingThreadsDemo();
        System.out.println("************************* SEQUENTIAL PROCESSING ******************************");
        Long start = System.currentTimeMillis();
        chainingThreadsDemo.sendRemainderToEmployeesSequentially();
        Long end = System.currentTimeMillis();
        System.out.println("Time taken to process sequentially: "+(end-start));
        System.out.println("*******************************************************************************\n\n");
        System.out.println("************************* PARALLEL PROCESSING WITH DEFAULT EXECUTOR ******************************");
        start = System.currentTimeMillis();
        chainingThreadsDemo.sendRemainderToEmployeeWithDefaultExecutor();
        end = System.currentTimeMillis();
        System.out.println("Time taken to process parallely with default executor: "+(end-start));
        System.out.println("*******************************************************************************\n\n");
        System.out.println("************************* PARALLEL PROCESSING WITH CUSTOM EXECUTOR ******************************");
        start = System.currentTimeMillis();
        chainingThreadsDemo.sendRemainderToEmployeeWithCustomExecutor();
        end = System.currentTimeMillis();
        System.out.println("Time taken to process parallely with custom executor: "+(end-start));
        System.out.println("*******************************************************************************\n\n");
    }
}
