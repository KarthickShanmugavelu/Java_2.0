package com.handsons.multithreading.complatable.future.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @JsonProperty("employeeId")
    private String employeeId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNewJoiner() {
        return newJoiner;
    }

    public void setNewJoiner(String newJoiner) {
        this.newJoiner = newJoiner;
    }

    public String getLearningPending() {
        return learningPending;
    }

    public void setLearningPending(String learningPending) {
        this.learningPending = learningPending;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

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
