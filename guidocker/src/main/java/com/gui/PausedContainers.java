package com.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class PausedContainers {

    /**Final variable that exposes docker daemon*/
    private static final String DOCKER_API_URL = "http://localhost:2375"; // Replace with your Docker API URL

    /**Method that show all the paused docker containers via http request */
    public static List<String> pausedContainers() {
        List<String> containersInfo =  new ArrayList<>();
        try {
            // Docker Remote API endpoint to give started containers
            String endpoint = DOCKER_API_URL + "/containers/json?filters={\"status\":[\"paused\"]}";

            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Send the request to restart the container
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                try {
                JSONArray containersArray = new JSONArray(response.toString());
                for (int i = 0; i < containersArray.length(); i++) {
                    JSONObject containerObject = containersArray.getJSONObject(i);
                    String name = containerObject.getJSONArray("Names").getString(0);
                    String id = containerObject.getString("Id");
                    String image = containerObject.getString("Image");

                    String containerInfo = "Container Name: " + name + "\n"
                                + "Container ID: " + id + "\n"
                                + "Container Image: " + image;

                    containersInfo.add(containerInfo);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Fail to fetch paused Containers");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containersInfo;
    }
}
