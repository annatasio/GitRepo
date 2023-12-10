package mainPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SQLServerRESTClient {

    public void getMetricIdStartsStops(String date) throws Exception {

        boolean exists = checkIfDateExists(date);

        if (exists) {

        // Create an HTTP server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // Context for handling "/data" requests
        server.createContext("/data", new DataHandler());

        // Start the server
        server.setExecutor(null); // Use default executor
        server.start();
        System.out.println("Server started on port 8080");
        Thread.sleep(20000);
        stopServer(server);

        } else {
            System.err.println("Date does not exist in the database.\nTry again");
        }
    }

    // Handler for "/data" endpoint
    static class DataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                // Set up database connection parameters
                String url = "jdbc:sqlserver://localhost:1433;databaseName=demo;integratedSecurity=true;"  +
                "encrypt=true;trustServerCertificate=true";

                try {
                    // Establish database connection
                    Connection connection = DriverManager.getConnection(url);
                    Statement statement = connection.createStatement();

                    // Execute SQL query
                    ResultSet resultSet = statement.executeQuery("SELECT id, start_containers, stop_containers FROM MetricsTable");

                    // Prepare response
                    StringBuilder response = new StringBuilder();
                    while (resultSet.next()) {
                        // Assuming your_table has a column named "column_name"
                        String data = resultSet.getString("id");
                        String data2 = resultSet.getString("start_containers");
                        String data3 = resultSet.getString("stop_containers");
                        response.append(data).append("|").append(data2).append("|").append(data3).append("\n");
                    }

                    // Set HTTP status code and response content type
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseHeaders().set("Content-Type", "text/plain");

                    // Send response
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.toString().getBytes());
                    os.close();

                    // Close database connections
                    resultSet.close();
                    statement.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1); // Internal Server Error
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    public static void stopServer(HttpServer server) {
        if (server != null) {
            server.stop(0); // Gracefully stop the server
            System.out.println("Server stopped");
        }

    }

    public boolean checkIfDateExists(String providedDate) {
        boolean dateExists = false;

        // Database connection parameters
        String url = "jdbc:sqlserver://localhost:1433;databaseName=demo;integratedSecurity=true;"  +
                "encrypt=true;trustServerCertificate=true";


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection(url);

            // SQL query to check if the provided username exists in the 'username' column
            String sql = "SELECT measurement_date FROM MetricsTable WHERE measurement_date = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, providedDate);

            // Execute the query
            resultSet = statement.executeQuery();

            // If resultSet has any rows, it means the provided username exists in the database
            if (resultSet.next()) {
                dateExists = true;
            }
        } catch (SQLException e) {
            System.err.println("Error:");
            // Handle the exception according to your application's logic
        } finally {
            // Close resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dateExists;
    }
}

