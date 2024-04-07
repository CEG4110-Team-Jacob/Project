# Restaurant Ordering System Design Specification

## Functional
![image](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/64d075f3-e01e-49b4-bc93-83616f4c4d9d)

![image](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/d40fa14f-3b08-4aa0-9600-de2a4eebf165)



1. The waiters view of the table features the name of the waiter and table number assigned to the waiter by the manager, current time, list of items on the menu as a result of their availability in stock. After selection of the orders for the table, the total price, tax and the list of items are listed in the waiters tab. As soon as the order is taken, the list are displayed on the cooks side.
![image](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/427d5640-45b3-4fac-a5d1-50a63638b820)



2. The waiters table view displays the tables that the waiter has been assigned to by the manager. 
![WaiterServe](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/eca13d3a-d306-42eb-8741-06213f11b56e)


3. In the cooks tab, there is an overview section that displays the different orders that have been taken down by the waiter. The orders are arranged in a list of time priority. The cooks tab goes into one of the tables and select the status of each item ordered by the table. The status of each table is then diplay done as soon as all items on the table are selected as ready.
![CookOrder](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/4216787f-d24c-4139-8b3b-47e040ea1e78)


4. The managers tab has different sections that can be selected by the manager.
![image](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/4c238a61-9fdf-4cb6-8597-3e10165b03e4)


   1. In the section of menu, there is a display of product just as the same way it shown on the menu. There is an edit section on the page. when the button is clicked, there are buttons that allow the manager to both add, click or edit items in the menu.
![Menu Management](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/3e0aa187-445f-4ca9-9cee-47326e09c127)

   
   2. Another section of the staff section displays the create account tab. In this tab, there are questions prompting the manager for the new staffs information in order to create an account.![Create Account](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/3e299c65-1f07-4405-b6bf-b633f5550a90)


5. In the table section, the tables are displayed and the status of each table is also displayed.![TableAssignment](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/ef6fd5dc-b92d-4fce-ba67-402b5e0fe01f)


## Database

