package mainPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse.ContainerState;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

import org.junit.jupiter.api.AfterEach;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DockerprTest {

    private ExecutorService executorService;
    private Dockerpr dockerpr;

    // Mocked DockerClient and container ID for testing
    private String ip = "0a4ab61546454c87840b0b3fa58a510bf2c65ca89c9f3055d6879799f55aa5c9";

    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .build();
        
        //.createDefaultConfigBuilder().withDockerHost("tcp://127.0.0.1:2375").build();
        // Create Docker client
        DockerClient dc = DockerClientBuilder
                .getInstance(config)
                .build();

    @BeforeEach
    void setup() {
        dockerpr = new Dockerpr(ip, dc);
    }

    @AfterEach
    void tearDown() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Test
    void testMeth1_StartContainer() {
        executorService = Executors.newFixedThreadPool(3);

        dockerpr.meth1();

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            // Assert any conditions after starting containers, if needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testMeth2_StopContainer() {
        executorService = Executors.newFixedThreadPool(3);

        dockerpr.meth2();

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            // Assert any conditions after stopping containers, if needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void testCheckState() {
        String containerId = "d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144";
        ContainerState containerStateMock = Mockito.mock(ContainerState.class);
        InspectContainerResponse inspectContainerResponseMock = Mockito.mock(InspectContainerResponse.class);
        Mockito.when(inspectContainerResponseMock.getState()).thenReturn(containerStateMock);

        dockerpr.checkState(dc, containerId);

        // You might want to add assertions or verify some specific behavior based on the state output
    }
}


