package com.handsons.multithreading.complatable.future.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @JsonProperty("employeeId")
    private String employeeId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("newJoiner")
    private String newJoiner;
    @JsonProperty("learningPending")
    private String learningPending;
    @JsonProperty("salary")
    private int salary;
    @JsonProperty("rating")
    private int rating;
}
