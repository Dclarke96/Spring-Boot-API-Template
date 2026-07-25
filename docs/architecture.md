# Architecture Overview

## Current State & Target Architecture

The Spring Boot API Template began with a **classic layered architecture**:

```
Controller → Service → Repository → Database
```

This structure supported rapid development, feature delivery, and early validation of application functionality.

As the project has matured, the architecture is evolving toward a **Clean Architecture / Domain-Centric approach** to improve long-term maintainability, testability, and reusability as a foundation for future applications.

The migration is intentionally incremental. The goal is not to rewrite the application, but to gradually improve boundaries while maintaining working functionality.

---

# Why the Architecture Is Evolving

The transition toward a domain-centric architecture is driven by several goals:

* Protect business logic from framework and infrastructure concerns.
* Reduce coupling between Spring, persistence technologies, and application rules.
* Improve testability of core business behavior.
* Create reusable patterns that can support future applications.
* Establish clearer boundaries as additional capabilities are introduced:

  * Authentication and authorization
  * Background processing
  * Reporting
  * Notifications
  * Additional business domains

---

# Target Architecture (Future State)

The intended architecture follows Clean Architecture principles:

```
src/main/java/com/template/

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

---

# Target Responsibilities

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

Responsible for external concerns:

* Database persistence
* REST adapters
* Security configuration
* External integrations
* Framework-specific implementations

---

# Migration Strategy

The migration follows a **Strangler Fig approach**.

Instead of rebuilding the entire application, functionality is migrated incrementally by business capability.

Example migration path:

1. Authentication foundation
2. Core business capability migration
3. Additional application domains

Existing layered components remain functional until their replacement is introduced.

This approach reduces risk while allowing architectural improvements over time.

---

# Current Architecture (Version 0.2.0)

The current implementation follows a layered architecture with additional separation for cross-cutting concerns.

```
API Layer
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
```

---

# Current Layer Responsibilities

## API Layer

Responsible for:

* Handling HTTP requests and responses
* Request validation
* API contract management
* Mapping requests/responses through DTOs

Examples:

* `VehicleController`
* `MaintenanceController`
* `AuthController`

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

The application separates shared technical concerns:

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

---

# Example Domain Entities

The current repository includes example business entities used to validate the template architecture.

These domains are intentionally included as reference implementations and can be replaced by application-specific models when using this template.

Current entities represent persistence models mapped to database tables.

---

## Vehicle Example

Represents an example business resource including:

* Resource identification
* Operational details
* Lifecycle information
* Domain-specific attributes

Future architecture iterations may move business rules from persistence entities into dedicated domain models.

---

## MaintenanceRecord Example

Represents an example related resource including:

* Maintenance details
* Resource relationships
* Operational records

Future domain migration may introduce richer business rules and lifecycle behavior.

---

# Data Transfer Objects (DTOs)

DTOs provide a boundary between external API contracts and internal application models.

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

# Validation Strategy

Validation currently occurs at multiple levels.

## API Boundary Validation

Uses Jakarta Validation for structural validation:

Examples:

* Required fields
* String length limits
* Input formatting

---

## Application Business Validation

Business rules are currently enforced within service workflows.

Examples:

* Duplicate resource checks
* Resource existence checks
* Relationship validation

Future domain migration will move appropriate business rules into domain models and use cases.

---

# Architecture Diagram

The current implementation follows a layered architecture:

```
Controller
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
- DTOs
- Security
- Exception Handling
- Logging
```

Future architecture diagrams should represent the target Clean Architecture structure as migration progresses.