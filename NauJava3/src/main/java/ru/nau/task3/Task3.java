package ru.nau.task3;

import java.util.ArrayList;

public class Task3 {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иванов Иван Иванович", 25, "IT", 80000.0));
        employees.add(new Employee("Петров Пётр Петрович", 35, "HR", 120000.0));
        employees.add(new Employee("Сидорова Анна Сергеевна", 28, "IT", 95000.0));
        employees.add(new Employee("Козлов Дмитрий Олегович", 42, "Finance", 150000.0));
        employees.add(new Employee("Новикова Мария Игоревна", 31, "HR", 110000.0));

        System.out.println("Есть ли сотрудник с зарплатой более 100000.00: " + employees.stream()
                .anyMatch(e -> e.getSalary() > 100000.0));
    }
}
