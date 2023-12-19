package com.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StopContainer {

    private static final String DOCKER_API_URL = "http://localhost:2375"; // Αντικαταστήστε με το Docker API URL σας

    public static void stopDockerContainer(String id) {
        try {
            // Docker Remote API endpoint to stop a container
            String endpoint = DOCKER_API_URL + "/containers/" + id + "/stop";

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Αποστολή του αιτήματος για τον τερματισμό του container
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Container stopped successfully.");
            } else {
                System.out.println("Failed to stop container. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}