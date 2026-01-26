# Hotel Management System

A simple Java Swing application for managing hotel rooms and customer check-ins.

## Features

- **Room Management**:
  - Add new rooms with number, type, and price.
  - View all rooms in a table.
  - Delete selected rooms.
  - Refresh room list.

- **Customer Management**:
  - Check-in new customers with name, phone, and room number.
  - View all customers in a table.
  - Remove selected customers.
  - Refresh customer list.

## How to Run

1. Ensure you have Java installed on your system (JDK 8 or higher recommended).
2. Navigate to the project directory.
3. Compile the Java files:
   ```
   javac src/*.java
   ```
4. Run the application:
   ```
   java -cp . src.MainUIPro
   ```

## Project Structure

- `src/MainUIPro.java`: Main GUI class that creates the Swing interface for the hotel management system.
- `src/Room.java`: Model class representing a hotel room with id, room number, type, and price.
- `src/Customer.java`: Model class representing a customer with id, name, phone, and room number.
- `src/RoomService.java`: Service class for managing room operations (add, view, delete, update).
- `src/CustomerService.java`: Service class for managing customer operations (check-in, view, remove, update).

## Requirements

- Java Development Kit (JDK) 8 or higher.
- Swing library (included in standard Java distributions).

## Notes

This is a basic implementation for demonstration purposes. In a real-world application, consider using a database for persistent storage instead of in-memory lists.