Tools: [Oracle XE](https://www.oracle.com/database/technologies/appdev/xe.html)

1. The database will use a relational database model
2. The database will use Oracle XE.
3. The logical database schema shall be as follows:
   1. There will be four tables for cooks, waiters, tables
      1. Cooks will be related to waiters
      2. Waiters will be related to the cooks and tables tables
      3. tables will be related to waiters
   2. Cooks, waiters, and tables will all have unique keys which will also be used as foreign keys
   3. Orders will be a field in the tables' table
   4. Logical Constraints must be placed such that unique IDs exist for all table entries
      1. Further constraints will be placed om the waiter and cook tables such that valid entries require first names, last names, and contact information
4. The database views will be as follows:
   1. The manager will have access to all tables
   2. Waiters will have access to a limited form of the tables table in regards to orders
   3. Cooks will have access to the waiters table data on orders assosciated with the waiters
   4. Hosts/Hostesses will have access to a limited form of the tables table in regards to table occupancy
5. The database can communicate in real time with the servers as the Oracle XE Database can allow the Java based server to communicate with the database via JDBC
6. The database will have documentation on the following:
   1. How to perform queries, item addition and deletion, and data insertion
   2. How to perform the initial data setup
   3. The configuration file
   4. How to set up the seperate views for roles
   5. All documentation will be found under the [Database](https://github.com/CEG4110-Team-Jacob/Project/wiki/Database) section of the Github Wiki

## Server

Framework: [Java Spring](https://spring.io/)

Tools: [Gradle](https://gradle.org/), [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

1. The server will be tested via the test package within Spring.
   1. The integration testing will be within its own separate package that tests many different practical API combinations. `e. located` [here](../server/src/test/java/com/restaurantsystem/api/integrations/)
   2. The unit testing will be within a package called test. The unit tests for each file will be in a file in test with a similar path. `e. located` [here](../server/src/test/java/com/restaurantsystem/api/)
2. The server will be configurable via a file called application.properties in the standard Gradle application. `e. Example exists` [here](../server/src/main/resources/application.example.properties)
3. The only initial dependencies needed are Java 21 and a database MySQL is used. The rest of the dependencies can be installed via Gradle automatically. Gradle can also build and run the server with one command. `e. less than five initial dependencies`
4. The frontend will send the login information to the server via a GET request.

   1. The server will confirm the login information by accessing the database and checking if the username and password hash is correct. If it is incorrect, return an invalid credentials error.
   2. If it is correct, return a token that will allow the user to access information via GET requests.

   `e. Demonstration` ![Login](../gifs/Login.gif)
   ![Curl GET](../img/CurlLogin.png)

5. The web plugin for Spring has the ability to open up APIs.

   1. When an API is accessed, the server will check if the token is valid, and the token can access the API.
   2. The server will consistently send back JSON formatted data.

   Diagram
   ![](../img/Login%20Diagram.png)

   `e. Demonstration` ![](../img/APICurl.png)

6. The server will allow for real time updating via polling (constantly sending GET Requests). `e. It exists`
7. The server will connect to the database via the Spring plugin JPA. `e. it exists` [here](../server/src/main/java/com/restaurantsystem/api/data/)
   1. To allow for the switching of databases, the logic for the specific database will be separate from the server logic. `e. It is through interfaces for queries` [queries](../server/src/main/java/com/restaurantsystem/api/repos/)
8. The server will have documentation via Github Wiki on this Github repository under the section [Server](https://github.com/CEG4110-Team-Jacob/Project/wiki/Server). `e. It exists`

## User Interface

1. The program will have a button located at the top right of the screen labeled "Exit". `e. It exists` ![Exit Button](../img/ExitButton.png)
   1. Clicking this button will close out of the program. `e. Clicking the button logs out the user`
2. Managerial staff will be able to see information that other users cannot see. `e. This is true`
   1. Managerial staff will be able to see which waitstaff is assigned to which table. `e. Managers can see this information`
      1. This will be done by selecting either the table the manager wants to know more information about or by selecting the waitstaff they want to know more about. `e. This works`
   2. Managerial staff will be able to select tables and assign waitstaff to groups of tables. `e. This works`
      1. This will be done by selecting multiple tables and choosing which waitstaff the manager wants assigned to those tables. `e. This works`
3. Managerial staff will be able to edit the layout of the restaurant. `e. Managers can edit the table layout`
   1. This will be done by selecting a button labeled "Edit" while looking at the restaurant's layout. `e. Managers delete and add tables from the same UI they view it from` ![table UI](../img/TableView.png)
      1. Selecting this button will allow management to add, remove, and move around tables at will. `e. Managers edit tables a different way`
4. Managerial staff will be able to edit the contents of the menu. `e. This works`
   1. This will be done by selecting a button labeled "Edit" while looking at the restaurant's menu. `e. This works similar to tables` ![Menu UI](../img/Menu.png)
      1. Selecting this button will allow management to add, remove, and otherwise edit menu items. `e. This works`
5. The program will have a button labeled "Swap Account" at the rop right of the screen, next to the button labeled "Exit". `e. Instead, clicking the exit button with logout the user` ![Exit Button](../img/ExitButton.png)
   1. When this button is selected the current user is logged out and the user is prompted with a username and password field. `e. 'Exit' has this functionality`
   2. Depending on the type of account, different information/permissions will be available. `e. This is true`
6. The program will have various tabs that change the view from one thing to another, these tabs will be available on different account types. `e. This exists`
   1. Management will have access to every tab, and all of the information associated with each tab. `e. Managers have access to every tab except for the cook's tab` ![Manager UI](../img/ManagerUI.png)
   2. The program will check the permissions of the user before displaying any information as to not give the wrong staff member too much or too little information. `e. This is done on log in`
7. The user interface will have documentation via Github Wiki on this Github repository under the section [User Interface](https://github.com/CEG4110-Team-Jacob/Project/wiki/User-Interface). `e. This does not exist`
8. The user interface will be programmed using [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/) `e. This is true`
