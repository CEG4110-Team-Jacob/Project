# Minimum Viable Product Showcase

## Server

1. Tests: found [here](../server/src/test/java/).
   ![Showcase of Tests](../gifs/Server%20Tests.gif)

2. An example Configuration can be found [here](../server/src/main/resources/application.example.properties)

3. The minimum dependencies are Java 21, and MySQL (Database).

4. Login with username and password.
   ![Login](../gifs/Login.gif)
   Curl
   ![Curl GET](../img/CurlLogin.png)

5. APIs can be found [here](../server/src/main/java/)

6. Real time updating (Polling)
   ![](../gifs/Updating.gif)

7. The database is connected via ConnectorJ.
   ![Database](../img/DatabaseConnecting.png)

8. Documentation can be found [here](https://github.com/CEG4110-Team-Jacob/Project/wiki/Server)


## Function Requirements

1. The software allows the users to manage orders. 
![OrderToCook](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/53806115-6cbb-4e3d-9f4e-177f4af1c00b)
![CookOrder](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/fc548ecf-d4b2-4d33-af18-885019ddd8ad)

![CookChangeStatus](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/f4afbb61-0c88-403a-a2cb-4f61b8e1420d)
![WaiterServe](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/d535d218-6ccf-4593-99ce-551f8ee6b7f5)


2. The software allows the manager to manage the staff accounts. 

![Create Account](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/01372e29-efe0-4e1f-ab35-6f8d05f30e1d)

4. The software allows for management of the menu. 
![Menu Management](https://github.com/CEG4110-Team-Jacob/Project/assets/102489053/7ea95c6b-dc6d-4ae6-b96d-bcea7a26f1fd)

## Database Requirements
1. Database ERR Diagram can be found [here](https://github.com/CEG4110-Team-Jacob/Project/blob/main/server/database/RestoDiagram.pdf)
2. Database and Views Implementation can be found [here](https://github.com/CEG4110-Team-Jacob/Project/blob/main/server/database/RestoDatabase.sql)
3. Database Documentation is pending due to issues with creating the database.

## UI Requirements
1. The button exists. ![Exit](../img/ExitButton.png)
2. The manager's view of the tables. ![here](../img/TableView.png)
      1. The manager can assign waitstaff to tables.
      2. The manager can see which waitstaff belongs to which table.
3. The manager can add and delete tables as necessary. ![here](../img/TableCreate.png)
4. The manager can edit the menu as they wish. ![here](../img/Menu.png)
      1. The manager can add or delete items.
      2. The manager can edit the price of the items.
5. The user can use the logout button ![logout](../img/Logout.png) to be redirected to the login page to change accounts. ![login](../img/Login.png)
      1. The pages viewable by a user is dependent on their role
           1. Waiter: ![waiter](../img/WaiterUI.png)
           2. Host: ![host](../img/HostUI.png)
           3. Cook: ![cook](../img/CookUI.png)
           4. Manager: ![manager](../img/ManagerUI.png)
      2. The manager has access to all the pages except for the cooks "orders" page.
            1. The manager can view information on other staff members. ![manager view](../img/StaffInformation.png)
6. The manager can swap views at any time by clicking the exit button and then a different page to view.
8. Documentation has not been written at this time.
