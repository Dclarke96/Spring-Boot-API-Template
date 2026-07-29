# Spring Boot API Template Guide

## Purpose

The Spring Boot API Template is a reusable foundation for building secure, maintainable REST APIs with Spring Boot.

The template provides a production-oriented starting point including:

- Application architecture
- Authentication and authorization
- DTO-based API boundaries
- Validation
- Exception handling
- Integration testing
- API documentation
- Containerized development workflows
- CI pipeline support

Rather than rebuilding common backend infrastructure for every project, developers can start with an established foundation and focus on implementing application-specific business functionality.

The repository includes an example domain implementation that demonstrates how business features can be built using the provided architecture patterns.

---

# What's Included

The template includes the following foundations out of the box.

---

## Core Framework

- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL support
- Gradle build system

---

## Security

- JWT authentication
- Spring Security integration
- Role-based authorization foundation
- BCrypt password encryption
- User registration workflow
- Login workflow
- Protected endpoint authorization

---

## Architecture

- Layered architecture
- DTO-based API contracts
- Service layer
- Repository pattern
- Jakarta Validation
- Centralized exception handling
- Standardized API responses
- Request trace ID support
- Logging foundation

---

## Documentation

The repository includes:

- OpenAPI / Swagger UI documentation
- Architecture overview
- API design documentation
- Design decisions
- Project roadmap
- Deployment guidance

---

## Testing

The template includes a reusable testing foundation:

- JUnit 5
- Spring Boot Test
- MockMvc integration testing
- PostgreSQL Testcontainers
- Shared integration test utilities
- Authentication workflow tests
- Exception handling tests
- Example resource lifecycle tests

The testing structure is designed so new application domains can follow consistent integration testing patterns.

---

## Deployment

The template includes:

- Docker support
- Docker Compose configuration
- GitHub Actions CI pipeline
- Environment-specific Spring profiles
- Health monitoring through Spring Boot Actuator

---

# Project Structure

The project follows a layered architecture with clear separation between application responsibilities.

```text
src
├── main
│   ├── java
│   │   └── com.dylanclarke.springbootapitemplate
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       ├── service
│   │       └── util
│   │
│   └── resources
│       ├── application.properties
│       ├── application-local.properties
│       ├── application-test.properties
│       ├── application-prod.properties
│       └── application-docker.properties
│
└── test
    └── integration
```

> The package structure can be customized when using the template for a new application.

---

# Getting Started

## Prerequisites

Before running the project, ensure you have:

- Java 21
- Git
- Gradle (or use the included Gradle Wrapper)
- Docker Desktop (recommended)
- PostgreSQL (only if not using Docker)

---

# Clone the Repository

```bash
git clone <repository-url>

cd springboot-api-template
```

---

# Configure Environment Variables

Copy the example environment file:

```bash
cp .env.example .env
```

Update the values based on your environment.

Review:

- Database configuration
- JWT secret
- Active Spring profile

Sensitive values should not be committed to source control.

---

# Start Supporting Services

For Docker-based development:

```bash
docker compose up -d
```

This starts the required application dependencies.

---

# Run the Application

Using the Gradle Wrapper:

```bash
./gradlew bootRun
```

Or run:

```
SpringBootApiTemplateApplication
```

from your IDE.

---

# Run Tests

Execute the automated test suite:

```bash
./gradlew test
```

The integration tests use PostgreSQL Testcontainers to provide an isolated database environment.

Docker Desktop must be running when executing integration tests.

---

# Spring Profiles

The template includes multiple configuration profiles.

| Profile | Purpose |
| ------- | ------- |
| local | Local development |
| test | Automated testing |
| docker | Docker-based development |
| prod | Production deployment |

Activate profiles using:

- Environment variables
- IDE configuration
- Deployment platform settings

---

# Customizing the Template

The repository is designed to be cloned and adapted for new applications.

The recommended customization process is:

---

## 1. Rename the Package

Update the base package to match the new application.

Example:

```
com.example.inventoryapi
```

Update:

- Java package declarations
- Application class package
- Component scanning configuration
- Test packages

---

## 2. Update Application Identity

Modify:

- Application name
- Project description
- OpenAPI metadata
- Docker image names
- Environment variable names
- Repository information

---

## 3. Replace the Example Domain

The included example domain demonstrates recommended implementation patterns.

When creating a new application:

- Replace example entities with application-specific models.
- Create new repositories.
- Implement business services.
- Add new controllers.
- Create DTOs for external API contracts.
- Add integration tests.

The reusable infrastructure should remain separate from business-specific functionality.

---

## 4. Configure the Database

Update:

- Database name
- Database username
- Database password
- JDBC connection URL

Database credentials should be provided through environment variables.

---

## 5. Configure Authentication

The template includes JWT authentication as a reusable security foundation.

Before production usage:

- Replace development secrets.
- Configure secure JWT keys.
- Review authorization requirements.
- Add application-specific roles if needed.

---

# API Documentation

After starting the application, interactive API documentation is available through Swagger UI.

Swagger UI allows developers to:

- View available endpoints.
- Review request and response models.
- Authenticate using JWT.
- Execute API requests directly.

Refer to the README for the current Swagger endpoint.

---

# Testing Strategy

The testing approach validates application behavior across the full request lifecycle.

Current testing includes:

- Authentication workflows.
- Authorization behavior.
- Validation failures.
- Exception handling.
- Resource creation.
- Resource retrieval.
- Resource updates.
- Resource deletion.
- Database interactions.

The shared integration testing framework allows additional domains to follow the same testing structure.

---

# Docker Support

Docker Compose provides a consistent local development environment.

The Docker workflow supports:

- Running the API application.
- Running PostgreSQL.
- Internal service communication.
- Persistent database storage.

Start the environment:

```bash
docker compose up --build
```

Stop the environment:

```bash
docker compose down
```

---

# Deployment

The repository provides deployment foundations including:

- Docker configuration.
- GitHub Actions CI.
- Externalized configuration.
- Environment-specific Spring profiles.
- Health monitoring endpoints.

Production deployment guidance is documented separately in:

```
docs/deployment-guide.md
```

---

# Recommended Development Workflow

A recommended workflow when starting a new project:

1. Clone the template repository.
2. Configure environment variables.
3. Start required services.
4. Verify the application starts successfully.
5. Review Swagger documentation.
6. Replace the example domain.
7. Implement application-specific functionality.
8. Add integration tests.
9. Commit changes frequently.
10. Push changes and verify CI execution.

---

# Versioning

The template follows semantic versioning.

- Major versions introduce significant architectural or compatibility changes.
- Minor versions introduce features and improvements.
- Patch versions provide bug fixes and refinements.

Refer to:

- `docs/project-roadmap.md`
- `CHANGELOG.md`

for release history and future development plans.