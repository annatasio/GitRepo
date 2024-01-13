package mainPackage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

import java.util.List;
import java.io.IOException;
import java.util.Scanner;

/**Here is a simple application that connects with docker deamon and manages docker containers.
 * A user can start or stop a container, see the state of it and also can get the statistics of a container.
 */

public class Main2 {

    /**Main method that runs the cli app */
    public static void main(String[] args) {
        // Set up Docker client configuration
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .build();
        
        //.createDefaultConfigBuilder().withDockerHost("tcp://127.0.0.1:2375").build();
        // Create Docker client
        DockerClient dockerClient = DockerClientBuilder
                .getInstance(config)
                .build();

        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);

        int x;

         System.out.println("****Here is a simple application that connects with docker deamon and manages docker containers.\n"
            + "You can start or stop a container, see the state of it and also can get the statistics of a container****");

        do {
            System.out.println("Please select a number between 0-5 depending on the process you want");
            System.out.println("0: Exit program\n1: Display all Containers\n2: Start a Container\n"
                    + "3: Stop a Container\n4: Status of a Container\n5: Statistics of a Container\n6: Metrics Id, Started and Stoped Containers\n");
                    
            x = sc.nextInt();

            switch (x) {

                case 1:
                
                // List all containers
                Containers l = new Containers();
                l.list(dockerClient, containers);

                break;

                case 2:

                System.out.println("Please give the id of the container you want to start");
                String id = sc1.nextLine();

                boolean isRunning = Containers.checkAlive(id);
                if (isRunning) {
                    System.out.println("The container is already running");

                } else {
                    
                    Dockerpr dp1 = new Dockerpr(id, dockerClient);    
                    dp1.meth1();
                    System.out.println("The conatiner has started");
                }

                break;

                case 3:

                System.out.println("Please give the id of the container you want to stop");
                String id2 = sc1.nextLine();

                boolean isRunning2 = Containers.checkAlive(id2);
                if (isRunning2 == false) {
                    System.out.println("The container is already stoped");

                } else {
                    
                    Dockerpr dp2 = new Dockerpr(id2, dockerClient);
                    dp2.meth2();
                    System.out.println("The conatiner has stoped");
                }

                break;

                case 4:

                System.out.println("Please give the id of the container you want to check");
                String id3 = sc1.nextLine();
                Dockerpr dp3 = new Dockerpr(id3, dockerClient);
                dp3.checkState(dockerClient, id3);

                break;

                case 5:

                DockerStatistics topology = new DockerStatistics();
                topology.updateStatistics(containers);
 
                System.out.println("Node Count: " + topology.getNodeCount());
                System.out.println("Edge Count: " + topology.getEdgeCount());
                topology.stats(dockerClient);

                DBThread.insertDockerInfoIntoDatabase(dockerClient);

                break;

                case 6:

                System.out.println("Please give a date");
                String date = sc1.nextLine();
                SQLServerRESTClient sql1 = new SQLServerRESTClient(date);

                try {
                sql1.getMetricIdStartsStops();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

                case 0:

                System.out.println("Programm has ended");

                break;

                default:

                System.out.println("Wrong number plese try again");

                break;
            }

        } while (x != 0);

        // Close the Docker client
        try {
            dockerClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

