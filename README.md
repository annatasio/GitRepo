# GitRepo
This is our project for subject Programming II
It is about managing docker desktop!!

INSTRUCTIONS FOR PACKAGE "clidocker"
1.Move into the project folder where the pom.xml is
2.Compile the program with the command "mvn clean istall"
3.Run the program with the command "java -jar clidocker-1.0-SNAPSHOT-shaded.jar" on cmd
4.Necessary is to have installed on your computer apache maven and jdk

****IMPORTANT****
THE APP WILL NOT RUN IF YOU DO NOT ADJUST THE J-UNIT TESTS ACCORDING TO YOUR COMPUTER

Indicatively must be done the following 
1.In class "ContainersTest.java" replace on lines 33 and 37 with images of docker containers that you have. Also replace on line 55 with a contaier id that is running and in 58 with a container id that is stoped"
2.In class "DbThreadTest.java" replace in lines 30 and 31 with image amd name of a running docker container and in lines 37 and 38 with image and name of an exited docker container
3.In class "DockerprTest.java" you must change with comtainer id's of your computer. Does not matter if they are running or not.
4.In class "DockerStatisticsTest.java" replace in lines 54, 67 and 70 with a runnning container. Do the sime in lines 57, 76 and 79

UML OF clidocker

HOW TO USE THIS APP
1. When you run the command "java -jar clidocker-1.0-SNAPSHOT-shaded.jar" a menu will appear. Choose an option
2. Do not use as first option the sixth becouse you have not get any docker statistics yet
3. If you choose the sixth option you should give a date of a metric that you did. you can find this metric by run the command SELECT FROM on SSMS.
4. If the date is valid a server will open on port 8080. The server will stay open for 20000 milliseconds
5. By searching on a browser "http://localost:8080/data" three numbers will be shown to you
6. The first is the serial number of the metric that happended this date. The second is the numbers of the containers that they were ruuning this date and the third is the stoped containers

HOW TO SET UP THE DATABASE ON YOUR COMPUTER
  ****IMPORTANT*** 
  YOU SHOULD HAVE INSTALLED MICROSOFT SQL SERVER AND SSMS
1. After you clone the repository on your computer open the SSMS
2. 
