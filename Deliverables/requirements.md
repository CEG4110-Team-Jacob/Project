# Minimum Viable Product Requirements

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

User Interface (UI): "the point of human-computer interaction and communication in a device. This can include display screens, keyboards, a mouse and the appearance of a desktop. It is also the way through which a user interacts with an application or a website" ([TechTarget](https://www.techtarget.com/searchapparchitecture/definition/user-interface-UI)).

Minimum Viable Product (MVP): "a product with enough features to attract early-adopter customers and validate a product idea early in the product development cycle. In industries such as software, the MVP can help the product team receive user feedback as quickly as possible to iterate and improve the product" ([ProductPlan](https://www.productplan.com/glossary/minimum-viable-product/)).

# Overall Description

## User Needs

One group of people that will use this product are restaurant owners. They would want the product to be setup with minimum technical expertise. They may also want it to be easily customizable to allow them to design the user interface be specific to their brand and menu.

(Insert customer)

(Insert staff)

## Assumptions and Dependencies

We will assume that the server that is hosting the software will be running Linux (specifically Ubuntu) or Windows. The software will depend on some external tools/libraries that will be determined in the design specifications.

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
8. Server shall have good documentation on:
   1. The initial dependencies to setup the server.
   2. How to setup the server.
   3. The configuration file.
   4. Where the APIs are located via URL.
   5. How to use each API.

## User Interface
