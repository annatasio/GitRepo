package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBThread {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=Demo;user=testLogin;password=1234;" +
        "encrypt=true;trustServerCertificate=true;";
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connected to SQL Server!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 
