package com.handsons.multithreading.complatable.future.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handsons.multithreading.complatable.future.dto.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeRepository {

    public static List<Employee> fetchEmployees() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("employees.json"),new TypeReference<List<Employee>>(){
        });
    }
}
