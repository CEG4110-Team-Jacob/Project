# Restaurant Ordering System Design Specification

## Functional
1. The waiters view of the table will feauture the name, table number, time of order, list of items on the menu. After selection of the orders for the table, the total price, tax and the list of items will be listed in the waiters tab. As soon as the order is taken, the list will be displayed on the cooks side.
2. The waiters table view will display the tables that the waiter has been assigned to by the manager. In the section of the tables, the list of items orders by each table will be displayed and the statues of each item will be displayed. The name of the waiter will be displayed on the top of this section of the waiters tab.
3. In the cooks tab, there would be an overview section that displays the different orders that have been taken down by the waiter. The orders would be arranged in a list of time priority. The cooks tab will be go into one of the tables and select the status of each item ordered by the table. The status of each table will diplay done as soon as all items on the table are selected as ready.
4. The managers tab will have different sections that can be selected by the manager.
   1. In the section of menu, there will be a display of product just as the same way it shown on the menu. There will be an edit section on the page. If the button is clicked, there will be buttons that allow the manager to both add, click or edit items in the menu.
   2. In the staff section, there will be a button that displays all the workers, the login time, and total work hours.
   3. In the section of staff, there will be buttons representing the staff that will give the manager informations like login time, work hours, tables that workers are assigned to and the status of each table. There will also be a button that allows the manager to delete the account of the staff.
   4.  Another section of the staff section, there will be a button that displays the orders. In this section, the tab will display the list of waiters as button and upon clicking the bottons, there will be a list of tables represented as buttons. Clicking each of the tables, will automatically assign the selected tables to the staff initially clicked on.
   5.   Another section of the staff section will display the create account tab. In this tab, there are questions prompting the manager for the new staffs information in order to create an account.
5. In the table section, the tables will be displayed and the status of each table will also be displayed. 

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

