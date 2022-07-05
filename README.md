# Spring Web Application - Chatter
This project was created for educational purposes. It is not fully implemented. It is a **demo version**.

## How to start the project locally
* **Clone** the project in your local folder
* **Run _gradle build_** scripts to install needed dependencies
* **Run** the project

## Project description
This is a demo live chat application that manages user communication.

**Users Authentication**

In order to *register* a new user, the application has specific route that creates and saves the user in the database.
*Login* requests are processed by the **Spring Security** module.

**Database**

For the purposes of the project, *ORM* is used, which allows working with different database distributions. 
In Spring, this is implemented by **Spring Data JPA**.

**Sockets**

To establish a real-time connection between users, **Websockets** are needed. They are easy customizable by the Spring context and connected to the logged user session.

## Functionalities
* Login
* Register
* Send message *(real-time)*
* Update seen status *(real-time)*
* Update online status *(real-time)*

