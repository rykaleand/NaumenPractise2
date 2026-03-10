package ru.nau.task3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Employee {
    private String fullName;
    private Integer age;
    private String department;
    private Double salary;
}
