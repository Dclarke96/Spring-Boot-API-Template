# Spring Boot API Template

![Build Status](https://github.com/Dclarke96/SpringbootAPITemplate/actions/workflows/build.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.13-brightgreen)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-green)

## Overview

Spring Boot API Template is a production-oriented backend foundation designed to accelerate the development of secure, maintainable REST APIs.

The template provides a reusable Spring Boot architecture including authentication, authorization, DTO-based API boundaries, validation, centralized exception handling, standardized API responses, OpenAPI documentation, integration testing, continuous integration, and containerized application workflows.

The repository includes an example application domain demonstrating how business functionality can be built on top of the reusable API foundation. The example implementation demonstrates resource management, relational data operations, authentication, and common REST API design patterns.

---

# Why This Template?

This template is designed to provide a production-oriented foundation rather than a minimal Spring Boot starter. It demonstrates clear separation of concerns, security boundaries, centralized error handling, automated testing, containerized development, CI validation, and documented architectural decisions.

---

# Who Is This Template For?

This template is intended for developers who want a production-oriented starting point for Spring Boot REST APIs.

Typical use cases include:

* New backend projects
* Portfolio applications
* Internal business tools
* Microservices
* Learning modern Spring Boot architecture
* Rapid API prototyping

The included example domain demonstrates the recommended architectural patterns and can be replaced or removed when building a new application.

---

# Documentation

| Document                      | Description                                             |
| ----------------------------- | ------------------------------------------------------- |
| Template Guide                | Getting started and customizing the template            |
| Architecture Overview         | Layered architecture and project organization           |
| API Design                    | API conventions and design standards                    |
| Deployment Guide              | Deployment requirements and environment configuration   |
| Architecture Decision Records | Significant architectural decisions and their rationale |
| Project Roadmap               | Project evolution and release milestones                |

---

# Quick Start

## Run with Docker

```bash
docker compose up --build
```

The application will be available at:

* API: `http://localhost:8080`
* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* Health Endpoint: `http://localhost:8080/actuator/health`

## Run Locally

```bash
./gradlew bootRun
```

Once the application starts, access:

* API: `http://localhost:8080`
* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* Health Endpoint: `http://localhost:8080/actuator/health`

---

# Features

* JWT authentication
* Role-based security foundation
* Example application domain implementation
* CRUD application patterns
* Relational data workflow examples
* Search functionality
* Pagination
* Jakarta Bean Validation
* Standardized API responses
* Centralized exception handling
* Request logging with trace IDs
* Spring Boot Actuator health monitoring
* OpenAPI / Swagger documentation
* PostgreSQL Testcontainers integration testing
* Reusable integration testing framework
* GitHub Actions CI pipeline
* Docker containerization

---

# Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.4.13
* **Database:** PostgreSQL
* **Validation:** Jakarta Bean Validation
* **Security:** Spring Security + JWT authentication
* **API Documentation:** SpringDoc OpenAPI / Swagger UI
* **Build Tool:** Gradle
* **Containerization:** Docker + Docker Compose
* **Architecture:** Layered architecture with separated API, business logic, persistence, security, and cross-cutting concerns
* **Testing:** JUnit 5 + Spring Boot Test + Testcontainers
* **CI:** GitHub Actions

---

# Architecture

The backend currently follows a layered architecture with clear separation between application responsibilities.

This structure provides a maintainable foundation while allowing future evolution toward additional architectural patterns.

```text
Controller Layer
        ↓
DTO Boundary
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Database
```

Supporting concerns include:

* **API Layer** - Provides standardized API responses and pagination models.
* **DTO Layer** - Separates external API contracts from internal persistence models.
* **Security Layer** - Handles JWT authentication, authorization, and current user context.
* **Exception Layer** - Provides centralized exception handling and consistent error responses.
* **Logging Layer** - Provides request logging and traceability.
* **Configuration** - Provides environment-specific application configuration through Spring profiles and environment variables.

Key architectural decisions:

* Controllers remain thin and delegate business logic to services.
* Services contain application workflows and business rules.
* Repositories manage database access through JPA.
* DTOs protect API contracts from internal entity changes.
* Security concerns are isolated from business logic.
* Cross-cutting concerns such as logging and exception handling remain separate from business workflows.

For a detailed overview, see:

* [Architecture Overview](docs/architecture.md)

---

# API Documentation

The example implementation exposes endpoints demonstrating:

* User authentication
* Resource management workflows
* Relational data operations
* Request validation
* Pagination
* Search functionality
* Standardized API responses

The example domain demonstrates these patterns using representative resource and CRUD workflows.

Supported API capabilities include:

- CRUD operations using GET, POST, PUT, and DELETE
- Search
- Pagination

Detailed endpoint documentation with request and response examples:

* [API Design](docs/api-design.md)

---

# OpenAPI Documentation

Interactive API documentation is available through Swagger UI after starting the application.

## Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

## OpenAPI JSON

```text
http://localhost:8080/v3/api-docs
```

Swagger UI allows developers to:

* Browse all available endpoints
* View request and response schemas
* Authenticate using JWT bearer tokens
* Execute API requests directly from the browser

---

# Health Monitoring

Spring Boot Actuator is included to provide application health monitoring for local development and production deployments.

Health endpoint:

```text
GET /actuator/health
```

When the application is running locally:

```text
http://localhost:8080/actuator/health
```

The endpoint provides application health information and can be used by deployment platforms, load balancers, or monitoring systems to verify service availability.

---

# Logging

The application uses structured request logging to improve troubleshooting and operational visibility.

Each incoming request is assigned a unique trace identifier that is included throughout the request lifecycle and in standardized error responses.

This allows application logs and client-facing errors to be correlated during debugging and troubleshooting.

Application logs intentionally exclude sensitive information such as passwords and JWT tokens.

---

# Authentication

The API uses JWT-based authentication.

## Registration

Create a new account:

```text
POST /api/auth/register
```

## Login

Authenticate and receive a JWT token:

```
POST /api/auth/login
```

Example authentication header for protected endpoints:

```
Authorization: Bearer <JWT_TOKEN>
```

Protected resources require a valid JWT authentication token, with authorization decisions based on the authenticated user's role where applicable.

## Using Swagger UI

1. Register a new user or log in using:

```
POST /api/auth/register
```

or

```
POST /api/auth/login
```

2. Copy the JWT token returned from the login response.

3. Open Swagger UI and click the **Authorize** button.

4. Enter:

```
Bearer <JWT_TOKEN>
```

5. Execute protected endpoints directly from the documentation.

---

# Architecture Decision Records

Significant architectural decisions are documented using focused Architecture Decision Records (ADRs). Each record captures the context, rationale, alternatives considered, and consequences of an architectural decision.

Current ADRs include:

* Layered architecture
* DTO-based API boundaries
* JWT security architecture
* Testcontainers-based integration testing
* Domain removal and template decoupling history

See:

* [Architecture Decision Records](docs/adr/ADR-001-layered-architecture.md)

---

# Project Roadmap

The template follows a structured release roadmap that incrementally evolves the project into a professional, reusable Spring Boot API template.

Each release focuses on a specific milestone, including architecture, developer experience, testing, CI/CD, documentation, and repository quality.

See:

- [Project Roadmap](docs/project-roadmap.md)

---

# Getting Started

## Prerequisites

Before running the application, ensure you have:

* Java 21
* Git
* Docker Desktop (required for integration tests and recommended for local development)
* PostgreSQL (only required for running locally without Docker)

---

# Environment Configuration

The application uses Spring profiles:

* `local` - Local development environment
* `test` - Automated testing environment
* `prod` - Production environment
* `docker` - Docker container environment

Sensitive configuration values should be provided through environment variables.

Required environment variables vary by active Spring profile.

Copy `.env.example` to `.env` (or configure the equivalent environment variables) before starting the application.

The template includes an `.env.example` file that documents the expected environment variables for local development.

## Local Profile

```
DB_PASSWORD=<database-password>
JWT_SECRET=<jwt-secret-key>
```

## Production / Docker Profiles

```
DB_URL=<jdbc-url>
DB_USERNAME=<database-username>
DB_PASSWORD=<database-password>
JWT_SECRET=<jwt-secret-key>
```

---

# Docker Environment

Docker Compose provides a complete local development environment, allowing the application and PostgreSQL database to be started with a single command.

Docker Compose provides:

* Spring Boot API container
* PostgreSQL database container
* Internal container networking

Start the application:

```bash
docker compose up --build
```

The API will be available at:

```
http://localhost:8080
```

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

Stop the containers:

```bash
docker compose down
```

---

# Running the Project Locally

1. Clone the repository:

```bash
git clone https://github.com/Dclarke96/SpringbootAPITemplate.git
cd SpringbootAPITemplate
```

2. Configure PostgreSQL and the required environment variables if running the application locally. PostgreSQL is not required for the integration test suite because Testcontainers creates an isolated PostgreSQL database automatically.

3. Build and run:

```bash
./gradlew bootRun
```

4. The API will start at:

```
http://localhost:8080
```

5. Open Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

6. View OpenAPI JSON:

```
http://localhost:8080/v3/api-docs
```

---

# Running Tests

Execute the automated test suite:

```bash
./gradlew clean build
```

The project includes unit and integration tests covering:

* Service-layer business logic
* Security and authentication behavior
* Controller behavior
* Authentication workflows
* Authorization rules
* Data integrity scenarios
* Exception handling
* Example domain workflows

Continuous integration is provided through GitHub Actions.

Every push and pull request to the `main` and `dev` branches automatically executes the Gradle build and test suite to verify application stability.

---

# Deployment

Deployment guidance, required environment variables, and production configuration are documented in:

* [Deployment Guide](docs/deployment-guide.md)
