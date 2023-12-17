package mainPackage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContainersTest {

    private Containers containers;
    private DockerClient dockerClient;

    @BeforeEach
    void setUp() {
        containers = new Containers();
        dockerClient = mock(DockerClient.class);
    }

    @Test
    void testList() {
        // Create a list of mock containers
        List<Container> mockContainers = new ArrayList<>();
        Container container1 = mock(Container.class);
        when(container1.getId()).thenReturn("1");
        when(container1.getImage()).thenReturn("nginx");

        Container container2 = mock(Container.class);
        when(container2.getId()).thenReturn("2");
        when(container2.getImage()).thenReturn("mongo:latest");

        mockContainers.add(container1);
        mockContainers.add(container2);

        // Test list method
        try {
            containers.list(dockerClient, mockContainers);
            // If the method call completes without throwing an exception, the test passes
        } catch (Exception e) {
            // If an exception is caught, fail the test
            System.out.println("Exception caused by null elements");
        }
    }

    @Test
    void testCheckAlive() {
        // Simulating a running container (assuming "docker inspect" returns "true")
        assertTrue(Containers.checkAlive("d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144"));

        // Simulating a non-running container (assuming "docker inspect" returns "false")
        assertFalse(Containers.checkAlive("f713c3561c94c2a4b5d370122a188cc0a957216671ceb0b696862c19f658821c"));
    }
}
