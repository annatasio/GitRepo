package mainPackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerCmd;
import com.github.dockerjava.api.command.InspectContainerResponse.ContainerState;

/**A class for start or stop a docker container.*/

public class Dockerpr {

        private ExecutorService executorService;
        private DockerClient dc;
        private String ip;

        public Dockerpr(String ip, DockerClient dc) {

            this.ip = ip;
            this.dc = dc;
        }

        public void setIP(String ip) {

            this.ip = ip;
        }

        public void setDC(DockerClient dc) {

            this.dc = dc;
        }

        public String getIP() {

            return ip;
        }

        public DockerClient getDC() {

            return dc;
        }

        /**Executor thread for starting a container */
        public void meth1() {
            
            executorService = Executors.newFixedThreadPool(3);
            executorService.execute(() -> startContainer(dc, ip));
            executorService.shutdown();

            try {
                if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                System.out.println("They haven't started");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**Executor thread for stoping a container*/
        public void meth2() {

            executorService = Executors.newFixedThreadPool(3);
            executorService.execute(() -> stopContainer(dc, ip));
            executorService.shutdown();

            try {
                if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                System.out.println("They haven't stopped");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        
    /**Method that starts a container @param doc, @param idd */

    public static void startContainer(DockerClient doc, String idd) {
        doc.startContainerCmd(idd).exec();
    }

    /**Method that stops a container @param dockerClient, @param containerID */
    public static void stopContainer(DockerClient dockerClient, String ContainerID) {
        dockerClient.stopContainerCmd(ContainerID).exec();
    }


    /**Method that checks the state of a container @param dockerClient, @param containerID */
    public void checkState (DockerClient dockerClient, String containerID) {

        InspectContainerCmd container = dockerClient.inspectContainerCmd(containerID);
        ContainerState state = container.exec().getState();
        System.out.println(state.toString());
    }
}  


