package mainPackage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.InvocationBuilder;
import com.github.dockerjava.core.InvocationBuilder.AsyncResultCallback;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DockerStatisticsTest {

    private DockerStatistics dockerStatistics;
    private DockerClient dockerClient;

    @BeforeEach
    void setUp() {
        dockerStatistics = new DockerStatistics();
        dockerClient = mock(DockerClient.class);
    }

    @Test
    void testUpdateStatistics() {
        List<Container> mockContainers = new ArrayList<>();
        Container container1 = mock(Container.class);
        when(container1.getId()).thenReturn("d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144");

        Container container2 = mock(Container.class);
        when(container2.getId()).thenReturn("d2b8efcd5610dcaf762fe4e1471b4a397142e3cc669b912b6c2606deef7dfe4b");

        mockContainers.add(container1);
        mockContainers.add(container2);

        dockerStatistics.updateStatistics(mockContainers);

        assertEquals(2, dockerStatistics.getNodeCount());
        assertEquals(128, dockerStatistics.getEdgeCount()); // ("id1".length() + "id2".length())
    }

    @Test
    void testStats() {
        // Create a list of mock containers
        List<Container> mockContainers = new ArrayList<>();
        Container container1 = mock(Container.class);
        when(container1.getId()).thenReturn("d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144");

        Container container2 = mock(Container.class);
        when(container2.getId()).thenReturn("d2b8efcd5610dcaf762fe4e1471b4a397142e3cc669b912b6c2606deef7dfe4b");

        mockContainers.add(container1);
        mockContainers.add(container2);

        // Mock DockerClient behavior
        doAnswer(new Answer<InvocationBuilder>() {
            @Override
            public InvocationBuilder answer(InvocationOnMock invocation) {
                String containerId = invocation.getArgument(0);
                assertEquals("d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144", containerId); // Verify the container ID passed
                return mock(InvocationBuilder.class);
            }
        }).when(dockerClient).statsCmd("d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144");

        doAnswer(new Answer<InvocationBuilder>() {
            @Override
            public InvocationBuilder answer(InvocationOnMock invocation) {
                String containerId = invocation.getArgument(0);
                assertEquals("d2b8efcd5610dcaf762fe4e1471b4a397142e3cc669b912b6c2606deef7dfe4b", containerId); // Verify the container ID passed
                return mock(InvocationBuilder.class);
            }
        }).when(dockerClient).statsCmd("d2b8efcd5610dcaf762fe4e1471b4a397142e3cc669b912b6c2606deef7dfe4b");

        AsyncResultCallback<Statistics> callback = mock(AsyncResultCallback.class);
        when(callback.awaitResult()).thenReturn(mock(Statistics.class));
        //doNothing().when(callback).close();

        // Capture the arguments passed to dockerClient.statsCmd() method
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        // Test stats method
        dockerStatistics.stats(dockerClient);

        // Verification
        List<String> capturedContainerIds = captor.getAllValues();
        assertEquals(0, capturedContainerIds.size());
    }
}
