# Restaurant Ordering System Requirements

Design from [Perforce](https://www.perforce.com/blog/alm/how-write-software-requirements-specification-srs-document)

## Table of Contents

1. [Introduction](#introduction)
   1. [Purpose](#purpose)
   2. [Intended Audience](#intended-audience)
   3. [Intended Use](#intended-use)
   4. [Product Scope](#product-scope)
   5. [Definitions and Acronyms](#definitions-and-acronyms)
2. [Overall Description](#overall-description)
   1. [User Needs](#user-needs)
   2. [Assumptions and Dependencies](#assumptions-and-dependencies)
3. [Sytstem Features and Requirements](#system-features-and-requirements)
   1. [Functional Requirements](#functional-requirements)
   2. [Database Requirements](#database)
   3. [Server Requirements](#backendserver)
   4. [User Interface Requirements](#user-interface)

# Introduction

## Purpose

The purpose of this software is to increase the efficiciency and smooth operation of restaurants taking food/beverage orders. This includes software that allows waiters to take orders easily on a device. The product will improve waiter, table efficiency, and customer experience. The product will ensure that the restaurant's top priority is customer satisfaction.

## Intended Audience

This document is intended for the developers to help them develop a usable software. It is also intended for any stakeholders to understand what software the developers intend to build.

This software is intended for use by the host and wait staff positions. The restaurant owner will also have access to the software.

## Intended Use

The intended use of this document is to define some minimum features the software will have when it is delivered. This will allow the developers to focus on the features that matter and others to understand what the software features.

The host will use this software in order to increase seating efficiency and checking for strain in the waiters' individual sections. The waiter's use of this software will be access to inventory data in order to see what items are available. The owner will have configuration privileges.

## Product Scope

This software is intended to improve restaurant efficiency and customer experience, which can in turn lead to more profit for the owner and shareholders. The current goal is to deliver a minimum viable product for availability.

## Definitions and Acronyms

Application Programming Interface (API): "a set of rules or protocols that let software applications communicate with each other to exchange data, features and functionality" ([IBM](https://www.ibm.com/topics/api)).

Database: "organized collection of structured information, or data, typically stored electronically in a computer system" ([Oracle](https://www.oracle.com/database/what-is-database/)).

Relational Database: "a type of database that store and organize data points with defined relationships for fast access" ([Microsoft Azure](https://azure.microsoft.com/en-us/resources/cloud-computing-dictionary/what-is-a-relational-database/?ef_id=_k_CjwKCAiA8sauBhB3EiwAruTRJhWk66wnEY7wl2YO85tTu3OlnYSV0-RI5IwkS3k8PMMEYL4hNIK1UhoC99gQAvD_BwE_k_&OCID=AIDcmme9zx2qiz_SEM__k_CjwKCAiA8sauBhB3EiwAruTRJhWk66wnEY7wl2YO85tTu3OlnYSV0-RI5IwkS3k8PMMEYL4hNIK1UhoC99gQAvD_BwE_k_&gad_source=1&gclid=CjwKCAiA8sauBhB3EiwAruTRJhWk66wnEY7wl2YO85tTu3OlnYSV0-RI5IwkS3k8PMMEYL4hNIK1UhoC99gQAvD_BwE))

Java Database Connectivity (JDBC): "the JavaSoft specification of a standard application programming interface (API) that allows Java programs to access database management systems." ([IBM](https://www.ibm.com/docs/en/informix-servers/12.10?topic=started-what-is-jdbc))

Structured Query Language (SQL): "a programming language for storing and processing information in a relational database" ([AWS](https://aws.amazon.com/what-is/sql/#:~:text=Structured%20query%20language%20(SQL)%20is%20a%20standard%20language%20for%20database,undergoes%20continual%20upgrades%20and%20improvements.))

User Interface (UI): "the point of human-computer interaction and communication in a device. This can include display screens, keyboards, a mouse and the appearance of a desktop. It is also the way through which a user interacts with an application or a website" ([TechTarget](https://www.techtarget.com/searchapparchitecture/definition/user-interface-UI)).

Minimum Viable Product (MVP): "a product with enough features to attract early-adopter customers and validate a product idea early in the product development cycle. In industries such as software, the MVP can help the product team receive user feedback as quickly as possible to iterate and improve the product" ([ProductPlan](https://www.productplan.com/glossary/minimum-viable-product/)).

# Overall Description

## User Needs

One group of people that will use this product are restaurant owners. They would want the product to be setup with minimum technical expertise. They may also want it to be easily customizable to allow them to design the user interface be specific to their brand and menu.

Another group of people that the product will be of use to are the customers of the restaurant. They would want to have a reliable experience in the restaurant, meaning no order should be left out.

The product will help the staff in things like table management. The staff especially the waiters would know what table(s) that they are assigned to. The staff both front staff and back staff would have knowledge of the inventory helping them know what things can be sold to the customers.

There would be a smoother communication between front end and back end staff. This would help with the front end staff relay what things the customers what and what time the things are available for pick up.

## Assumptions and Dependencies

We will assume that the server that is hosting the software will be running Linux (specifically Ubuntu) or Windows. The software will depend on some external tools/libraries that will be determined in the design specifications.

# System Features and Requirements

## Functional Requirements

1. The product should manage orders.
   1. An order consists of food/drinks, date and time when the order was placed and when it was completed, a state of completion, waiter who placed it, an id, and total price.
      1. The waiter should only see the date placed, the food, completion state, and price.
      2. The cook should only see the food, when it was placed, and state of completion.
   2. The waiters should be able to order foods/drinks that the cooks can see in real time.
   3. The cooks should be able to manage the state of the order. For example, cooking, ready.
   4. The waiters should be informed when the food is ready to serve.
   5. The tables should be assigned to the waiters. 
        
2. The product should be able to manage staff account.
     1. Staff account should conist of first name, last name, work ID, login time, logout time, work hours, age, sex, job ID, job title. 
3. The product should be able to manage the menu.
      1. The menu should consists of name, type of dish, price, description. 
      2. The menu should stay constantly updated.
      3. The menu should be able to be changed depending on the restaurants needs.
   

## Database
1. Database will allow for different view configurations
   1. Waiters will have access to food and drink data as well as data that can calculate for serving time
   2. Cooks will have access to order based data
   3. Owners have access to table layout data
2. Database will communicate with server for real time data gathering and updating using server api
   1. The database will use SQL while the server will use JDBC to communicate with the database
3. Database will use a relational database management system model
4. Database will have documentation on the following
   1. How to perform queries, data insertion, item addition and deletion
   2. How to perform initial database setup
   3. The configuration file
   4. How to set up seperate views


## Backend/Server

1. Server shall have tests on all code that can be tested
   1. Server shall have integration tests
   2. Server shall have unit tests
2. Server shall be easily configurable.
3. Server shall be compiled and run easily with minimum dependencies.
4. Server shall accept login information
   1. Server shall confirm the login is correct,
   2. Server shall return a token for further access of data.
5. Server shall have APIs
   1. Shall make sure the user using the token can access the API.
   2. Shall return data in a consistent format.
   3. Shall have APIs for everything the UI wants.
6. Server shall allow real time updating data of the UIs.
7. Server shall be able to connect to the database
   1. Shall be able to switch databases without a rewrite of the entire server.
8. Server shall have good documentation on:
   1. The initial dependencies to setup the server.
   2. How to setup the server.
   3. The configuration file.
   4. Where the APIs are located via URL.
   5. How to use each API.

## User Interface

1. The software shall be able to be closed using a button labeled 'exit'.
2. The software shall allow the selection of individual tables.
     1. The user shall be able to assign groups of tables to certain waitstaff.
     2. The user shall be able to view the tables that each waitstaff are responsible for.
3. The software shall allow the user to reconfigure the table layout.
     1. Managerial staff shall be able to move, add, and remove tables as necessary.
4. The software shall allow the user to reconfigure the menu.
      1. Managerial staff shall be able to add and remove items from the menu.
      2. Managerial staff shall be able to edit the price of menu items.
5. The software shall allow a user to swap to a different account.
     1. The amount of features usable by a specific user shall be dependent on the specific type of user.
     2. Managerial staff shall have access to all of the features that other staff members have access to.
           1. Managerial staff shall be able to view the current status of other staff members.
           2. Managerial staff shall be able to message all staff members.
6. The user shall be able to change views between the restaurant layout and information about the menu and orders.
7. The User Interface shall have proper documentation.
      1. Documentation will be included for how to use the User Interface as each different staff position.
            1. This includes managerial staff, waitstaff, host staff, and kitchen staff.
