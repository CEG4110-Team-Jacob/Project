<h1 style="font-size:xxx-large"> Minimum Viable Product Requirements </h1>

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
The purpose of this software is to increase the efficiciency and smooth operation of the end user. The software will ensure proper monitoring of inventory and financial resources. The product will improve waiter and table efficiency and improvement of customer experience. The product will ensure the restaurant's top priority of customer satisfaction. 

## Intended Audience

## Intended Use

## Product Scope

## Definitions and Acronyms

# Overall Description

## User Needs

## Assumptions and Dependencies

# System Features and Requirements

## Functional Requirements

## Database

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

## User Interface
