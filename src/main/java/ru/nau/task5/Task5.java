package ru.nau.task5;

import lombok.RequiredArgsConstructor;

import java.net.Socket;
import java.util.Scanner;

@RequiredArgsConstructor
public class Task5 implements Task {

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
            } catch (Exception _) {
            }
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter host: ");
        String host = scanner.nextLine();

        System.out.print("Start port: ");
        int startPort = scanner.nextInt();

        System.out.print("Final port: ");
        int endPort = scanner.nextInt();

        Task5 task = new Task5(host, startPort, endPort);

        // Запускаем сканирование в отдельном потоке
        Thread thread = new Thread(task::start);
        thread.start();

        // Останавливаем через 10 секунд если не завершилось
        try {
            thread.join(10000);
            if (thread.isAlive()) {
                task.stop();
            }
        } catch (InterruptedException e) {
            task.stop();
        }
    }
}
