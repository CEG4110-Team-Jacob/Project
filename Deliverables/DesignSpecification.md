# Restaurant Ordering System Design Specification

## Functional

## Database

## Server

Framework: [Java Spring](https://spring.io/)

Tools: [Maven](https://maven.apache.org/), [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

1. The server will be tested via the test package within Spring.
   1. The integration testing will be within its own separate package that tests many different practical API combinations.
   2. The unit testing will be within a package called test. The unit tests for each file will be in a file in test with a similar path.
2. The server will be configurable via a file called .config.json. Json files allow for easy configuration.
3. The only initial dependencies needed are Maven and Java 21. The rest of the dependencies can be installed via Maven automatically. Maven can also build and run the server with one command.
4. The frontend will send the login information to the server via a POST request.  
   1. The server will confirm the login information by accessing the database and checking if the username and password hash is correct.  If it is incorrect, return an invalid credentials error.
   2. If it is correct, return a token that will allow the user to access information via GET requests.
5. The web plugin for Spring has the ability to open up APIs.
   1. When an API is accessed, the server will check if the token is valid, and the token can access the API.
   2. The server will consistently send back JSON formatted data.
6. The server will allow for real time updating via websockets by the plugin Websockets in Spring.
7. The server will connect to the database via the Spring plugin JDBC.
   1. To allow for the switching of databases, the logic for the specific database will be separate from the server logic. 
9. The server will have documentation via Github Wiki on this Github repository under the section [Server](https://github.com/CEG4110-Team-Jacob/Project/wiki/Server).  

## User Interface

- The product manages orders that are taken down by the waiters.
- The product displays all the orders that were made by the waiter. The product also shows the state of the meal that was ordered.
- The product give the manager the ability to assign waiters to tables.
- THe product give the cook the ability to see the orders as soon as they made.
- The product gives the manager the ability to manage staff.
     - Management includes creation of account for new staff, termination of account, ability to see the number of work hours, name etc.
- The product can manage the menu by adding and removing products from it. Also adjusting the names of the items sold. 
