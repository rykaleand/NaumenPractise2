package ru.nau.task4;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task4 {

    private static final String URL = "https://httpbin.org/get";

    public static void run() {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            String host = root.path("headers").path("Host").asText();
            System.out.println("Host: " + host);

        } catch (Exception e) {
            System.out.println("Error: failed to perform HTTP request.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}