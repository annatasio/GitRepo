package com.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.HttpURLConnection;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PausedContainersTest {

    @Mock
    private HttpURLConnection mockConnection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPausedContainersInfo() {

        List<String> containersInfo = PausedContainers.pausedContainers();

        assertNotNull(containersInfo);
        assertFalse(containersInfo.isEmpty());
        assertEquals(1, containersInfo.size());

        // Verify that the expected information is present in the returned list
        for (String info : containersInfo) {
            assertTrue(info.contains("Container Name:"));
            assertTrue(info.contains("Container ID:"));
            assertTrue(info.contains("Container Image:"));
        }
    }
}
