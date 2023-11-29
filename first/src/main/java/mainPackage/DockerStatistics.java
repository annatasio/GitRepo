package mainPackage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.InvocationBuilder.AsyncResultCallback;

import java.util.List;

/**This DockerStatistics class refers to docker containers statistics */

public class DockerStatistics {
    private int nodeCount;
    private int edgeCount;
 
    public DockerStatistics() {
        this.nodeCount = 0;
        this.edgeCount = 0;
    }

    /**A method which calculates Node and Edge counts */
    public void updateStatistics(List<Container> containers) {
        this.nodeCount = containers.size();
        this.edgeCount = calculateEdgeCount(containers);
    }
 
    public int calculateEdgeCount(List<Container> containers) {
        // For demonstration, using a simple logic (sum of lengths of container IDs)
        int totalLength = 0;
        for (Container container : containers) {
            totalLength += container.getId().length();
        }
        return totalLength;
    }
 
    public int getNodeCount() {
        return nodeCount;
    }
 
    public int getEdgeCount() {
        return edgeCount;
    }


    /**A method which takes statistics of running docker containers */
    public void stats(DockerClient dockerClient) {

        try {

            // Fetch all containers
            List<Container> containers = dockerClient.listContainersCmd().exec();
            
            for (Container container : containers) {
                // Get container ID
                String containerId = container.getId();
            
                // Fetch container statistics
                AsyncResultCallback<Statistics> callback = new AsyncResultCallback<>();
                dockerClient.statsCmd(containerId).exec(callback);
                Statistics stats;

                stats = callback.awaitResult();

                System.out.println("Container ID: " + containerId);
                System.out.println("CPU Usage: " + stats.getCpuStats().getCpuUsage().getTotalUsage());
                System.out.println("Memory usage: " + stats.getMemoryStats().getUsage());
                System.out.println("Disk read/write: " + stats.getRead());
                System.out.println("Network I.O: " + stats.getNetworks());
                System.out.println("Pids Statistics: " + stats.getPidsStats().toString());

                //call back closes
                callback.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
 
