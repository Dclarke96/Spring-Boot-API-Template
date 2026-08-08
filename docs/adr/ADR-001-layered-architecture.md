# ADR-001: Layered Architecture

* **Status:** Accepted
* **Date:** 2026-07-30
* **Decision Type:** Architecture

---

## Context

The Spring Boot API Template was extracted from a production-style application and needed an architecture that could provide clear separation of responsibilities without introducing unnecessary complexity.

The template is intended to serve as a reusable foundation for developers building Spring Boot REST APIs. The architecture therefore needs to be:

* Easy to understand.
* Practical to implement.
* Maintainable as applications grow.
* Testable at multiple levels.
* Flexible enough to support different business domains.

A more heavily abstracted architecture, such as Clean Architecture or a domain-centric design, could provide stronger separation between business logic and infrastructure. However, introducing those boundaries before application complexity justifies them would add unnecessary abstraction to the template.

---

## Decision

The template uses a **layered architecture** as its current architectural foundation.

The primary application flow is:

```text
Client
    ↓
Controller
    ↓
DTO Boundary
    ↓
Service
    ↓
Repository
    ↓
Database
```

Each component has a defined responsibility.

### Controller Layer

Handles HTTP requests and responses and provides the API boundary.

Responsibilities include:

* Request handling.
* Structural request validation.
* API contract management.
* Mapping requests and responses through DTOs.

### DTO Boundary

Provides separation between external API contracts and internal persistence models.

DTOs define the data exposed through the API without directly exposing persistence entities.

The DTO boundary is an API design convention rather than a standalone application layer.

### Service Layer

Contains application workflows and coordinates operations between the API boundary and persistence layer.

Responsibilities include:

* Application workflows.
* Business rules and validation.
* Repository coordination.
* Transaction boundaries.
* Application-specific operations.

### Repository Layer

Provides persistence operations and database access through Spring Data JPA.

Repositories isolate database access from service-level application workflows.

### Database

PostgreSQL provides the persistence layer for the current example implementation.

Applications built from the template may use a different relational database where appropriate, provided the persistence configuration and application requirements support it.

---

## Cross-Cutting Concerns

The layered application flow is supported by shared technical concerns that operate across multiple layers.

These include:

* Security.
* Exception handling.
* Logging.
* Configuration.
* Testing infrastructure.

These concerns are documented separately within the architecture documentation where appropriate.

---

## Rationale

A layered architecture was selected because it provides a strong balance between structure and simplicity.

The decision provides:

* Clear separation of responsibilities.
* Predictable project structure.
* Reduced coupling between API and persistence concerns.
* Straightforward testing boundaries.
* A familiar architecture for Spring Boot developers.
* A practical foundation for different application domains.

The architecture is intentionally not designed around abstractions that are not currently required.

---

## Consequences

### Positive

* The architecture is easy for new developers to understand.
* Responsibilities are clearly separated.
* API contracts are protected through DTOs.
* Persistence concerns remain isolated within the repository layer.
* Services provide a clear location for application workflows.
* The structure is suitable for a reusable Spring Boot template.
* The architecture supports incremental evolution.

### Negative

* Service classes may become more complex as business rules grow.
* Business logic can become coupled to framework or persistence concerns.
* Strong domain boundaries are not enforced by the current structure.
* Large applications may eventually require additional architectural boundaries.

These trade-offs are considered acceptable for the current scope of the template.

---

## Future Evolution

The layered architecture is not intended to prevent future architectural evolution.

As application complexity increases, selected capabilities may adopt stronger Clean Architecture or domain-centric patterns.

Potential future improvements include:

* Explicit application use cases.
* Richer domain models.
* Domain-level repository abstractions.
* Stronger separation between application and infrastructure concerns.
* Reduced dependency on framework-specific implementations.

Any future architectural changes should be introduced incrementally and justified by actual application requirements rather than adopted solely for structural complexity.

---

## Related Documentation

* [`architecture.md`](../architecture.md)
* [`template-guide.md`](../template-guide.md)
* [`api-design.md`](../api-design.md)
