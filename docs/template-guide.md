# Spring Boot API Template Guide

## Purpose

The Spring Boot API Template is a reusable foundation for building secure, maintainable REST APIs with Spring Boot.

The template provides a production-oriented starting point including:

* Application architecture
* Authentication and authorization
* DTO-based API boundaries
* Request validation
* Centralized exception handling
* Standardized API responses
* Unit and integration testing
* API documentation
* Containerized development workflows
* CI pipeline support
* Health monitoring

Rather than rebuilding common backend infrastructure for every project, developers can start with an established foundation and focus on implementing application-specific business functionality.

The repository includes a Fleet Management example domain that demonstrates how business features can be built using the provided architecture and engineering patterns. The example domain is intended as a reference implementation and can be replaced or removed when creating a new application.

---

# What's Included

The template includes the following foundations out of the box.

---

## Core Framework

* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL support
* Gradle build system
* Java 21

---

## Security

* JWT authentication
* Spring Security integration
* Role-based authorization foundation
* BCrypt password encryption
* User registration workflow
* Login workflow
* Protected endpoint authorization
* JWT request filtering

---

## Architecture

* Layered architecture
* DTO-based API contracts
* Service layer
* Repository pattern
* Jakarta Bean Validation
* Centralized exception handling
* Standardized API responses
* Request trace ID support
* Logging foundation

---

## Documentation

The repository includes:

* OpenAPI / Swagger UI documentation
* Architecture documentation
* API design documentation
* Architecture Decision Records (ADRs)
* Project roadmap
* Deployment guidance
* Contribution guidelines
* Changelog

---

## Testing

The template includes a reusable testing foundation:

* JUnit 5
* Spring Boot Test
* Mockito
* MockMvc
* PostgreSQL Testcontainers
* Shared integration test infrastructure
* Service-layer unit tests
* Security and authentication tests
* Controller tests
* Exception handling tests
* Example resource lifecycle tests
* JaCoCo code coverage reporting

The testing structure is designed so new application domains can follow consistent unit and integration testing patterns.

---

## Deployment

The template includes:

* Docker support
* Docker Compose configuration
* GitHub Actions CI pipeline
* Environment-specific Spring profiles
* Spring Boot Actuator health monitoring
* Externalized environment configuration

The template does not require a specific cloud provider or hosting platform.

---

# Project Structure

The project follows a layered architecture with clear separation between application responsibilities.

```text
src
├── main
│   ├── java
│   │   └── com.dylanclarke.springbootapitemplate
│   │       ├── api
│   │       ├── config
│   │       ├── controller
│   │       ├── documentation
│   │       ├── dto
│   │       ├── exception
│   │       ├── logging
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       └── service
│   │
│   └── resources
│       ├── application.properties
│       ├── application-local.properties
│       ├── application-test.properties
│       ├── application-prod.properties
│       └── application-docker.properties
│
└── test
    └── java
```

> The package structure can be customized when using the template for a new application.

---

# Getting Started

## Prerequisites

Before running the project, ensure you have:

* Java 21
* Git
* Docker Desktop
* Gradle, or use the included Gradle Wrapper

A local PostgreSQL installation is optional when using the Docker-based development environment.

Docker Desktop is required when running integration tests because the tests use PostgreSQL Testcontainers.

---

# Clone the Repository

```bash
git clone <repository-url>

cd springboot-api-template
```

Replace the repository URL and directory name with the values for your cloned repository.

---

# Configure Environment Variables

Copy the example environment file:

```bash
cp .env.example .env
```

On Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

Review the values for:

* Database configuration
* JWT secret
* Active Spring profile
* Other environment-specific settings

Sensitive values should never be committed to source control.

---

# Start Supporting Services

For Docker-based development:

```bash
docker compose up -d
```

This starts the application and supporting services defined by the Docker Compose configuration.

Use:

```bash
docker compose ps
```

to verify the containers are running.

To stop the services:

```bash
docker compose down
```

---

# Run the Application

Using the Gradle Wrapper:

### Linux/macOS

```bash
./gradlew bootRun
```

### Windows

```powershell
.\gradlew.bat bootRun
```

The application can also be started through the IDE by running:

```text
SpringBootApiTemplateApplication
```

---

# Run Tests

Execute the automated test suite:

### Linux/macOS

```bash
./gradlew test
```

### Windows

```powershell
.\gradlew.bat test
```

The integration tests use PostgreSQL Testcontainers to provide an isolated database environment.

Docker Desktop must be running when executing integration tests.

To perform a full build:

```bash
./gradlew clean build
```

or on Windows:

```powershell
.\gradlew.bat clean build
```

---

# Spring Profiles

The template includes multiple configuration profiles.

| Profile  | Purpose                  |
| -------- | ------------------------ |
| `local`  | Local development        |
| `test`   | Automated testing        |
| `docker` | Docker-based development |
| `prod`   | Production deployment    |

Profiles can be activated through:

* Environment variables
* IDE configuration
* Deployment platform settings

Production deployments should use the `prod` profile.

---

# Customizing the Template

The repository is designed to be cloned and adapted for new applications.

