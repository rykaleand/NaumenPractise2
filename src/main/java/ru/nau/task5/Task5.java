package ru.nau.task5;

import lombok.RequiredArgsConstructor;

import java.net.Socket;
import java.util.Scanner;

@RequiredArgsConstructor
public class Task5 implements Task {

    private static final int TIMEOUT_MS = 10000;
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;

    private final String host;
    private final int startPort;
    private final int endPort;
    private volatile boolean running = false;

    @Override
    public void start() {
        running = true;
        System.out.println("Start scanning " + host + " ports " + startPort + "-" + endPort);

        for (int port = startPort; port <= endPort && running; port++) {
            try (Socket socket = new Socket(host, port)) {
                System.out.println("Port " + port + " is open");
            } catch (Exception ignored) {}
        }

        if (running) {
            System.out.println("Scanning finished");
        }
    }

    @Override
    public void stop() {
        running = false;
        System.out.println("Scanning stopped");
    }

    public static void run(Scanner scanner) {
        try {
            System.out.print("Enter host: ");
            String host = scanner.nextLine().trim();
            if (host.isEmpty()) {
                System.out.println("Error: host cannot be empty.");
                return;
            }

            System.out.print("Start port: ");
            int startPort = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Final port: ");
            int endPort = Integer.parseInt(scanner.nextLine().trim());

            if (startPort < MIN_PORT || endPort > MAX_PORT || startPort > endPort) {
                System.out.println("Error: ports must be between " + MIN_PORT + " and "
                        + MAX_PORT + ", start port must be <= end port.");
                return;
            }

            Task5 task = new Task5(host, startPort, endPort);
            Thread thread = new Thread(task::start);
            thread.start();

            try {
                thread.join(TIMEOUT_MS);
                if (thread.isAlive()) {
                    task.stop();
                }
            } catch (InterruptedException e) {
                task.stop();
                System.out.println("Error: scanning interrupted.");
                System.out.println(e.getMessage());
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: invalid port. Please enter an integer.");
            System.out.println(e.getMessage());
        }
    }
}