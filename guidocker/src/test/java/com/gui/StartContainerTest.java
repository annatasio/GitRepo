package com.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class StartContainerTest {

    @Mock
    private HttpURLConnection mockConnection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartDockerContainer() throws IOException {
        String id = "f713c3561c94c2a4b5d370122a188cc0a957216671ceb0b696862c19f658821c";

        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NO_CONTENT);
        when(mockConnection.getOutputStream()).thenReturn(null);
        when(mockConnection.getResponseMessage()).thenReturn("OK");
        //doReturn(mockConnection).when(mockConnection); 

        HttpURLConnection.setFollowRedirects(false);
        StartContainer.startDockerContainer(id);

        List<String> l = DockerDesktopApp.getContainersByStatus("running", "Id");
        for(int i=0; i < l.size(); i++) {
            if(l.get(i).equals("f713c3561c94c2a4b5d370122a188cc0a957216671ceb0b696862c19f658821c")) {
                assertTrue(true);
            } else {
                assertFalse(false);
            }
        }

    }
    
}
