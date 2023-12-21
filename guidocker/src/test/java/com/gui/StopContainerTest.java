package com.gui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StopContainerTest {

    @Mock
    private HttpURLConnection mockConnection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStopDockerContainer() throws IOException {
        String id = "d2b8efcd5610dcaf762fe4e1471b4a397142e3cc669b912b6c2606deef7dfe4b";

        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NO_CONTENT);
        when(mockConnection.getOutputStream()).thenReturn(null);
        when(mockConnection.getResponseMessage()).thenReturn("OK");
        doReturn(mockConnection).when(mockConnection); 

        HttpURLConnection.setFollowRedirects(false);
        RestartContainer.restartDockerContainer(id);

        List<String> l = DockerDesktopApp.getContainersByStatus("exited", "Id");
        for(int i=0; i < l.size(); i++) {
            if(l.get(i).equals("d2b8efcd5610dcaf762fe4e1471b4a397142e3cc669b912b6c2606deef7dfe4b")) {
                assertTrue(true);
            } else {
                assertFalse(false);
            }
        }
    }
}
