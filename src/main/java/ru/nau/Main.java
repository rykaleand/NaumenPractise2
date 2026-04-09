package ru.nau;

import ru.nau.task1.Task1;
import ru.nau.task2.Task2;
import ru.nau.task3.Task3;
import ru.nau.task4.Task4;
import ru.nau.task5.Task5;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                String input = scanner.nextLine().trim();
                switch (input) {
                    case "1" -> Task1.run(scanner);
                    case "2" -> Task2.run(scanner);
                    case "3" -> Task3.run();
                    case "4" -> Task4.run();
                    case "5" -> Task5.run(scanner);
                    case "exit" -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Unknown command. Please enter 1-5 or 'exit'.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1 - Sum of positive elements in array");
        System.out.println("2 - Sort array with insertion sort");
        System.out.println("3 - Employee list and salary check");
        System.out.println("4 - HTTP request to httpbin.org");
        System.out.println("5 - Port scanner");
        System.out.println("exit - Exit program");
        System.out.print("> ");
    }
}
