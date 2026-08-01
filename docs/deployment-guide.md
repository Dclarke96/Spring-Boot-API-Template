# Deployment Guide

## Overview

This guide describes the deployment requirements and recommended practices for applications built from the Spring Boot API Template foundation.

The application is designed around externalized configuration, environment-specific Spring profiles, containerized deployment, and health monitoring to support modern deployment environments.

The deployment approach keeps infrastructure configuration separate from application code while providing a consistent foundation for local, staging, and production environments.

This guide focuses on deployment principles and the configuration required by the template rather than prescribing a specific cloud provider or hosting platform.

---

# Production Requirements

Before deploying the application, ensure the following requirements are available.

## Java

* Java 21 or later

The project is configured and tested against Java 21.

## Database

* PostgreSQL database instance
* Database user with appropriate permissions
* Network access from the application environment
* Secure database credentials supplied through environment configuration

## Build Tool

* Gradle
* Gradle Wrapper (recommended)

The Gradle Wrapper should be used for consistent builds across development and deployment environments.

## Container Runtime

Containerization is supported through Docker.

Docker is recommended for environments where the application is deployed as a container.

Docker Compose may be used for local development or environments requiring multiple coordinated services.

---

# Spring Profiles

The application supports environment-specific Spring profiles.

| Profile  | Purpose                        |
| -------- | ------------------------------ |
| `local`  | Local development              |
| `test`   | Automated testing              |
| `docker` | Docker-based local development |
| `prod`   | Production deployment          |

Production deployments should activate the `prod` profile:

```text
SPRING_PROFILES_ACTIVE=prod
```

Environment-specific configuration should remain outside the source code whenever possible.

---

# Environment Configuration

Sensitive and environment-specific values should be supplied through environment variables or the deployment platform's secret-management facilities.

Examples include:

* Database URL
* Database username
* Database password
* JWT secret
* JWT expiration settings
* Application-specific configuration

The repository includes an `.env.example` file to document the expected environment configuration without exposing real credentials.

Never commit production secrets, passwords, tokens, or private keys to source control.

---

# Database Configuration

Production deployments require a PostgreSQL database.

The application should be configured with the appropriate database connection properties for the target environment.

A typical production configuration uses environment-provided values rather than hard-coded credentials.

Example:

```text
DB_URL=<production-database-url>
DB_USERNAME=<production-database-user>
DB_PASSWORD=<production-database-password>
```

The database must be reachable from the application environment and the configured database user must have the permissions required by the application.

Production database credentials should be stored using the hosting platform's secret or environment-variable management system.

---

# JWT Configuration

The application uses JWT authentication for protected API resources.

Production deployments must provide a secure JWT signing secret through environment configuration.

The JWT secret should:

* Be sufficiently long and unpredictable.
* Be unique to the deployment environment.
* Never be committed to source control.
* Never be reused for development or testing environments.

JWT expiration settings should also be configured according to the security requirements of the application.

---

# Building the Application

The application can be built using the Gradle Wrapper.

On Windows:

```text
.\gradlew.bat clean build
```

On Linux or macOS:

```text
./gradlew clean build
```

A successful build verifies compilation and executes the configured automated test suite.

The resulting application JAR is generated under:

```text
build/libs/
```

The exact JAR filename may vary depending on the project version.

---

# Running the Application

The application can be started using the Gradle Wrapper:

```text
./gradlew bootRun
```

Or by running the packaged JAR:

```text
java -jar build/libs/<application>.jar
```

The active Spring profile should be supplied according to the target environment.

Example:

```text
SPRING_PROFILES_ACTIVE=prod
```

---

# Docker Deployment

The application can be packaged and deployed as a Docker container.

A typical workflow is:

1. Build the application.
2. Build the Docker image.
3. Configure production environment variables.
4. Start the container.
5. Verify application health.
6. Verify database connectivity.
7. Monitor application logs.

Example Docker image build:

