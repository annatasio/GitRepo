package mainPackage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.Container;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DBThreadTest {

    @Test
    void testInsertDockerInfoIntoDatabase() throws SQLException {
        // Mocking DockerClient and its behavior
        DockerClient dockerClient = Mockito.mock(DockerClient.class);
        ListContainersCmd listContainersCmd = Mockito.mock(ListContainersCmd.class);
        when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);

        List<Container> mockContainers = new ArrayList<>();
        Container runningContainer = Mockito.mock(Container.class);
        when(runningContainer.getState()).thenReturn("running");
        when(runningContainer.getImage()).thenReturn("mongo:latest");
        when(runningContainer.getNames()).thenReturn(new String[]{"demo-web-1"});
        when(runningContainer.getId()).thenReturn("1");
        mockContainers.add(runningContainer);

        Container stoppedContainer = Mockito.mock(Container.class);
        when(stoppedContainer.getState()).thenReturn("exited");
        when(stoppedContainer.getImage()).thenReturn("mongo:latest");
        when(stoppedContainer.getNames()).thenReturn(new String[]{"test-mongo"});
        when(stoppedContainer.getId()).thenReturn("2");
        mockContainers.add(stoppedContainer);

       // when(dockerClient.listContainersCmd().withShowAll(true).exec()).thenReturn(mockContainers);
       when(dockerClient.listContainersCmd()).thenReturn(listContainersCmd);
       when(listContainersCmd.withShowAll(true)).thenReturn(listContainersCmd); // Return itself
       when(listContainersCmd.exec()).thenReturn(new ArrayList<>()); // Return an empty list or add desired containers

       
       // Mocking DriverManager and its behavior
        Connection mockConnection = Mockito.mock(Connection.class);
        DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=demo1;integratedSecurity=true;"  +
                             "encrypt=true;trustServerCertificate=true");

        // Test the method
        DBThread.insertDockerInfoIntoDatabase(dockerClient);

        // Verify interactions
        verify(dockerClient, times(1)).listContainersCmd();
        verify(mockConnection, times(0)).prepareStatement(any());
        verify(listContainersCmd, times(1)).withShowAll(true);
        verify(listContainersCmd, times(1)).exec();
    }
}
