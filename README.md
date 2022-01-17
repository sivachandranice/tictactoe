# tictactoe
Tic Tac Toe backend API development based on Spring boot

# **Tech Stack:**

   * SpringBoot
   * Lombok
   * Mapstruct
   * Spring Data JPA
   * H2 in-memory DB
   * Mockito
   * MockMvc
   * Swagger
 
 # Topics
 
 * How to run this application
 * How to access the spring boot restful application
 
 # This is the springBoot based RESTful API. It supports the below functions:
 
 * Create a new game
 * List all games
 * Find a game via id
 * Perform a move in a game & determine the status of a game & return the winner if there is one

# How to run this application

* Navigate the the root folder /tictactoe under the command line
* run the command to build the whole project: **mvn clean install**
* run the command to start the application: java -jar ./target/tictactoedemo-0.0.1-SNAPSHOT.jar

# How to access Spring booe rest apis

You may access swagger doc by accessing below url once application is up and running

http://localhost:8080/swagger-ui.html
This is swagger summary screenshot
![image](https://user-images.githubusercontent.com/20538746/149687924-e3a62b21-7f27-42b0-9cc4-9314776f143c.png)

# Advantages of this application
* Integrated with swagger doc which easily provides with the RESTful API UI and can be easily shared with other developers to use use API
* Spring Data JPA builds the repository layer (DAO) & H2 in-mem database used as Unit Test to test this layer
* Mapstruct used to map data between entities and dtos
* All the exceptions can be centrally handled in one place (ControllerExceptionHandler.java)
* Lombok makes our life easierIt automatically generates getter,setter, constructor, hashcode, log etc.