```text
docker build -t spring-boot-api-template .
```

Example container startup:

```text
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL=<database-url> \
  -e DB_USERNAME=<database-user> \
  -e DB_PASSWORD=<database-password> \
  -e JWT_SECRET=<jwt-secret> \
  spring-boot-api-template
```

The exact environment variables should match those defined by the application's configuration.

---

# Docker Compose

Docker Compose can be used for local development environments where the application and supporting services need to run together.

A typical development environment may include:

* Spring Boot application
* PostgreSQL database

Docker Compose should primarily be treated as a development and local infrastructure convenience unless the deployment environment specifically supports Compose-based deployments.

Production deployments should use the deployment model appropriate for the selected hosting platform.

---

# Health Monitoring

The application includes Spring Boot Actuator for application monitoring and health information.

Health endpoints can be used by deployment platforms and monitoring systems to determine whether the application is available.

The health endpoint is:

```text
/actuator/health
```

Example:

```text
GET /actuator/health
```

A successful health response indicates that the application is running and able to report its current health status.

Production environments should use the health endpoint when configuring container health checks, load balancers, or platform-specific health monitoring.

---

# Logging and Traceability

Application logs should be collected and monitored by the deployment environment.

The application includes trace identifiers in standardized API error responses. These identifiers can be used to correlate client-visible errors with application logs.

When investigating production issues:

1. Capture the API response and `traceId`.
2. Locate the corresponding application log entry.
3. Identify the underlying exception or failure.
4. Investigate the affected application or infrastructure component.

Production logs should not expose sensitive credentials, authentication tokens, or other confidential information.

---

# Database Migrations

The application currently relies on the configured JPA/Hibernate database schema management strategy.

For production systems, database schema changes should be managed deliberately and should not rely on destructive automatic schema recreation.

As the template evolves toward production use, a dedicated database migration tool such as Flyway or Liquibase may be introduced.

Until then, production deployments should review schema changes carefully before applying a new application version.

---

# CI/CD

The repository includes GitHub Actions automation for building and testing the application.

The CI pipeline provides an automated verification step before changes are merged or released.

The deployment process should follow the general sequence:

```text
Commit
   ↓
Pull Request
   ↓
Automated Build & Tests
   ↓
Review
   ↓
Merge
   ↓
Build Deployment Artifact
   ↓
Deploy
   ↓
Health Verification
```

The repository's CI workflow is intended to provide a foundation that can be extended with deployment-specific steps for a chosen hosting platform.

---

# Production Deployment Checklist

Before deploying a new application version, verify:

* [ ] Java 21+ is available.
* [ ] PostgreSQL is available and reachable.
* [ ] Production database credentials are configured securely.
* [ ] JWT signing secret is configured securely.
* [ ] `prod` Spring profile is active.
* [ ] Required environment variables are configured.
* [ ] Application builds successfully.
* [ ] Automated tests pass.
* [ ] Docker image builds successfully if containerized deployment is used.
* [ ] Application health endpoint is available.
* [ ] Application logs are accessible.
* [ ] Database schema changes have been reviewed.
* [ ] Production secrets are not committed to source control.

---

# Deployment Principles

The template follows several deployment principles:

* **Externalized configuration** — Environment-specific values should not be hard-coded into the application.
* **Environment separation** — Spring profiles provide a mechanism for separating local, test, Docker, and production configuration.
* **Container readiness** — The application can be packaged and deployed as a Docker container.
* **Automated verification** — CI should verify the application before deployment.
* **Health monitoring** — Actuator provides health information for deployment and monitoring systems.
* **Secure secrets management** — Credentials and cryptographic secrets should be supplied through secure environment configuration.
* **Platform independence** — The template does not require a specific cloud provider or hosting platform.

The goal is to provide a deployment-ready foundation while allowing applications built from the template to choose the hosting platform, database provider, container platform, and deployment strategy appropriate to their requirements.
