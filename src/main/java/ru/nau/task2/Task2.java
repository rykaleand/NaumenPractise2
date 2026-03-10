package ru.nau.task2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input number of elements: ");
        int n = scanner.nextInt();

        ArrayList<Double> list = new ArrayList<>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            list.add(random.nextDouble() * 200 - 100);
        }

        System.out.println("Array before sort: " + list);

        // Сортировка вставками
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
    }
}
