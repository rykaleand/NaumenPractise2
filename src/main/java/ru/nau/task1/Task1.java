package ru.nau.task1;

import java.util.Random;
import java.util.Scanner;

public class Task1 {

    private static final int RANDOM_MIN = -100;
    private static final int RANDOM_MAX = 100;
    private static final int RANDOM_RANGE = RANDOM_MAX - RANDOM_MIN + 1;

    public static void run(Scanner scanner) {
        System.out.print("Input size of array: ");
        try {
            int n = Integer.parseInt(scanner.nextLine().trim());
            if (n <= 0) {
                System.out.println("Error: array size must be a positive number.");
                return;
            }

            int[] array = new int[n];
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(RANDOM_RANGE) + RANDOM_MIN;
            }

            System.out.print("Array: ");
            for (int num : array) {
                System.out.print(num + " ");
            }
            System.out.println();

            int sum = 0;
            for (int num : array) {
                if (num > 0) {
                    sum += num;
                }
            }
            System.out.println("Sum of positive elements: " + sum);

        } catch (NumberFormatException e) {
            System.out.println("Error: invalid input. Please enter an integer.");
            System.out.println(e.getMessage());
        }
    }
}