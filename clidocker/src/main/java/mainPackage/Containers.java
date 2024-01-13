package mainPackage;

import java.util.List;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.DockerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Containers {

    /**A method that lists all containers of docker engine
     * @param dockerClient, @param containers
     */

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

    /**Checking if a docker Container is running or not */

    public static boolean checkAlive(String containerId) {
        System.out.println("Checking the container " + containerId);
        Process p = runCommand("docker inspect -f {{.State.Running}} "+ containerId);
        return readCommandResult(p);
    }
    
    private static Process runCommand(String command) {
        Process p = null;
        String[] words = command.split("\\s+");
        try {
            p = Runtime.getRuntime().exec(words);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    private static boolean readCommandResult(Process proc) {
        String s;
        boolean result = false;
        try {
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                System.out.println("Container is working: "+ s);
                if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false")) {
                    result = Boolean.parseBoolean(s);
                }
            }
            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
