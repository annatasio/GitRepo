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
import org.json.JSONObject;

/**This is the main class that runs the GUI App. A User can have interaction
 * with their docker containers and execute simple tasks like restarting a container.
 */

public class DockerDesktopApp extends JFrame {

    /**Final Variables that describe the status of a docker container and the name and the id. */
    private static final String DOCKER_PAUSED = "paused";
    private static final String DOCKER_RUNNING = "running";
    private static final String DOCKER_EXITING = "exited";
    private static final String DOCKER_NAMES = "Names";
    private static final String DOCKER_ID = "Id";

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

    /**The method build up the application. Shows the menu to the user. */
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
                List<String> containerNames = getContainersByStatus(DOCKER_RUNNING, DOCKER_NAMES); // Fetch container names
                List<String> containerId = getContainersByStatus(DOCKER_RUNNING, DOCKER_ID);
                containerNames.addAll(getContainersByStatus(DOCKER_PAUSED, DOCKER_NAMES));
                containerId.addAll(getContainersByStatus(DOCKER_PAUSED, DOCKER_ID));

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
            
            } else if (selectedChoice.equals("Start a Container")) {
                List<String> containerNames = getContainersByStatus(DOCKER_EXITING, DOCKER_NAMES); // Fetch container names
                List<String> containerId = getContainersByStatus(DOCKER_EXITING, DOCKER_ID);
                containerNames.addAll(getContainersByStatus(DOCKER_PAUSED, DOCKER_NAMES));
                containerId.addAll(getContainersByStatus(DOCKER_PAUSED, DOCKER_ID));

                if (!containerNames.isEmpty()) {
                    String selectedContainer = (String) JOptionPane.showInputDialog(
                            this,
                            "Choose a container to start:",
                            "Select Container",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            containerNames.toArray(new String[0]),
                            containerNames.get(0));
                        
    
                    int selectedIndex = containerNames.indexOf(selectedContainer);
                    if (selectedIndex != -1 && !containerNames.isEmpty() && !containerId.isEmpty()) {
                        String selectedContainerId = containerId.get(selectedIndex);
                        StartContainer.startDockerContainer(selectedContainerId);
                        JOptionPane.showMessageDialog(this, "Starting container: " + selectedContainer);
                    } else {
                        JOptionPane.showMessageDialog(this, "Container ID not found for the selected container.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No stoped containers found.");
                }
            } else if (selectedChoice.equals("Stop a Container")) {
                List<String> containerNames = getContainersByStatus(DOCKER_RUNNING, DOCKER_NAMES); // Fetch container names
                List<String> containerId = getContainersByStatus(DOCKER_RUNNING, DOCKER_ID);

                if (!containerNames.isEmpty()) {
                    String selectedContainer = (String) JOptionPane.showInputDialog(
                            this,
                            "Choose a container to stop:",
                            "Select Container",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            containerNames.toArray(new String[0]),
                            containerNames.get(0));
                        
    
                    int selectedIndex = containerNames.indexOf(selectedContainer);
                    if (selectedIndex != -1 && !containerNames.isEmpty() && !containerId.isEmpty()) {
                        String selectedContainerId2 = containerId.get(selectedIndex);
                        StopContainer.stopDockerContainer(selectedContainerId2);
                        JOptionPane.showMessageDialog(this, "Exiting container: " + selectedContainer);
                    } else {
                        JOptionPane.showMessageDialog(this, "Container ID not found for the selected container.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No running containers found.");
                }

            } else if (selectedChoice.equals("Show All Running Containers")) {
                String running = null;
                for (String container : RunningContainers.runningContainers()) {
                    if (running == null) {
                    running = container;
                    } else {
                        running = running + "\n\n" + container;
                    }
                }
                JOptionPane.showMessageDialog(this, "Running Containers:\n\n" + running);
            
            } else if (selectedChoice.equals("Show All Paused Containers")) {
                String paused = null;
                for (String container : PausedContainers.pausedContainers()) {
                    if (paused == null) {
                    paused = container;
                    } else {
                        paused = paused + "\n\n" + container;
                    }
                }
                JOptionPane.showMessageDialog(this, "Paused Containers:\n\n" + paused);
                
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

    /**Method that returns the A List of the elements that the user asks for. For example a user
     * can give status running and element id and the method will return the Id's of all running containers 
     * in their localhost.
     */
    public static List<String> getContainersByStatus(String status, String element) {
        List<String> containerInfo = new ArrayList<>();

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
                        if (element.equals("Id")) {
                            String id = container.getString("Id");
                            containerInfo.add(id);
                        } else {
                            JSONArray namesArray = container.getJSONArray("Names");
                            for (int j = 0; j < namesArray.length(); j++) {
                                String name = namesArray.getString(j);
                                containerInfo.add(name);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Failed to fetch containers with status '" + status + "'. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containerInfo;
    }
}

