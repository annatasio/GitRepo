package mainPackage;

import java.util.List;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.DockerClient;

public class ListContainers {

    /**A method that lists all containers of docker engine */

    public void list(DockerClient dockerClient, List<Container> containers) {

        // List all containers

        for (Container container : containers) {
            System.out.println(container.getId() + " - " + container.getImage());
        }

        for (Container container : containers) {
            System.out.println("Container ID: " + container.getId());
            System.out.println("Container Name: " + container.getNames()[0]);
            System.out.println("Container Image: " + container.getImage());
            System.out.println("-----------------------");
        }

    }
    
}
