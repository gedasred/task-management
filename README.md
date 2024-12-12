# Task Management

## Project Purpose

The Task Management project is designed to manage tasks. It provides RESTful APIs to create, retrieve, and manage tasks and users. The project is built using Spring Boot and follows best practices for security and data management.

## Technologies Used

- **Java 17**: The programming language used for the project.
- **Spring Boot 3.4.0**: Framework for building the application.
    - **Spring Boot Starter Data JPA**: For data persistence using JPA.
    - **Spring Boot Starter Security**: For securing the application.
    - **Spring Boot Starter Web**: For building web applications and RESTful services.
- **PostgreSQL**: The database used for storing data.
- **Liquibase**: For database migrations.
- **MapStruct**: For mapping between DTOs and entities.
- **Lombok**: For reducing boilerplate code.
- **JUnit**: For unit testing.
- **Spring Security Test**: For testing security configurations.
- **Gradle**: Build automation tool.

## CI/CD

- **GitHub Actions**: Used for continuous integration.
    - **ci.yml**: Workflow configuration for building and testing the project.
    - **aws.yml**: Workflow configuration for deploying the project to AWS.

## How to Run

1. Ensure you have Docker installed.
2. Clone the repository.
3. Navigate to the `docker` folder where the `docker-compose.yml` file is located:
   ```sh
   cd docker
   ```
4. Start the services using Docker Compose:
   ```sh
   docker-compose up -d
   ```
5. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```
6. Run the application:
   ```sh
   ./gradlew bootRun
   ```