The recommended customization process is:

---

## 1. Rename the Package

Update the base package to match the new application.

Example:

```text
com.example.inventoryapi
```

Update:

* Java package declarations
* Application class package
* Component scanning configuration, where applicable
* Test packages

---

## 2. Update Application Identity

Modify:

* Application name
* Project description
* OpenAPI metadata
* Docker image names
* Environment variable names
* Repository information
* README content
* Project documentation

Remove references to the Fleet Management example where they are no longer relevant.

---

## 3. Replace the Example Domain

The included Fleet Management domain demonstrates recommended implementation patterns.

When creating a new application:

* Replace example entities with application-specific models.
* Create application-specific repositories.
* Implement business services.
* Add application-specific controllers.
* Create DTOs for external API contracts.
* Define validation rules.
* Add unit tests.
* Add integration tests.

The reusable infrastructure should remain separate from business-specific functionality.

---

## 4. Configure the Database

Update:

* Database name
* Database username
* Database password
* JDBC connection URL
* Environment-specific database configuration

Database credentials should be provided through environment variables or the deployment platform's secret-management facilities.

---

## 5. Configure Authentication

The template includes JWT authentication as a reusable security foundation.

Before production usage:

* Replace development secrets.
* Configure a secure JWT signing secret.
* Review token expiration settings.
* Review authorization requirements.
* Add application-specific roles and permissions where required.
* Verify protected endpoint behavior.

---

## 6. Review the Architecture

Before adding significant business functionality, review the project's architecture and established engineering conventions.

Recommended documentation:

```text
docs/architecture.md
docs/api-design.md
docs/deployment-guide.md
docs/adr/
```

The current ADRs document:

* Layered architecture
* DTO-based API boundaries
* JWT security architecture
* Testcontainers-based integration testing

Architecture Decision Records document significant architectural decisions and their rationale.

---

# API Documentation

After starting the application, interactive API documentation is available through Swagger UI.

Swagger UI allows developers to:

* View available endpoints.
* Review request and response models.
* Review API schemas.
* Authenticate using JWT.
* Execute API requests directly.

Refer to the README for the current Swagger UI endpoint.

---

# Testing Strategy

The testing approach validates application behavior at multiple levels.

## Unit Tests

Unit tests validate individual components in isolation, including:

* Service-layer business logic
* Authentication behavior
* Security filtering
* Controller behavior where appropriate
* Error and edge-case scenarios

## Integration Tests

Integration tests validate application behavior across multiple layers using Spring Boot Test, MockMvc, and PostgreSQL Testcontainers.

Current integration coverage includes:

* Authentication workflows
* Authorization behavior
* Validation failures
* Exception handling
* Resource creation
* Resource retrieval
* Resource updates
* Resource deletion
* Database interactions

The shared integration testing framework provides reusable infrastructure and helpers for extending coverage to additional application domains.

---

# Docker Support

Docker Compose provides a consistent local development environment.

The Docker workflow supports:

* Running the API application
* Running PostgreSQL
* Internal service communication
* Persistent database storage

Start the environment:

```bash
docker compose up --build
```

Stop the environment:

```bash
docker compose down
```

Docker is also used by Testcontainers when running integration tests.

---

# Deployment

The repository provides deployment foundations including:

* Docker configuration
* GitHub Actions CI
* Externalized configuration
* Environment-specific Spring profiles
* Spring Boot Actuator health monitoring

Production deployment guidance is documented separately in:

```text
docs/deployment-guide.md
```

The template intentionally remains platform-independent so applications can be deployed to the hosting environment appropriate to their requirements.

---

# Recommended Development Workflow

A recommended workflow when starting a new project:

1. Clone the template repository.
2. Rename the base package.
3. Update application identity and project metadata.
4. Configure environment variables.
5. Start required services.
6. Verify the application starts successfully.
7. Review the architecture and API documentation.
8. Review the existing ADRs.
9. Replace the example domain.
10. Implement application-specific functionality.
11. Add unit tests.
12. Add integration tests.
13. Run the full build.
14. Commit changes frequently.
15. Push changes and verify CI execution.
16. Review deployment configuration before release.

---

# Versioning

The template follows semantic versioning.

* Major versions introduce significant architectural or compatibility changes.
* Minor versions introduce features and improvements.
* Patch versions provide bug fixes and refinements.

Refer to:

```text
docs/project-roadmap.md
CHANGELOG.md
```

for release history and future development plans.

---

# Further Documentation

Additional information is available in:

| Document                   | Purpose                                            |
| -------------------------- | -------------------------------------------------- |
| `README.md`                | Project overview and quick start                   |
| `docs/architecture.md`     | System architecture and component responsibilities |
| `docs/api-design.md`       | API conventions and design patterns                |
| `docs/deployment-guide.md` | Deployment requirements and practices              |
| `docs/adr/`                | Significant architecture and engineering decisions |
| `CONTRIBUTING.md`          | Contribution workflow and project standards        |
| `CHANGELOG.md`             | Release history                                    |
| `docs/project-roadmap.md`  | Project evolution and future milestones            |
