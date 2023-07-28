<h1 align = "center"> Blogging-Platform-API👋</h1>

<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-3.0.5-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.6.0-brightgreen.svg" />
</a>
<a >
    <img alt="MySQL" src="https://img.shields.io/badge/MySQL-blue.svg">
</a>
</p>
<br>

## Framework Used and Create Project
* Spring Boot
* spring initializr

---
<br>

## Dependencies
The following dependencies are required to run the project:

* Spring Boot Dev Tools
* Spring Web
* Spring Data JPA
* MySQL Driver
* Lombok
* Validation
* Swagger

<br>

## Database Configuration
To connect to a MySQL database, update the application.properties file with the appropriate database URL, username, and password. The following properties need to be updated:
```
spring.datasource.url= jdbc:mysql://localhost:3306/<DatabaseName>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update


spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true


```
<br>

## Language Used
* Java

---
<br>

## Data Model

The Job data model is defined in the Job class, which has the following attributes:
<br>

* User Model
```
Id : Long
userName : string
userHandle : string
userAge : string
useremail : string
userpassword : string
userPhoneNumber : string
followerCount : Integer
```

* Post Model
```
postId = Long
title : String
@ManyToOne
Post : User


```
* Comment Model
```
commentId = Long
content : String
@ManyToOne
commenter : user
@ManyToOne
Comment : blogPost


```


* Follow Model
```
followId : Long
@ManyToOne
follow : currentUser;
@ManyToOne
follow : currentUserFollower
```


## Data Flow

1. The user at client side sends a request to the application through the API endpoints.
2. The API receives the request and sends it to the appropriate controller method.
3. The controller method makes a call to the method in service class.
4. The method in service class builds logic and retrieves or modifies data from the database, which is in turn given to controller class
5. The controller method returns a response to the API.
6. The API sends the response back to the user.

---

<br>


## API End Points 

The following endpoints are available in the API:

* User Controller:
```
POST /user/signUp : create a new user account
POST /user/signIn : login a existing user account
POST /user/post : create a new user post account
POST /user/follow : become friend of other user
POST /user/comment : write on other post

GET /user/{username}: get user by username
GET /user/getPostMy: get all my post
GET /user/comment: get all comment on post

PUT /user/{postid}: update user details
PUT /user/comment: update user comment

DELETE /user/comment: Delete out comments commented
DELETE /user/post: Delete only own post
DELETE /user/signout: Delete token 


```



<br>

## DataBase Used
* SQL database
```
We have used Persistent database to implement CRUD Operations.
```
---
<br>

## Project Summary

This Blogging Platform is a Backend Appliction that allows users to create, read, update, and delete blog posts and comments. It provides a user-friendly interface for bloggers to share their thoughts and engage with their readers. Users can also search for posts and follow other bloggers to stay updated with their latest content using Swagger.




