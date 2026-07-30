# Contributing to Spring Boot API Template

Thank you for your interest in contributing to the Spring Boot API Template.

This project is intended to provide a reusable, production-oriented foundation for building secure and maintainable Spring Boot REST APIs. Contributions should preserve the template's reusable nature, maintainability, security, and developer experience.

---

## Table of Contents

* [Getting Started](#getting-started)
* [Development Environment](#development-environment)
* [Branching Strategy](#branching-strategy)
* [Development Workflow](#development-workflow)
* [Commit Conventions](#commit-conventions)
* [Testing Requirements](#testing-requirements)
* [Documentation Requirements](#documentation-requirements)
* [Code Quality](#code-quality)
* [Pull Requests](#pull-requests)
* [Security and Secrets](#security-and-secrets)
* [Architecture Changes](#architecture-changes)
* [Reporting Issues](#reporting-issues)

---

## Getting Started

Before contributing, review the project documentation, particularly:

* `README.md` — Project overview and setup
* `docs/architecture.md` — Current application architecture
* `docs/template-guide.md` — Guidance for adapting the template
* `docs/api-design.md` — API design conventions
* `docs/deployment-guide.md` — Deployment and environment configuration
* `docs/design-decisions.md` — Current architectural decisions
* `CHANGELOG.md` — Release history and upcoming changes

The project is built using Gradle and requires Java 21.

---

## Development Environment

The recommended development environment includes:

* Java 21
* Gradle Wrapper
* Docker Desktop
* PostgreSQL-compatible database support
* Git

Use the Gradle Wrapper included with the repository rather than relying on a system-wide Gradle installation.

Verify the environment with:

```bash
./gradlew --version
```

On Windows PowerShell:

```powershell
.\gradlew --version
```

Docker must be available when running integration tests that use Testcontainers.

---

## Branching Strategy

The project uses a feature-branch workflow.

The primary development flow is:

```text
feature/*
    ↓
dev
    ↓
main
```

### Maintainer Development

Project maintainers may create feature branches directly from the repository and submit changes through the `dev` branch.

Example:

```text
Repository
    ↓
feature/my-change
    ↓
dev
    ↓
main
```

### External Contributors

External contributors should fork the repository and create feature branches within their fork.

Example:

```text
Spring Boot API Template
        ↓
     Fork
        ↓
Contributor Fork
        ↓
feature/my-change
        ↓
Pull Request
        ↓
Spring Boot API Template / dev
```

External contributors should not require direct write access to the main repository.

Pull Requests from external contributors should target the `dev` branch unless the change is specifically intended to address an issue in `main` and the maintainer requests otherwise.

### Feature Branches

Create a feature branch for changes rather than working directly on `dev` or `main`.

Use descriptive branch names such as:

```text
feature/add-health-check
feature/improve-error-response
feature/update-documentation
fix/authentication-validation
docs/update-contributing-guide
```

Keep branches focused on a single feature, fix, or related change.

### Development Branch

The `dev` branch is used to integrate and validate changes before they are promoted to `main`.

### Main Branch

The `main` branch represents the stable project state and should contain release-ready changes.


---

## Development Workflow

A typical contribution should follow this process:

```text
1. Review the existing documentation
        ↓
2. Create a feature branch
        ↓
3. Implement the change
        ↓
4. Add or update tests
        ↓
5. Update documentation when necessary
        ↓
6. Run the build and test suite
        ↓
7. Review the changes locally
        ↓
8. Commit using the project's commit conventions
        ↓
9. Open a Pull Request
        ↓
10. Address review feedback
```

Before submitting a Pull Request, ensure that the change is complete, tested, documented where appropriate, and does not introduce unnecessary project-specific assumptions.

---

## Commit Conventions

This project follows a Conventional Commit-style format.

Use:

```text
type: description
```

Common types include:

| Type       | Purpose                                         |
| ---------- | ----------------------------------------------- |
| `feat`     | Add or extend functionality                     |
| `fix`      | Correct a defect                                |
| `refactor` | Change implementation without changing behavior |
| `test`     | Add or modify tests                             |
| `docs`     | Documentation changes                           |
| `chore`    | Maintenance, configuration, or tooling changes  |
| `ci`       | Continuous integration changes                  |

Examples:

```text
feat: add health check endpoint
fix: correct invalid token handling
refactor: simplify authentication service
test: add vehicle service unit tests
docs: update deployment guide
chore: update Gradle configuration
ci: update GitHub Actions workflow
```

Commit messages should be concise and describe the purpose of the change.

Avoid vague messages such as:

```text
update stuff
changes
fix
work
misc
```

---

## Testing Requirements

Changes should include appropriate tests.

The project uses both integration and unit testing patterns.

### Integration Tests

Integration tests use Spring Boot and Testcontainers with PostgreSQL.

Docker must be running when executing the integration test suite.

Run the test suite with:

```bash
./gradlew test
```

On Windows PowerShell:

```powershell
.\gradlew test
```

Integration tests should follow the established Arrange / Act / Assert structure:

```text
Arrange
Act
Assert
```

Shared integration-test infrastructure should be reused where applicable rather than duplicating Testcontainers or Spring test configuration.

### Build Verification

Before submitting a Pull Request, run:

```bash
./gradlew clean build
```

This verifies compilation, tests, and the project's build pipeline.

### Test Expectations

New functionality should include appropriate test coverage.

Bug fixes should include a regression test when practical.

Changes to existing behavior should include updates to affected tests.

Tests should verify observable behavior rather than implementation details whenever possible.

---

## Documentation Requirements

Documentation should be updated when a change affects:

* Public API behavior
* Configuration
* Environment variables
* Development workflow
* Deployment
* Architecture
* Security behavior
* Template usage
* Testing procedures

Documentation should remain generic and reusable.

Avoid introducing application-specific terminology, domain models, credentials, URLs, or configuration into the template unless the change is intentionally part of the template itself.

When documentation references commands or configuration, verify that the examples work with the current project structure.

---

## Code Quality

Contributions should follow the existing project structure and established coding conventions.

Prefer:

* Clear and descriptive names
* Small, focused classes and methods
* Appropriate separation of responsibilities
* DTO boundaries between API and persistence layers
* Service-layer business logic
* Centralized exception handling
* Reusable configuration
* Secure defaults
* Meaningful tests

Avoid:

* Unnecessary architectural changes
* Duplicated infrastructure
* Hard-coded credentials
* Application-specific assumptions
* Dead code
* Unnecessary dependencies
* Changes that introduce complexity without a clear benefit

Contributors should favor consistency with the existing architecture unless there is a documented reason to change it.

---

## Pull Requests

Pull Requests should clearly explain the proposed change.

Include:

### Description

Explain what the change does and why it is needed.

### Changes Made

Summarize the significant implementation changes.

### Testing Completed

List the tests and validation performed.

For example:

```text
./gradlew clean build
./gradlew test
```

### Documentation

Identify any documentation that was added or updated.

### Pull Request Checklist

Before submitting a Pull Request, verify:

* [ ] The change is focused and necessary
* [ ] Tests have been added or updated where appropriate
* [ ] `./gradlew clean build` passes
* [ ] Integration tests pass when applicable
* [ ] Docker/Testcontainers requirements were verified when applicable
* [ ] Documentation has been updated where necessary
* [ ] No secrets or sensitive configuration have been committed
* [ ] Commit messages follow the project's conventions
* [ ] The change does not introduce unnecessary application-specific dependencies

Pull Requests may require changes before they are merged.

---

## Security and Secrets

Never commit secrets or sensitive credentials to the repository.

Do not commit:

* Database passwords
* JWT signing secrets
* API keys
* Access tokens
* Private keys
* Certificates containing private credentials
* Production credentials
* `.env` files containing real secrets

Use the provided environment configuration examples as a reference.

The `.env.example` file should contain placeholders or example values only.

If a secret is accidentally committed, report it immediately and rotate the affected credential. Removing the secret from a later commit does not guarantee that it has been removed from Git history.

Security-related issues should be reported privately when disclosure could expose a vulnerability or credential.

---

## Architecture Changes

The project currently uses a layered Spring Boot architecture.

The primary application flow is:

```text
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

Supporting concerns include:

```text
Security
Exception Handling
Logging
Configuration
Validation
API Documentation
```

Contributors should understand the existing architecture before introducing structural changes.

Significant architectural changes should include:

1. A clear explanation of the problem being solved
2. An explanation of the proposed approach
3. Consideration of alternatives
4. Documentation of the resulting decision

Architectural decisions should be documented using the project's architecture decision record process when applicable.

Avoid introducing major architectural patterns, frameworks, or infrastructure solely for the sake of adding complexity.

---

## Reporting Issues

When reporting a bug, provide enough information to reproduce the problem.

Include, when applicable:

* Description of the problem
* Expected behavior
* Actual behavior
* Steps to reproduce
* Relevant error messages or logs
* Java version
* Operating system
* Docker version
* Relevant configuration information without exposing secrets
* Test or build command that produced the problem

For feature requests, explain:

* The problem the feature would solve
* The proposed behavior
* Why the feature would benefit the reusable template
* Any relevant alternatives considered

Feature requests should consider whether the proposed functionality belongs in a reusable API foundation rather than an application-specific implementation.

---

## Contribution Philosophy

The goal of this project is not simply to provide a working Spring Boot application.

It is to provide a maintainable, secure, understandable, and reusable foundation that another developer can clone and adapt.

Contributions should therefore favor:

* Reusability
* Simplicity
* Maintainability
* Security
* Testability
* Clear documentation
* Consistent engineering practices

Thank you for helping improve the Spring Boot API Template.
