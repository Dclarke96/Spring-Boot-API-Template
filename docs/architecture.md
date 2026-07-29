# Architecture Overview

## Current State & Future Architecture Direction

The Spring Boot API Template currently follows a **layered architecture** designed to provide a maintainable, testable, and reusable foundation for Spring Boot REST API development.

The current structure:

```
Controller → DTO Boundary → Service → Repository → Database
```

This architecture provides clear separation between API concerns, business workflows, persistence operations, and cross-cutting application concerns.

As applications grow in complexity, the template can evolve toward stronger **Clean Architecture and Domain-Centric patterns** to further improve maintainability, testability, and separation of responsibilities.

The goal is not to introduce unnecessary complexity early, but to provide a foundation that can scale as application requirements increase.

---

# Why the Architecture Is Designed to Evolve

The template architecture is designed with future expansion in mind.

Potential future improvements may include:

* Stronger separation between business rules and infrastructure concerns.
* Increased independence from framework-specific implementations.
* Additional domain-driven patterns.
* More explicit application use cases.
* Support for additional business capabilities:

  * Background processing
  * Reporting
  * Notifications
  * Additional business domains

The architecture intentionally balances practical development speed with long-term maintainability.

---

# Future Architecture Direction

Future iterations of the template may adopt additional Clean Architecture principles:

```
src/main/java/

├── domain/
│   ├── model/
│   ├── repository/
│   ├── service/
│   └── usecase/

├── application/
│   └── DTOs, orchestration, input/output models

├── infrastructure/
│   ├── persistence/
│   ├── web/
│   ├── external/
│   └── config/

├── common/
│   └── shared utilities, exceptions, constants

└── Application.java
```

This structure represents a future direction rather than the current implementation.

---

# Future Layer Responsibilities

## Domain Layer

Responsible for:

* Core business rules
* Domain entities
* Business invariants
* Domain-specific behavior

The domain layer should remain independent from:

* Spring Framework
* Database technology
* External services

---

## Application Layer

Responsible for:

* Application workflows
* Use case orchestration
* Input/output models
* Coordinating domain operations

---

## Infrastructure Layer

Responsible for external technical concerns:

* Database persistence
* REST adapters
* Security configuration
* External integrations
* Framework-specific implementations

---

# Architecture Evolution Strategy

The architecture is designed to evolve incrementally as application complexity increases.

Future architectural improvements should be introduced by capability rather than through large-scale rewrites.

This approach allows:

1. Existing functionality to remain stable.
2. New patterns to be introduced gradually.
3. Architectural improvements to be validated through working features.

The template prioritizes practical maintainability while allowing future adoption of more advanced architectural patterns.

---

# Current Architecture

The current implementation follows a layered architecture with additional separation for cross-cutting concerns.

```
Controller Layer
        |
        ↓
DTO Boundary
        |
        ↓
Service Layer
        |
        ↓
Repository Layer
        |
        ↓
Database
```

Supporting components include:

```
DTO Layer
Security Layer
Exception Handling
Logging
Configuration
Testing Infrastructure
```

---

# Current Layer Responsibilities

## API Layer

Responsible for:

* Handling HTTP requests and responses
* Request validation
* API contract management
* Mapping requests and responses through DTOs

Examples:

* `VehicleController`
* `MaintenanceController`
* `AuthController`

---

## DTO Layer

Responsible for:

* Defining external API contracts
* Separating API models from persistence entities
* Controlling request and response structures

Examples:

* `VehicleRequestDTO`
* `VehicleResponseDTO`
* `MaintenanceRequestDTO`
* `MaintenanceResponseDTO`

Benefits:

* Prevent direct exposure of persistence models.
* Allow API contracts to evolve independently.
* Reduce coupling between clients and internal implementation details.

---

## Service Layer

Responsible for:

* Business workflows
* Application logic
* Coordination between repositories and models
* Transaction boundaries

Examples:

* `VehicleService`
* `MaintenanceService`
* `AuthenticationService`

---

## Persistence Layer

Responsible for:

* Database access
* Entity persistence
* Query operations

Examples:

* `VehicleRepository`
* `MaintenanceRepository`
* `UserRepository`

---

# Cross-Cutting Concerns

The application separates shared technical concerns that support all application layers.

---

## Security

Responsible for:

* JWT authentication
* Authorization checks
* User context handling

Examples:

* `JwtAuthFilter`
* `JwtService`
* `CurrentUserService`

---

## Exception Handling

Responsible for:

* Consistent API error responses
* Centralized exception management
* Traceability

Examples:

* `GlobalExceptionHandler`
* `ErrorResponse`

---

## Logging

Responsible for:

* Request visibility
* Operational troubleshooting
* Application diagnostics

The application uses structured request logging with trace identifiers to support debugging and operational visibility.

---

# Testing Architecture

The template includes integration testing infrastructure designed to provide a reusable foundation for future API resources.

Integration testing uses:

* JUnit 5
* Spring Boot Test
* MockMvc
* Testcontainers
* PostgreSQL containerized test database

The integration testing framework provides:

* Isolated database testing environments.
* HTTP-level API validation.
* Authentication workflow testing.
* Resource lifecycle testing.
* Database cleanup between tests.

The testing infrastructure is designed to allow new resources to follow consistent testing patterns.

---

# Example Domain Entities

The repository includes example business entities used to demonstrate the template architecture.

These domains are intentionally included as reference implementations and can be replaced with application-specific models when using this template.

Current entities represent persistence models mapped to database tables.

---

## Vehicle Example

Represents an example business resource including:

* Resource identification
* Operational details
* Lifecycle information
* Domain-specific attributes

Future architecture iterations may move additional business rules from persistence entities into dedicated domain models.

---

## MaintenanceRecord Example

Represents an example related resource including:

* Maintenance details
* Resource relationships
* Operational records

Future domain evolution may introduce richer business rules and lifecycle behavior.

---

# Validation Strategy

Validation currently occurs at multiple levels.

---

## API Boundary Validation

Uses Jakarta Validation for structural validation.

Examples:

* Required fields
* String length limits
* Input formatting

---

## Application Business Validation

Business rules are enforced within service workflows.

Examples:

* Duplicate resource checks
* Resource existence checks
* Relationship validation

Future architecture improvements may move appropriate business rules into dedicated domain models and use cases.

---

# Architecture Diagram

The current implementation follows a layered architecture:

```
Controller
    |
    ↓
DTO Boundary
    |
    ↓
Service
    |
    ↓
Repository
    |
    ↓
Database
```

Supporting components:

```
- Security
- Exception Handling
- Logging
- Configuration
- Testing Infrastructure
```

Future architecture diagrams should represent additional domain and application boundaries as the template evolves.
