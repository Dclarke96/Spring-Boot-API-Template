# ADR-002: DTO Boundary

* **Status:** Accepted
* **Date:** 2026-07-30
* **Decision Type:** API Design / Architecture

---

## Context

The API template needs to provide a clear separation between externally exposed API contracts and internal persistence models.

Directly exposing JPA entities through REST endpoints creates coupling between the API contract and the database model. Changes to persistence entities could unintentionally change the API exposed to clients.

The template also needs to support applications where API requirements may evolve independently from database structure and internal application models.

---

## Decision

The template uses **Data Transfer Objects (DTOs) as the boundary between the API layer and internal application models**.

The API does not directly expose persistence entities as request or response contracts. Controllers therefore depend on API-specific DTOs rather than persistence entities.

The general request flow is:

```text
HTTP Request
     ↓
Controller
     ↓
Request DTO
     ↓
Service
     ↓
Persistence Entity
```

The response flow is:

```text
Persistence Entity
     ↓
Service
     ↓
Response DTO
     ↓
Controller
     ↓
HTTP Response
```

DTOs are therefore treated as part of the API contract rather than as persistence models.

---

## Current Implementation

The example domain demonstrates this pattern through request and response DTOs such as:

* `VehicleRequestDTO`
* `VehicleResponseDTO`
* `MaintenanceRequestDTO`
* `MaintenanceResponseDTO`

Controllers accept request DTOs and return response DTOs rather than exposing JPA entities directly.

Persistence entities remain internal to the application's persistence and service workflows.

---

## Rationale

DTOs were selected to establish an explicit API boundary and reduce coupling between external consumers and internal implementation details.

This provides several benefits:

* Prevents direct exposure of persistence entities.
* Allows API contracts to evolve independently of database structure.
* Provides control over which fields are accepted or returned.
* Supports API-specific validation.
* Reduces accidental exposure of internal persistence relationships.
* Makes request and response models explicit.
* Provides a consistent pattern for future resources added to the template.

This approach also makes the template's intended API design easier for developers to understand and reproduce when adding new resources.

---

## Consequences

### Positive

* API contracts remain independent from persistence models.
* Persistence entities can change without necessarily changing API responses.
* Request and response structures are explicit.
* Sensitive or internal persistence fields can be excluded from API responses.
* API validation can be defined independently from persistence constraints.
* New API versions or response shapes can be introduced more easily.
* The pattern provides a clear example for developers extending the template.

### Negative

* Additional classes and mapping code are required.
* Developers must maintain DTOs alongside persistence entities.
* Simple resources may require more code than directly exposing entities.
* Changes to an entity and its corresponding DTOs may require updates in multiple locations.

These trade-offs are considered acceptable because the template prioritizes maintainability and clear API boundaries over minimizing the amount of code required for simple resources.

---

## Alternatives Considered

### Exposing JPA Entities Directly

This approach would reduce the number of classes and eliminate some mapping code.

However, it would tightly couple the external API contract to the persistence model and could expose internal implementation details.

This was rejected for the template.

### Using a Single Model for API and Persistence

A shared model could reduce duplication but would combine responsibilities that may evolve independently.

This was rejected because the template is intended to demonstrate maintainable API design that can scale beyond simple CRUD applications.

---

## Future Evolution

The current DTO boundary provides separation between the API and persistence layers without introducing unnecessary additional abstraction.

As application complexity increases, future architectures may introduce additional separation such as:

```text
API DTOs
    ↓
Application Models
    ↓
Domain Models
    ↓
Persistence Models
```

This is not required by the current architecture.

Additional model boundaries should only be introduced when application complexity and business requirements justify them.

---

## Related Documentation

* [`architecture.md`](../architecture.md)
* [`api-design.md`](../api-design.md)
* [`template-guide.md`](../template-guide.md)
* [`ADR-001: Layered Architecture`](ADR-001-layered-architecture.md)
