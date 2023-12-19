package mainPackage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.List;

/**
 * Manages Docker-to-database interactions, inserting container metrics and updating records.
 * Author: Ioannis Varelis 8220009
 */
public class DBThread {

    /**Method to insert Docker information into the database*/
    public static void insertDockerInfoIntoDatabase(DockerClient dockerClient) {

        // Database connection details
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=demo1;integratedSecurity=true;"
                + "encrypt=true;trustServerCertificate=true";
        //String user = "testLogin";
        //String password = "1234";

        try (Connection connection = DriverManager.getConnection(dbURL)) {
            // Insert a metrics entry into the MetricsTable and retrieve its ID
            int metricsId = insertMetricsEntry(connection);

            // Fetch list of containers from Docker
            List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();

            int runningContainers = 0;
            int stoppedContainers = 0;

            // Iterate through Docker containers
            for (Container container : containers) {
                String imageName = container.getImage();
                String containerName = container.getNames()[0];
                String containerId = container.getId();
                String containerState = container.getState();

                // Check container state and count running and stopped containers
                if (containerState.equalsIgnoreCase("running")) {
                    runningContainers++;
                } else if (containerState.equalsIgnoreCase("exited")) {
                    stoppedContainers++;
                }

                // Insert Docker instance details into DockerInstances table
                boolean isRunning = containerState.equalsIgnoreCase("running");
                insertDockerInstance(connection, containerId, metricsId, imageName, containerName, isRunning);
            }

            // Update MetricsTable with running and stopped container counts
            updateMetricsTable(connection, metricsId, runningContainers, stoppedContainers);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        } catch (com.github.dockerjava.api.exception.DockerClientException | com.github.dockerjava.api.exception.NotFoundException e) {
            e.printStackTrace();
            System.out.println("Docker Error: " + e.getMessage());
        }
    }

    // Method to insert a metrics entry into MetricsTable
    private static int insertMetricsEntry(Connection connection) throws SQLException {
        String sql = "INSERT INTO MetricsTable (measurement_date, start_containers, stop_containers) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);

            preparedStatement.executeUpdate();

            int metricsId = -1;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    metricsId = generatedKeys.getInt(1);
                }
            }
            return metricsId;
        }
    }

    // Method to update MetricsTable with running and stopped container counts
    private static void updateMetricsTable(Connection connection, int metricsId, int runningContainers, int stoppedContainers) throws SQLException {
        String sql = "UPDATE MetricsTable SET start_containers = ?, stop_containers = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
            updateStatement.setInt(1, runningContainers);
            updateStatement.setInt(2, stoppedContainers);
            updateStatement.setInt(3, metricsId);

            updateStatement.executeUpdate();
        }
    }

    // Method to insert Docker instance details into DockerInstances table
    private static void insertDockerInstance(Connection connection, String containerId, int metricsId, String imageName, String containerName, boolean isRunning) throws SQLException {
        String sql = "INSERT INTO DockerInstances (id, metrics_id, image, name, is_running) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, containerId);
            preparedStatement.setInt(2, metricsId);
            preparedStatement.setString(3, imageName != null ? imageName : "Unknown");
            preparedStatement.setString(4, containerName != null ? containerName : "Unnamed");
            preparedStatement.setBoolean(5, isRunning);

            preparedStatement.executeUpdate();
        }
    }
}
