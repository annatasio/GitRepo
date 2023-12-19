package com.gui;

/**Project guidocker*/
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**This is the main class that runs the GUI App. A User can have interaction
 * with their docker containers and execute simple tasks like restarting a container.
 */

public class DockerDesktopApp extends JFrame {

    public DockerDesktopApp() {

        setTitle("Docker Desktop App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        getContentPane().setBackground(Color.BLUE);

        JLabel titleLabel = new JLabel("Docker Desktop App");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> showMenu());
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton, BorderLayout.CENTER);

        panel.add(buttonPanel, BorderLayout.CENTER);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }

    private void showMenu() {
        String[] choices = {"Show All Running Containers", "Show All Paused Containers", "Stop a Container", "Restart a Container", "Start a Container"};

        String selectedChoice = (String) JOptionPane.showInputDialog(
                this,
                "Choose an action:",
                "Docker Actions Menu",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);

        if (selectedChoice != null) {
            if (selectedChoice.equals("Restart a Container")) {
                List<String> containerNames = getContainerNames(); // Fetch container names
                List<String> containerId = getContainerId();

                if (!containerNames.isEmpty()) {
                    String selectedContainer = (String) JOptionPane.showInputDialog(
                            this,
                            "Choose a container to restart:",
                            "Select Container",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            containerNames.toArray(new String[0]),
                            containerNames.get(0));
                        
    
                    int selectedIndex = containerNames.indexOf(selectedContainer);
                    if (selectedIndex != -1) {
                        String selectedContainerId = containerId.get(selectedIndex);
                        RestartContainer.restartDockerContainer(selectedContainerId);
                        JOptionPane.showMessageDialog(this, "Restarting container: " + selectedContainer);
                    } else {
                        JOptionPane.showMessageDialog(this, "Container ID not found for the selected container.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No running or paused containers found.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selected: " + selectedChoice);
            }
        }
    }
    
    /**Main method that runs the app */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DockerDesktopApp();
        });
    }

     private List<String> getContainerNames() {
        List<String> containerNames = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:2375/containers/json");
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
                    JSONArray namesArray = container.getJSONArray("Names");
                    for (int j = 0; j < namesArray.length(); j++) {
                    String name = namesArray.getString(j);
                    containerNames.add(name);
                    }
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to fetch containers. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containerNames;
    }

    private List<String> getContainerId() {
        List<String> containerId = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:2375/containers/json");
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
                    String id = container.getString("Id");
                    containerId.add(id);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to fetch containers. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containerId;
    }
}
