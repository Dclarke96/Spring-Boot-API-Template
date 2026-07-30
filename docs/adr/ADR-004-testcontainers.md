# ADR-004: Testcontainers for Integration Testing

* **Status:** Accepted
* **Date:** 2026-07-30
* **Decision Type:** Testing / Infrastructure

---

## Context

The Spring Boot API Template requires an integration testing strategy that validates application behavior against a real PostgreSQL database while remaining reproducible across developer environments and CI.

Relying on a developer-managed local PostgreSQL installation introduces environmental differences and additional setup requirements.

The template is intended to be cloned and extended by other developers, so integration tests should work against a predictable database environment without requiring developers to manually configure a persistent database instance.

---

## Decision

The template uses **Testcontainers with PostgreSQL** to provide an isolated database environment for integration testing.

The integration testing environment combines:

* JUnit 5
* Spring Boot Test
* MockMvc
* Testcontainers
* PostgreSQL

The general test environment is:

```text id="w0w6a2"
Integration Test
      ↓
Spring Boot Application
      ↓
Testcontainers
      ↓
PostgreSQL Container
```

The containerized database is created for the integration test environment rather than relying on a developer's existing PostgreSQL installation.

---

## Current Implementation

The integration testing foundation provides shared infrastructure for API integration tests.

The test framework supports:

* Starting a PostgreSQL container for integration testing.
* Supplying database connection properties to the Spring application.
* Running the application against a real PostgreSQL database.
* Executing HTTP-level requests through MockMvc.
* Testing authentication and authorization workflows.
* Testing validation and exception handling.
* Testing resource creation, retrieval, update, and deletion.
* Cleaning database state between tests where required.

The shared integration test infrastructure allows additional API resources to follow consistent testing patterns.

---

## Rationale

Testcontainers was selected because it provides a reproducible database environment without requiring each developer or CI environment to maintain a separately configured PostgreSQL instance.

Using the same database technology in integration tests as the application uses in deployment also helps identify database-specific behavior that would not necessarily be detected by mocking persistence operations.

The approach provides a balance between realistic integration testing and developer convenience.

---

## Consequences

### Positive

* Integration tests run against a real PostgreSQL database.
* Test environments are isolated from developer databases.
* Database configuration is reproducible.
* CI environments can use the same testing approach as local development.
* Database-specific behavior can be validated.
* Developers do not need to maintain a dedicated test database.
* The testing infrastructure can be reused for additional API resources.

### Negative

* Docker is required to run the integration test suite.
* Container startup introduces additional test execution time.
* Developers and CI environments must provide a working container runtime.
* Testcontainers introduces an additional testing dependency.
* Troubleshooting container-runtime issues can add complexity to local development.

These trade-offs are considered acceptable because reliable integration testing against the actual database technology is more valuable than minimizing local test infrastructure requirements.

---

## Alternatives Considered

### Local PostgreSQL Database

Tests could connect to a developer-managed PostgreSQL instance.

This was rejected because it creates additional environment-specific configuration and makes test execution less reproducible.

### Embedded Database

An embedded database could provide faster and simpler tests.

This was not selected because an embedded database may behave differently from PostgreSQL and therefore would not provide the same confidence in PostgreSQL-specific persistence behavior.

### Mocking the Repository Layer

Repository interactions could be mocked in application tests.

This remains appropriate for certain unit tests, but it does not replace integration testing against a real database.

The template therefore uses repository mocking where appropriate for unit tests while retaining Testcontainers for integration testing.

---

## Future Evolution

The Testcontainers approach can be extended as the template grows.

Future testing infrastructure may introduce additional containers for services such as:

* External service dependencies.
* Message brokers.
* Caches.
* Other infrastructure required by applications built from the template.

Additional infrastructure should only be introduced when required by the application being developed.

---

## Related Documentation

* [`architecture.md`](../architecture.md)
* [`template-guide.md`](../template-guide.md)
* [`ADR-001: Layered Architecture`](ADR-001-layered-architecture.md)
* [`ADR-003: JWT Security`](ADR-003-jwt-security.md)
