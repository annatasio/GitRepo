package com.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestartContainer {

    /**Final variable that exposes docker daemon*/
    private static final String DOCKER_API_URL = "http://localhost:2375"; // Replace with your Docker API URL

    /**Method that restarts a docker containet using http POST method*/
    public static void restartDockerContainer(String id) {
        try {
            // Docker Remote API endpoint to restart a container
            String endpoint = DOCKER_API_URL + "/containers/" + id + "/restart";

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Send the request to restart the container
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Container restarted successfully.");
            } else {
                System.out.println("Failed to restart container. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}