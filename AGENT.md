# Agent Guidelines for Laptop Store Java Application

## Build & Test Commands
- Build: `mvn clean install`
- Run application: `mvn spring-boot:run`
- Run specific test: `mvn test -Dtest=TestClassName#testMethodName`
- Run all tests: `mvn test`
- Check compilation: `mvn compile`

## Code Style Guidelines
- **Imports**: Organize imports with standard Java conventions (java.*, javax.*, org.*, com.*)
- **Formatting**: Use 4-space indentation, no tabs
- **Naming**: 
  - Classes: PascalCase (e.g., UserController)
  - Methods/Variables: camelCase (e.g., getAllUsers)
  - Constants: UPPER_SNAKE_CASE
- **Architecture**: Follow Spring MVC pattern (controllers, services, repositories, models)
- **Error Handling**: Use ResponseEntity with appropriate HTTP status codes
- **Annotations**: Use Spring annotations (@RestController, @Service, etc.) consistently
- **Validation**: Apply @Valid for request bodies with constraint annotations
- **Documentation**: Add JavaDoc comments for public methods

## Technology Stack
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Maven build system