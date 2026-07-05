# Hotel Management System

A console-based Hotel Management System developed using Java, JDBC, and MySQL. This project is designed to manage hotel room reservations efficiently by allowing users to perform CRUD (Create, Read, Update, Delete) operations. It demonstrates core backend development concepts such as database connectivity, SQL query execution, object-oriented programming, and layered architecture.

## Features

- Reserve a room
- View all reservations
- Search room number using reservation details
- Update reservation details
- Delete reservation records
- Exit system safely

## Tech Stack

- Java
- JDBC
- MySQL
- IntelliJ IDEA

## Project Structure

src/
- Main.java
- DBConnection.java
- Reservation.java
- ReservationDAO.java
- HotelService.java

## Database Setup

Create database:

```sql
CREATE DATABASE hotel_db;
USE hotel_db;
```

Create reservations table:

```sql
CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    guest_name VARCHAR(100) NOT NULL,
    room_number INT NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## How to Run

1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Configure MySQL username and password inside DBConnection.java
4. Add MySQL JDBC Driver dependency
5. Run Main.java

## Workflow

- User selects an operation from the menu
- Inputs reservation details
- Data gets stored in MySQL database
- User can retrieve, update, or delete reservation details anytime

## Concepts Used

- JDBC Connectivity
- CRUD Operations
- PreparedStatement
- SQL Queries
- Exception Handling
- Object-Oriented Programming
- Layered Architecture
- MySQL Database Integration

## Future Enhancements

- Room availability checking
- Billing system
- Customer check-in/check-out
- Admin authentication
- Booking history
- Spring Boot REST API conversion
- Frontend integration

## Learning Outcomes

Through this project, I learned how to connect Java applications with databases using JDBC, perform CRUD operations, structure backend applications using layered architecture, handle SQL exceptions, and build modular applications.

## Author

Hemanth  
B.Tech CSE | Java Backend Developer
