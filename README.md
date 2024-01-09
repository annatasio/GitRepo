# GitRepo
This is our project for subject Programming II
It is about managing docker desktop!!

INSTRUCTIONS FOR PACKAGE "clidocker"
1. At CMD, move into the project folder named "clidocker" where the pom.xml is
2. Compile the program with the command "mvn clean istall"
3. Run the program with the command "java -jar clidocker-1.0-SNAPSHOT-shaded.jar" on cmd (move to target folder first)
4. Necessary is to have installed on your computer apache maven and jdk

****IMPORTANT****
THE APP WILL NOT RUN IF YOU DO NOT ADJUST THE J-UNIT TESTS ACCORDING TO YOUR COMPUTER

Indicatively must be done the following 
1. In class "ContainersTest.java" replace on lines 33 and 37 with images of docker containers that you have. Also replace on line 55 with a contaier id that is running and in 58 with a container id that is stoped"
2. In class "DbThreadTest.java" replace in lines 30 and 31 with image amd name of a running docker container and in lines 37 and 38 with image and name of an exited docker container
3. In class "DockerprTest.java" you must change with comtainer id's of your computer. Does not matter if they are running or not.
4. In class "DockerStatisticsTest.java" replace in lines 54, 67 and 70 with a runnning container. Do the sime in lines 57, 76 and 79

UML OF "CLIDOCKER"

HOW TO USE THIS APP
1. When you run the command "java -jar clidocker-1.0-SNAPSHOT-shaded.jar" a menu will appear. Choose an option
2. Do not use as first option the sixth becouse you have not get any docker statistics yet
3. If you choose the sixth option you should give a date of a metric that you did. you can find this metric by run the command SELECT FROM on SSMS.
4. If the date is valid a server will open on port 8080. The server will stay open for 20000 milliseconds
5. By searching on a browser "http://localost:8080/data" three numbers will be shown to you
6. The first is the serial number of the metric that happended this date. The second is the numbers of the containers that they were ruuning this date and the third is the stoped containers

HOW TO SET UP THE DATABASE ON YOUR COMPUTER
  ****IMPORTANT**** 
  YOU SHOULD HAVE INSTALLED MICROSOFT SQL SERVER AND SSMS
1. After you clone the repository on your computer open the SSMS and connecti to the server
2. On the left, you will see a folder named "Databases". Right click on it
3. Choose option "Import Data-tier application"
4. On "Introduction" field press next (bottom right)
5. Choose "Import from local disk" and add the path of the BACPAC file. This file is in GitRepo\clidocker\src\main\resources
6. On "New database name" change it as "demo1" not "demo".
7. Press next and finish
8. The database must have been created succesfully

A FEW WORDS FOR THE STRUCTURE
1. This app uses external library "docker-java" for connection with docker daemon and performimg actions about docker containers
2. Threads have been created for start or stop execution, statitics and DB connection
3. We also created a simple REST API for connection with the database and get the serial number of metric
4. MAVEN build tool has been used for compilation and creation of the final jar file
5. CHECKSTYLE helped us with our errors, peroformamve and appearance of our app
6. We created J-UNIT tests for testing our methods that they work properly

INSTRUCTIONS FOR PACKAGE "guidocker"
1. At CMD, move into the the project folder named "guidocker", where the pom.xml is
2. Compile the program with the command "mvn clean istall"
3. Run the programm with double click on jar file named "guidocker-1.0-SNAPSHOT.jar" (inside the target folder) or
4. Run the programm with via CMD, with the command "java -jar guidcoker-1.0-SNAPSHOT.jar" (move to target folder first)
5. Necessary is to have installed on your computer apache maven and jdk

UML OF "GUIDOCKER"

HOW TO USE THIS GRAPHICAL APP
1. When the jar file opens, it will appear the name of the app and a button. Press start
2. A menu will appear with some options about docker engines and containers
3. Choose one and considering what you have chosen the app will let you run or stop a docker container or will let you see the running containers


