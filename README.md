# GitRepo 
## Gen0 Programming Team 
### Project: Programming II - Docker Desktop Management
## Academic Free License v. 3.0
Version 3.0
Submitter: Lawrence Rosen
SPDX short identifier: AFL-3.0

## This is our project for the subject Programming II. It is about managing Docker Desktop.

## Instructions for Package "clidocker"

1. Open CMD and navigate to the project folder named "clidocker" where the `pom.xml` is located.
2. Compile the program with the command `mvn clean install`.
3. Run the program with the command `java -jar clidocker-1.0-SNAPSHOT-shaded.jar` on CMD (move to the target folder first).
4. It is necessary to have Apache Maven and JDK installed on your computer.
5. Your SQL driver must be configured for windows authentication and intergrated security

## UML of "clidocker"

![Στιγμιότυπο οθόνης 2024-01-11 100704](https://github.com/annatasio/GitRepo/assets/147800087/135636cf-35db-49d4-87b6-f6cbca8c85c1)


### How to Use This App

1. When you run the command `java -jar clidocker-1.0-SNAPSHOT-shaded.jar`, a menu will appear where you have the ability to choose an option.
2. Prioritize collecting Docker statistics **before** considering the sixth option as your initial choice.
3. For the sixth option, it's necessary to provide the date of a metric you've collected. You can find this metric by running the command `SELECT FROM` in SSMS.
4. If the date is valid, a server will open on port 8080. The server will stay open for 20000 milliseconds.
5. By searching on a browser `http://localhost:8080/data`, three numbers will be shown to you. The first number represents the serial number of the metric recorded on this date. The second number indicates the count of Docker containers that were running on this date. The third number represents the count of Docker containers that were stopped on this date.

## How to Set Up the Database on Your Computer

**Important:** You should have Microsoft SQL Server and SSMS installed.

1. After you clone the repository on your computer, open the SSMS and connect to the server.
2. On the left, you will see a folder named "Databases". Right-click on it.
3. Choose the option "Import Data-tier application".
4. On the "Introduction" field, press next (bottom right).
5. Select 'Import from local disk' and include the path of the BACPAC file, which is located in `GitRepo\clidocker\src\main\resources`.
6. On "New database name", change it to "demo1", not "demo".
7. Once you've done the above, press 'Next' and then 'Finish'. Upon completion, the database should have been created successfully.

## A Few Words for the Structure

- This app uses the external library "docker-java" for connection with the Docker daemon and performing actions about Docker containers.
- Threads have been created for starting or stopping execution, gathering statistics, and managing DB connection.
- We also created a simple REST API for connection with the database and get the serial number of the metric.
- Maven build tool has been used for compilation and creation of the final jar file.
- Checkstyle helped us with our errors, performance, and appearance of our app.
- We created J-Unit tests for testing our methods that they work properly.

## Instructions for Package "guidocker"

1. Open CMD and navigate to the project folder named "guidocker", where the `pom.xml` is located.
2. Compile the program with the command `mvn clean install shade:shade`.
3. Run the program with a double click on the jar file named `guidocker-1.0-SNAPSHOT.jar` (inside the target folder) or
4. Run the program via CMD, with the command `java -jar guidocker-1.0-SNAPSHOT.jar` (move to the target folder first).
5. As before, it's is necessary to have Apache Maven and JDK installed on your computer.

## UML of "guidocker"

![Στιγμιότυπο οθόνης 2024-01-11 100721](https://github.com/annatasio/GitRepo/assets/147800087/c0b0536d-5f2b-4b77-9c77-f010f07bb995)


### How to Use This Graphical App

1. When the jar file opens, the name of the app and a button will appear. Press start.
2. A menu will appear with several options regarding Docker engines and containers.
3. Select one, and based on your choice, the app will enable you to start or stop a Docker container or view the running containers.

## A Few Words for the Structure

- In this app has been incorporated a REST API that we created
- This REST API connects with the localhoat on port 2375. The local docker daemon. In that way we have access to all of our containers and images
- Maven build tool has been used for compilation and creation of the final jar file.
- Checkstyle helped us with our errors, performance, and appearance of our app.
- We created J-Unit tests for testing our methods that they work properly.

