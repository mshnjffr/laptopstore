# Laptop Store Web Application

## Overview
A web-based laptop store application built with Spring Boot and Thymeleaf. This application allows users to browse, search, and order laptops through a simple, intuitive web interface.

## Features

- **Browse Products**: View all available laptops and computer mice with details like brand, model, and price
- **Search & Filter**: Search for products by keyword and filter by brand
- **Detailed View**: See detailed specifications for each product
- **Shopping Cart**: Add products to your cart and proceed to checkout
- **Order Management**: View your order history and order details
- **User Profile**: Manage your user information
- **Standard Images**: Consistent product imagery using standard photos for laptops and mice

## Tech Stack

- **Backend**: Spring Boot 3.2.0, Spring Data JPA, H2 Database
- **Frontend**: Thymeleaf templates, Bootstrap 5, JavaScript
- **Build Tool**: Maven

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone this repository
2. Navigate to the project directory
3. Build the application using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. Open a web browser and go to http://localhost:8084

### Running Tests

- Run all tests:
  ```
  mvn test
  ```
- Run a specific test:
  ```
  mvn test -Dtest=TestClassName#testMethodName
  ```

### Database

The application uses an in-memory H2 database that is initialized with sample data on startup. You can access the H2 console at http://localhost:8084/h2-console with the following credentials:

- JDBC URL: `jdbc:h2:mem:laptopdb`
- Username: `sa`
- Password: (leave empty)

### Project Organization

```
src/main/java/com/example/laptopstore/
├── config/           # Application configuration
│   ├── DataLoader.java       # Initializes sample data
│   ├── SwingUIRunner.java    # Optional Swing UI launcher
│   └── WebConfig.java        # Web configuration
├── controller/       # HTTP request handlers
│   ├── LaptopController.java  # REST API for laptops
│   ├── MouseController.java   # REST API for mice
│   ├── OrderController.java   # REST API for orders
│   ├── TestController.java    # For testing purposes
│   ├── UserController.java    # REST API for users
│   ├── WebController.java     # MVC controllers for web views
│   └── WebMouseController.java # MVC controllers for mouse views
├── exception/        # Exception handling
│   ├── ErrorResponse.java      # Error response model
│   └── GlobalExceptionHandler.java # Global exception handler
├── model/            # Domain models/entities
│   ├── Laptop.java     # Laptop entity
│   ├── Mouse.java      # Mouse entity
│   ├── Order.java      # Order entity
│   ├── OrderItem.java  # Order item entity
│   └── User.java       # User entity
├── repository/       # Data access layer
│   ├── LaptopRepository.java   # Laptop data access
│   ├── MouseRepository.java    # Mouse data access
│   ├── OrderItemRepository.java # Order item data access
│   ├── OrderRepository.java    # Order data access
│   └── UserRepository.java     # User data access
├── service/          # Business logic
│   ├── LaptopService.java   # Laptop business logic
│   ├── MouseService.java    # Mouse business logic
│   ├── OrderService.java    # Order business logic
│   └── UserService.java     # User business logic
├── ui/               # Optional Swing UI
│   ├── LaptopStoreUI.java   # Swing UI implementation
│   └── UILauncher.java      # UI launcher
└── LaptopStoreApplication.java # Application entry point
```

### Resources Structure

```
src/main/resources/
├── static/           # Static web resources
│   ├── css/             # CSS stylesheets
│   └── images/          # Image files for products (laptop.jpg, mouse.jpg)
├── templates/        # Thymeleaf HTML templates
│   ├── laptops/         # Laptop-related views
│   ├── mice/            # Mouse-related views
│   ├── orders/          # Order-related views
│   ├── cart.html        # Shopping cart view
│   ├── exercises.html   # Developer exercises page
│   ├── index.html       # Home page
│   ├── layout.html      # Shared layout template
│   └── profile.html     # User profile view
└── application.properties # Application configuration
```

### Application Flow

1. **Entry Point**: `LaptopStoreApplication.java` bootstraps the Spring Boot application
2. **Data Initialization**: `DataLoader.java` populates the H2 database with sample laptops and users
3. **Web Layer**:
   - **REST Controllers**: Handle API requests (LaptopController, OrderController, UserController)
   - **MVC Controller**: WebController serves Thymeleaf templates for web views
4. **Service Layer**: Implements business logic and connects controllers with repositories
5. **Repository Layer**: Interfaces with the H2 database using Spring Data JPA
6. **Frontend**: Thymeleaf templates rendered on the server-side with Bootstrap for styling

### Key Components Explained

- **Spring Boot**: Provides auto-configuration, embedded server, and dependency injection
- **Spring MVC**: Handles HTTP requests and renders views
- **Spring Data JPA**: Simplifies data access with repository interfaces
- **Thymeleaf**: Server-side template engine for rendering dynamic HTML
- **H2 Database**: In-memory database for development and testing
- **Bootstrap**: Frontend framework for responsive UI components
- **Lombok**: Reduces boilerplate code with annotations (e.g., @Data)

## API Endpoints

In addition to the web interface, this application exposes REST APIs that can be consumed by other clients:

### Laptop APIs
- GET `/api/laptops` - Get all laptops
- GET `/api/laptops/{id}` - Get laptop by ID
- GET `/api/laptops/search?keyword=&brand=` - Search laptops by keyword and/or brand

### Mouse APIs
- GET `/api/mouse` - Get all computer mice
- GET `/api/mouse/{id}` - Get mouse by ID
- GET `/api/mouse/search?keyword=` - Search mice by keyword

### Order APIs
- GET `/api/orders` - Get all orders for the user
- GET `/api/orders/{id}` - Get order by ID
- POST `/api/orders` - Create a new order

### User APIs
- GET `/api/users` - Get all users
- GET `/api/users/{id}` - Get user by ID
- POST `/api/users/register` - Register a new user
- PUT `/api/users/{id}` - Update user information
- DELETE `/api/users/{id}` - Delete a user
- POST `/api/users/login` - Authenticate a user

## For Developers

The application includes an "Exercises" page designed for learning and practice. This page contains a list of development tasks that you can complete using Cody.

Access the exercises page by clicking the "Exercises" link in the navigation menu.

