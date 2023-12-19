package com.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class StartContainer {

    /**Final variable that exposes docker daemon*/
    private static final String DOCKER_API_URL = "http://localhost:2375"; // Replace with your Docker API URL

    /**Method that starts a docker containet using http POST method*/
    public static void startDockerContainer(String id) {
        try {
            // Docker Remote API endpoint to start a container
            String endpoint = DOCKER_API_URL + "/containers/" + id + "/start";

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Send the request to restart the container
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Container started successfully.");
            } else {
                System.out.println("Failed to start container. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
