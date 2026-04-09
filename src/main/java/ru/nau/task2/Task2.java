package ru.nau.task2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Task2 {

    private static final double RANDOM_MIN = -100.0;
    private static final double RANDOM_MAX = 100.0;
    private static final double RANDOM_RANGE = RANDOM_MAX - RANDOM_MIN;

    public static void run(Scanner scanner) {
        System.out.print("Input number of elements: ");
        try {
            int n = Integer.parseInt(scanner.nextLine().trim());
            if (n <= 0) {
                System.out.println("Error: number of elements must be a positive number.");
                return;
            }

            ArrayList<Double> list = new ArrayList<>(n);
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                list.add(random.nextDouble() * RANDOM_RANGE + RANDOM_MIN);
            }

            System.out.println("Array before sort: " + list);

            for (int i = 1; i < list.size(); i++) {
                double key = list.get(i);
                int j = i - 1;
                while (j >= 0 && list.get(j) > key) {
                    list.set(j + 1, list.get(j));
                    j--;
                }
                list.set(j + 1, key);
            }

            System.out.println("Array after sort: " + list);

        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input. Please enter an integer.");
            System.out.println(e.getMessage());
        }
    }
}