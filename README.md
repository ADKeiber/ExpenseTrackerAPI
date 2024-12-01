# Expense Tracker API
API that is used to create and manage expenses using Spring Boot Framework

<b> Table of Contents </b> <br>
- [Description](#description)
  - [Endpoint Operations](#endpoint-operations)
  - [Expense Filtering Options](#expense-filtering-options)
  - [Application Technical Features](#application-technical-features)
- [How to Run](#how-to-run)
- [Screenshots](#screenshots)

## Description
Expense Tracker API is a Spring Boot application that allows users to create/manage accounts, and expenses attached to those accounts. <br>
This application is protected by Spring security and requires a User to get a valid JWT Bearer token to interact with most endpoints.<br>
On top of that, authenticated users are also limited to non-admin endpoints unless their account contains the "ADMIN" role. <br>
<br>
<b>NOTE:</b> This application is part of a learning experience and is the third project in my backend learning journey. The path of my journey closely follows this roadmap... https://roadmap.sh/backend/project-ideas <br>
<br>
![image](https://github.com/user-attachments/assets/9cd8232d-2d93-4e25-ae06-4789607842f5)
<br>
### Endpoint Operations

* Sign up a new user
* Update users
* Delete users
* Generate and validate JWTs for handling authentication and user sessions.
* Add new expenses
* Remove existing expenses
* Update existing expenses
* Filter through expenses
* Create new Expense Categories

### Expense Filtering Options
- Transaction Date  
   There are multiple predefined transaction date periods (past week, past month, past 3 months),  
   but there is also the option to filter on a custom date. User ID is also required to filter by date.
- User ID  
   The User ID can be used in conjunction with the transaction date and category to further filter through a user's expenses
- Category  
   User ID is also required to filter by category.
  
### Application Technical Features
* Spring Boot 3.4.0
* Java 17
* Spring Security
* JWT Authentication
* Spring Doc OpenAPI 2.7 documentation
* MongoDB database
* Lombok

## How to Run 
To run the application you will need to have Maven, Java, NodeJS, and MongoDB installed. <br>

My local environment versions:
- Maven 3.9.9
- Java 17.0.9
- NodeJS 18.18.2
- MongoDB 8.0

<br>
<b> NOTE: </b> NodeJS is needed to execute JS script to populate the database. Data can be populated by using endpoints except for making a user an ADMIN which can only occur with existing admin access. These can be manually entered into a database as well.<br>
<br>

Certain ports must be open or already running the service
- localhost:8080 for API  
- localhost:27017 for MongoDB


## Screenshots






