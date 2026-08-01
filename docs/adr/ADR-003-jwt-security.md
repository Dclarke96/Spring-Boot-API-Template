# ADR-003: JWT Security Architecture

* **Status:** Accepted
* **Date:** 2026-07-30
* **Decision Type:** Security / Architecture

---

## Context

The template requires a reusable authentication and authorization foundation that can support different business domains without being coupled to domain-specific ownership or organizational concepts.

The original application contained business-specific security relationships that were appropriate for its original domain but were not appropriate for a reusable API template.

As part of the domain decoupling work, authentication and authorization were separated from those business-specific concepts.

The resulting security architecture needed to provide:

* User authentication.
* Credential validation.
* JWT generation.
* JWT validation.
* A role-based authorization foundation.
* Current user context.
* Protection of API endpoints.

---

## Decision

The template uses **Spring Security with JWT-based authentication and role-based authorization** as its reusable security foundation.

Authentication and authorization are implemented as infrastructure concerns and are not dependent on any particular business domain.

The authentication flow is:

```text id="0l9y3k"
Authentication Request
        ↓
AuthController
        ↓
AuthenticationService
        ↓
UserRepository
        ↓
Credential Validation
        ↓
JwtService
        ↓
JWT
```

Subsequent authenticated requests follow:

```text id="m8n5a2"
HTTP Request
        ↓
JWT
        ↓
JwtAuthFilter
        ↓
Authentication Context
        ↓
Authorization
        ↓
Controller
```

The security implementation provides authentication independently of business-domain ownership concepts.

---

## Current Implementation

The security foundation includes components such as:

* `AuthenticationService`
* `JwtService`
* `JwtAuthFilter`
* `CurrentUserService`
* Spring Security configuration
* `User`
* `Role`

`AuthenticationService` coordinates registration and login workflows.

`JwtService` is responsible for JWT creation and related token operations.

`JwtAuthFilter` processes incoming requests and establishes the authenticated security context when a valid token is provided.

`CurrentUserService` provides access to the authenticated user within application workflows where required.

A role-based authorization foundation is used to control access to protected resources.

---

## Domain Independence

Authentication does not depend on business-specific ownership concepts.

The reusable security foundation does not require:

* Company entities.
* Company repositories.
* Tenant ownership relationships.
* Fleet-specific security relationships.
* Domain-specific ownership identifiers.

The `User` entity represents authentication identity independently from the example business domain.

This allows the security foundation to be reused when the template is adapted to a different application domain.

---

## Rationale

JWT-based authentication was selected because it provides a practical stateless authentication mechanism for REST APIs and integrates well with Spring Security.

Separating authentication from business-domain concepts provides additional benefits:

* The security foundation can be reused across applications.
* Business domains do not need to own authentication infrastructure.
* Authentication workflows remain consistent when example domains are replaced.
* Authorization can be applied to different resources without changing the underlying authentication model.
* The template remains independent from the original application's business ownership model.
* The API does not require traditional server-side session state for authenticated requests.

The approach provides a practical security foundation without requiring a more complex identity architecture.

---

## Consequences

### Positive

* Authentication is reusable across different business domains.
* API endpoints can be protected consistently.
* JWTs provide stateless request authentication.
* Role-based authorization provides a foundation for access control.
* Security infrastructure remains separate from example business functionality.
* The authentication model is not tied to company or tenant ownership.
* The template can be adapted to new domains without redesigning its authentication foundation.

### Negative

* JWT lifecycle management must be handled correctly.
* Token security depends on appropriate secret management and configuration.
* Stateless JWT authentication introduces considerations around token expiration and revocation.
* Security configuration is more complex than unauthenticated API endpoints.
* Role-based authorization may require additional design as application-specific authorization rules become more complex.

These trade-offs are considered appropriate for the current reusable API template.

---

## Alternatives Considered

### Session-Based Authentication

Traditional server-side sessions could provide authentication state managed by the application.

This was not selected because the template is intended primarily as a REST API foundation where stateless authentication provides a practical default.

### Domain-Coupled Authentication

Authentication could have remained tied to business-domain concepts such as company or tenant ownership.

This was rejected because it would prevent the security infrastructure from being reused across different application domains.

### External Identity Provider

An external identity provider could provide authentication and identity management.

This was not selected as the default because it would introduce external infrastructure and configuration requirements that are not appropriate for the base template.

Applications with more advanced identity requirements can replace or extend the security implementation as needed.

---

## Future Evolution

Future applications built from the template may require more advanced authentication or authorization capabilities.

Potential future improvements could include:

* Refresh token workflows.
* More granular permissions.
* Fine-grained authorization policies.
* External identity providers.
* OAuth 2.0 / OpenID Connect integration.
* Domain-specific authorization policies.

These capabilities are outside the current template scope and should only be introduced when application requirements justify them.

---

## Related Documentation

* [`architecture.md`](../architecture.md)
* [`api-design.md`](../api-design.md)
* [`ADR-001: Layered Architecture`](ADR-001-layered-architecture.md)
* [`ADR-002: DTO Boundary`](ADR-002-dto-boundary.md)
