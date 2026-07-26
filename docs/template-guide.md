# Spring Boot API Template Guide

## Purpose

The Spring Boot API Template is a reusable foundation for building secure, maintainable REST APIs with Spring Boot. It provides a production-oriented starting point that includes common architectural patterns, authentication, testing, documentation, and deployment support.

Rather than starting every project from scratch, this template allows you to focus on implementing business logic while relying on a proven application structure.

---

# What's Included

The template includes the following foundations out of the box:

## Core Framework

* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Gradle

## Security

* JWT Authentication
* Role-based authorization foundation
* Password encryption using BCrypt
* Authentication and registration workflows

## Architecture

* Layered architecture
* DTO-based API contracts
* Service layer
* Repository pattern
* Validation
* Centralized exception handling
* Standardized API responses

## Documentation

* OpenAPI / Swagger UI
* Architecture documentation
* Design decisions
* Project roadmap

## Testing

* Integration testing foundation
* Authentication workflow tests
* Exception handling tests

## Deployment

* Docker support
* Docker Compose
* GitHub Actions CI
* Environment-specific Spring profiles

---

# Project Structure

The project follows a standard layered architecture.

```text
src
├── main
│   ├── java
│   │   └── com.example.springbootapitemplate
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       ├── model
│   │       ├── repository
│   │       ├── security
│   │       ├── service
│   │       └── util
│   └── resources
│       ├── application.properties
│       ├── application-local.properties
│       ├── application-test.properties
│       ├── application-prod.properties
│       └── application-docker.properties
└── test
```

> **Note:** Your package structure may vary depending on how you customize the template.

---

# Getting Started

## Prerequisites

Before running the project, ensure you have:

* Java 21
* Gradle (or use the included Gradle Wrapper)
* Docker Desktop (recommended)
* PostgreSQL (if not using Docker)
* Git

---

## Clone the Repository

```bash
git clone <repository-url>
cd springboot-api-template
```

---

## Configure Environment Variables

Copy the example environment file.

```bash
cp .env.example .env
```

Update the values to match your local environment.

At a minimum, review:

* PostgreSQL configuration
* JWT secret
* Active Spring profile

---

## Start Supporting Services

If using Docker:

```bash
docker compose up -d
```

---

## Run the Application

Using the Gradle Wrapper:

```bash
./gradlew bootRun
```

Or from your IDE by running:

```
SpringBootApiTemplateApplication
```

---

## Run the Tests

Execute the full test suite with:

```bash
./gradlew test
```

---

# Spring Profiles

The template includes multiple configuration profiles.

| Profile | Purpose               |
| ------- | --------------------- |
| local   | Local development     |
| test    | Automated testing     |
| docker  | Docker environment    |
| prod    | Production deployment |

Activate the desired profile using environment variables or your IDE configuration.

---

# Customizing the Template

This repository is intended to be used as a starting point for new projects.

Typical customization steps include:

## 1. Rename the Package

Rename the base package to match your organization or project.

Example:

```
com.example.inventoryapi
```

---

## 2. Update Application Information

Modify:

* Application name
* Project description
* OpenAPI information
* Docker image names
* Environment variables

---

## 3. Replace the Example Domain

The included example domain demonstrates the recommended application architecture.

When starting a new project:

* Replace entities with your own domain models.
* Create new repositories.
* Create new services.
* Create new controllers.
* Keep the architectural patterns unchanged.

---

## 4. Configure the Database

Update:

* Database name
* Username
* Password
* Connection URL

---

## 5. Generate a Secure JWT Secret

Never use the default JWT secret in production.

Generate a strong random secret and store it securely using environment variables or a secrets management solution.

---

# API Documentation

Once the application is running, OpenAPI documentation is available through Swagger UI.

Refer to the project README for the current endpoint URL.

---

# Testing Strategy

The project currently includes:

* Integration tests
* Authentication workflow testing
* Exception handling verification

Future versions of the template will also include:

* Testcontainers
* Service-layer unit tests
* Controller tests
* Code coverage reporting

---

# Docker Support

Docker Compose can be used to start the application and supporting services for local development.

The Docker configuration mirrors the expected production environment as closely as practical while remaining easy to use during development.

---

# Deployment

The repository includes:

* Docker configuration
* GitHub Actions CI pipeline
* Environment-specific configuration
* Production-ready project structure

Additional deployment examples may be added in future releases.

---

# Recommended Development Workflow

1. Clone the repository.
2. Configure environment variables.
3. Start supporting services.
4. Run the application.
5. Verify the API using Swagger.
6. Implement your domain model.
7. Add tests for new functionality.
8. Commit changes frequently.
9. Push to GitHub to validate the CI pipeline.

---

# Versioning

The template follows semantic versioning.

* Major versions introduce significant architectural or compatibility changes.
* Minor versions add features and improvements while maintaining compatibility.
* Patch versions provide bug fixes and small refinements.

Refer to the project roadmap and CHANGELOG for release history and future plans.
