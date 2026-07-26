# Business Domain Decoupling

## Objective

Decouple business-specific dependencies from the application foundation while preserving a production-ready Spring Boot API structure.

The goal of this phase is not to remove all example business functionality, but to separate reusable infrastructure from domain-specific concerns.

The completed foundation should provide:

* Authentication and authorization infrastructure.
* JWT security.
* Standardized API responses.
* Validation.
* Exception handling.
* Logging.
* Integration testing foundation.
* Docker support.
* CI/CD workflow.
* OpenAPI documentation.

The current example functionality remains as a demonstration domain showing how additional applications can be built using the foundation.

---

# Success Criteria

At completion:

* Authentication is independent from business domains.
* No company or tenant ownership concepts remain.
* Security does not depend on fleet-specific data.
* Domain services do not influence authentication behavior.
* Application builds successfully.
* Tests pass.
* Swagger loads correctly.
* Docker starts successfully.
* GitHub Actions passes.
* The application represents a reusable Spring Boot foundation with an example domain.

---

# Phase 1 — Authentication Foundation Decoupling

## Goal

Remove company-specific concepts from authentication and security infrastructure.

---

## User Entity

Completed:

- [x] Removed Company relationship.
- [x] Removed company ownership fields.
- [x] Removed company-specific helper methods.
- [x] User entity represents standalone authentication identity.

---

## Authentication

Completed:

- [x] Removed CompanyRepository dependency from AuthenticationService.
- [x] Removed company creation during registration.
- [x] Removed company assignment during user creation.
- [x] Removed company-specific logging.
- [x] Removed companyName from registration flow.

Authentication now manages only:

* User creation.
* Password encoding.
* Credential validation.
* JWT generation.

---

## Security

Completed:

- [x] Removed company identifiers from security context.
- [x] Removed company dependencies from JWT authentication flow.
- [x] Verified JWT authentication continues functioning.
- [x] Verified authorization continues functioning.

---

## Verification

Completed:

- [x] Registration works.
- [x] Login works.
- [x] JWT authentication works.
- [x] Build passes.
- [x] Integration tests pass.

---

# Phase 2 — Domain Boundary Cleanup

## Goal

Remove business-domain coupling from shared application infrastructure.

---

## Completed Cleanup

### Services

Completed:

- [x] Removed CompanyService.
- [x] Removed company-related business workflows.
- [x] Verified remaining services do not depend on authentication ownership concepts.

---

## Repositories

Completed:

- [x] Removed CompanyRepository.
- [x] Removed company-scoped repository queries.

---

## Entities

Completed:

- [x] Removed Company entity.
- [x] Removed entity relationships tied to company ownership.

Remaining example domain entities:

* Vehicle
* MaintenanceRecord
* User

These remain intentionally to demonstrate API patterns and application structure.

---

# Phase 3 — Documentation and Identity Cleanup

## Goal

Ensure repository documentation reflects the new reusable foundation.

---

## Documentation Updates

Completed:

- [x] Updated README to describe reusable API foundation.
- [x] Removed company-level isolation references.
- [x] Updated API documentation examples.
- [x] Updated architecture documentation.
- [x] Removed outdated authentication examples.

---

## Configuration Review

Reviewed:

- [x] Application properties.
- [x] Test configuration.
- [x] Docker configuration.
- [x] Build configuration.

Remaining fleet naming in application metadata is considered branding cleanup and will be addressed in future generalization phases.

---

# Verification Gate

The application must provide:

## Infrastructure

Completed:

- [x] Spring Boot application starts.
- [x] JWT authentication.
- [x] Role-based authorization foundation.
- [x] Global exception handling.
- [x] Request validation.
- [x] Logging.
- [x] OpenAPI / Swagger.
- [x] Docker support.
- [x] GitHub Actions CI.

---

## Quality

Completed:

- [x] No Company dependency remains.
- [x] No tenant ownership assumptions remain.
- [x] No authentication/business domain coupling remains.
- [x] Clean build.
- [x] Passing test suite.

---

# Current State

The repository now represents:

```
Reusable Spring Boot Foundation
            +
Example Business Domain
            +
Production Development Practices
```

The foundation is ready for future generalization phases.

---

# Future Work

Future phases may address:

* Package name generalization.
* Application branding cleanup.
* Example domain extraction strategy.
* More generic templates.
* Additional reusable modules.
* Public template preparation.

---

# Outcome

The v0.2.0 milestone successfully separates reusable application infrastructure from company-specific business logic.

The project is no longer dependent on the previous domain-specific concepts while retaining a functional example domain that demonstrates how the foundation can be extended.