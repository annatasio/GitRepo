package mainPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;

public class SQLServerRESTClientTest {

    private SQLServerRESTClient restClient;

    @Test
    void testServerStartAndStop() {

        try {

            // Start the server
            restClient.getMetricIdStartsStops();

            // Sleep to allow the server to start
            Thread.sleep(1000);
            URL url = new URL("http://localhost:8080/data");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            assertEquals(200, responseCode, "HTTP GET request should return 200 OK");

            // Stop the server
            SQLServerRESTClient.stopServer(null); // Passing null as argument for testing purposes
        } catch(Exception e) {
            System.out.println("Error");
        }
    }
}

