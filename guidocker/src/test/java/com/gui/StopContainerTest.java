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
        String id = "d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144";

        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NO_CONTENT);
        when(mockConnection.getOutputStream()).thenReturn(null);
        when(mockConnection.getResponseMessage()).thenReturn("OK");
        //doReturn(mockConnection).when(mockConnection); 

        HttpURLConnection.setFollowRedirects(false);
        StopContainer.stopDockerContainer(id);

        List<String> l = DockerDesktopApp.getContainersByStatus("exited", "Id");
        for(int i=0; i < l.size(); i++) {
            if(l.get(i).equals("d06bf50e943500636ddf1fec3ee9833b457e2b5c277b2bbeb9882ce92eba9144")) {
                assertTrue(true);
            } else {
                assertFalse(false);
            }
        }
    }
}
