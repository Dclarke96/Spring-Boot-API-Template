# Spring Boot API Template

![Build Status](https://github.com/Dclarke96/SpringbootAPITemplate/actions/workflows/build.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-brightgreen)
![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-green)

## Overview

Spring Boot API Template is a production-oriented backend foundation designed to accelerate the development of secure, maintainable REST APIs.

The template provides a reusable Spring Boot architecture including authentication, authorization, DTO-based API boundaries, validation, centralized exception handling, standardized API responses, OpenAPI documentation, integration testing, continuous integration, and containerized deployment workflows.

The repository includes an example application domain demonstrating how business functionality can be built on top of the reusable API foundation.

The current example implementation uses a reusable API template structure to demonstrate resource management, relational data operations, authentication, and API design patterns.

---

# Who Is This Template For?

This template is intended for developers who want a production-oriented starting point for Spring Boot REST APIs.

Typical use cases include:

- New backend projects
- Portfolio applications
- Internal business tools
- Microservices
- Learning modern Spring Boot architecture
- Rapid API prototyping

The included example domain demonstrates the recommended architectural patterns and can be replaced with your own business domain.

---

# Documentation

| Document | Description |
|----------|-------------|
| Template Guide | Getting started with the template |
| Architecture Overview | Layered architecture and project organization |
| API Design | API conventions and design standards |
| Deployment Guide | Deployment and environment configuration |
| Design Decisions | Architectural rationale |
| Project Roadmap | Planned releases and milestones |

---

# Quick Start

## Run with Docker

```bash
docker compose up --build
````

The application will be available at:

* API: [http://localhost:8080](http://localhost:8080)
* Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* Health Endpoint: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

## Run Locally

```bash
./gradlew bootRun
```

Once the application starts, access:

* API: [http://localhost:8080](http://localhost:8080)
* Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* Health Endpoint: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

# Features

* JWT Authentication
* Role-based security foundation
* Example application domain implementation
* CRUD application patterns
* Relational data workflow examples
* Search functionality
* Pagination
* Jakarta Validation
* Standardized API responses
* Centralized exception handling
* Request logging with trace IDs
* Spring Boot Actuator health monitoring
* OpenAPI / Swagger documentation
* Integration testing
* GitHub Actions CI pipeline
* Docker containerization

---

# Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.4.2
* **Database:** PostgreSQL
* **Validation:** Jakarta Validation
* **Security:** Spring Security + JWT Authentication
* **API Documentation:** SpringDoc OpenAPI / Swagger UI
* **Build Tool:** Gradle
* **Containerization:** Docker + Docker Compose
* **Architecture:** Layered Architecture with separated API, business logic, persistence, security, and cross-cutting concerns
* **Testing:** JUnit 5 + Spring Boot Test
* **CI/CD:** GitHub Actions

---

# Architecture

The backend currently follows a layered architecture with clear separation between application responsibilities.

This structure provides a maintainable foundation while allowing future evolution toward additional architectural patterns.

```
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Database
```

Supporting layers include:

* **API Layer** - Provides standardized API responses and pagination models.
* **DTO Layer** - Separates external API contracts from internal persistence models.
* **Security Layer** - Handles JWT authentication, authorization, and current user context.
* **Exception Layer** - Provides centralized exception handling and consistent error responses.
* **Logging Layer** - Provides request logging and traceability.

Key architectural decisions:

* Controllers remain thin and delegate business logic to services.
* Services contain application rules and workflows.
* Repositories manage database access through JPA.
* DTOs protect API contracts from internal entity changes.
* Security concerns are isolated from business logic.

For a detailed overview, see:

* [Architecture Overview](docs/architecture.md)

---

# API Documentation

The example implementation exposes endpoints demonstrating:

* User authentication
* Resource management workflows
* Relational data operations

The current example domain demonstrates these patterns using template resources and example CRUD workflows.

Supported operations include:

* GET
* POST
* PUT
* DELETE
* Search
* Pagination

Detailed endpoint documentation with request and response examples:

* [API Design](docs/api-design.md)

---

# OpenAPI Documentation

Interactive API documentation is available through Swagger UI after starting the application.

## Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

## OpenAPI JSON

```
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

```
GET /actuator/health
```

When the application is running locally:

```
http://localhost:8080/actuator/health
```

The endpoint reports application and database health and can be used by deployment platforms, load balancers, or monitoring systems to verify service availability.

---

# Logging

The application uses structured request logging to improve troubleshooting and operational visibility.

Each incoming request is assigned a unique trace identifier that is included throughout the request lifecycle and in standardized error responses.

This allows application logs and client-facing errors to be correlated during debugging.

Application logs intentionally exclude sensitive information such as passwords and JWT tokens.

---

# Authentication

The API uses JWT-based authentication.

## Registration

Create a new account:

```
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

Protected resources require a valid authentication token.

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

# Design Decisions

Major architectural and implementation decisions are documented to explain the reasoning behind the template's design. These documents describe not only *what* was implemented, but *why* each decision was made.

* Why layered architecture was selected
* Why DTOs are used
* Authentication and authorization strategy
* Validation approach
* Exception handling design
* Repository design
* Testing strategy

See:

* [Design Decisions](docs/design-decisions.md)

---

# Project Roadmap

The template follows a structured release roadmap that incrementally evolves the project from a standalone repository into a production-ready Spring Boot API template.

Each release focuses on a specific milestone, including architecture, developer experience, testing, CI/CD, documentation, and repository quality.

See:

- [Project Roadmap](docs/project-roadmap.md)

---

# Getting Started

## Prerequisites

Before running the application, ensure you have:

* Java 21
* Git
* Docker Desktop (recommended)
* PostgreSQL (only if not using Docker)

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

Docker Compose provides a complete local development environment, allowing the application and PostgreSQL database to be started with a single command..

Docker Compose provides:

* Spring Boot API container
* PostgreSQL database container
* Internal container networking
* Persistent database storage through Docker volumes

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

2. Configure PostgreSQL and environment variables.

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
./gradlew test
```

The project includes integration tests covering:

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
