package ru.nau.task3;

import java.util.ArrayList;

public class Task3 {

    private static final double SALARY_THRESHOLD = 100000.0;

    public static void run() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Ivanov Ivan Ivanovich", 25, "IT", 80000.0));
        employees.add(new Employee("Petrov Petr Petrovich", 35, "HR", 120000.0));
        employees.add(new Employee("Sidorova Anna Sergeevna", 28, "IT", 95000.0));
        employees.add(new Employee("Kozlov Dmitry Olegovich", 42, "Finance", 150000.0));
        employees.add(new Employee("Novikova Maria Igorevna", 31, "HR", 110000.0));

        System.out.println("Employee list:");
        employees.forEach(System.out::println);

        boolean hasHighSalary = employees.stream()
                .anyMatch(e -> e.getSalary() > SALARY_THRESHOLD);
        System.out.println("\nIs there an employee with salary more than "
                + SALARY_THRESHOLD + ": " + hasHighSalary);
    }
}