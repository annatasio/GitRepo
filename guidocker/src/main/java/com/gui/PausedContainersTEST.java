import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PausedContainersTEST {

    private static final String DOCKER_PAUSED = "paused";
    private static final String DOCKER_NAMES = "Names";
    private static final String DOCKER_ID = "Id";
    private static final String DOCKER_IMAGE = "Image";

    public static void main(String[] args) {
        List<ContainerInfo> pausedContainers = getContainersByStatus(DOCKER_PAUSED);
        if (!pausedContainers.isEmpty()) {
            for (ContainerInfo container : pausedContainers) {
                System.out.println("Container Name: " + container.getName());
                System.out.println("Container ID: " + container.getId());
                System.out.println("Container Image: " + container.getImage());
            }
        } else {
            System.out.println("No paused containers found.");
        }
    }

    public static List<ContainerInfo> getContainersByStatus(String status) {
        List<ContainerInfo> containerInfoList = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:2375/containers/json?filters={%22status%22:[%22" + status + "%22]}");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

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
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject container = jsonArray.getJSONObject(i);

                        String id = container.getString(DOCKER_ID);
                        JSONArray namesArray = container.getJSONArray(DOCKER_NAMES);
                        String name = namesArray.getString(0);
                        String image = container.getString(DOCKER_IMAGE);

                        ContainerInfo containerInfo = new ContainerInfo(id, name, image);
                        containerInfoList.add(containerInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to fetch paused containers. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containerInfoList;
    }
    private static class ContainerInfo {
        private final String id;
        private final String name;
        private final String image;

        public ContainerInfo(String id, String name, String image) {
            this.id = id;
            this.name = name;
            this.image = image;
        }

        @Override
        public String toString() {
            return "Container Name: " + name + "\nContainer ID: " + id + "\nContainer Image: " + image;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

    }
}
