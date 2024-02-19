# Restaurant Ordering System Design Specification

## Functional

## Database

## Server

Framework: [Java Spring](https://spring.io/)

Tools: [Maven](https://maven.apache.org/), [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

1. The server will be tested via the test package within Spring
  1. The integration testing will be within its own separate package that tests many different practical API combinations.
  2. The unit testing will be within a package called test.  The unit tests for each file will be in a file in test with a similar path.
2. The server will be configurable via a file called .config.json.  Json files allow for easy configuration.
3. The only initial dependencies needed are Maven and Java 21.  The rest of the dependencies can be installed via Maven automatically.  Maven can also build and run the server with one command.

## User Interface
