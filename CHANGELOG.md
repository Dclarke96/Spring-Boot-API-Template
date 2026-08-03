# Changelog

All notable changes to this project will be documented in this file.

This project follows [Semantic Versioning](https://semver.org/) for release versioning.

---

## [Unreleased]

Changes currently being developed for the next release.

---

## [0.5.0] - 2026-08-02

### Added

* MIT License
* CONTRIBUTING.md with contribution workflow and development guidelines
* GitHub issue templates for bug reports and feature requests
* Pull request template
* Architecture Decision Records documenting major architectural and engineering decisions
* Expanded JaCoCo test coverage reporting
* Updated architecture diagram and supporting documentation

### Changed

* Professionalized repository documentation and project structure
* Updated README to reflect the current reusable Spring Boot API Template
* Updated API design documentation
* Updated deployment documentation
* Updated template usage documentation
* Updated architecture documentation
* Added historical documentation for the v0.2.0 business domain decoupling milestone
* Replaced centralized design-decision documentation with Architecture Decision Records
* Expanded unit test coverage across security and authentication controllers
* Updated GitHub Actions CI configuration and repository workflows
* Improved contribution, issue reporting, and pull request guidance
* Aligned project documentation with the current Java 21, Spring Boot, testing, Docker, and CI/CD configuration

### Removed

* Outdated design decision documentation and references
* Obsolete documentation references that no longer reflect the current repository structure

---

## [0.4.0] - 2026-07-28

### Added

* PostgreSQL integration testing foundation using Testcontainers
* Shared `BaseIntegrationTest` framework and reusable test utilities
* Authentication integration test coverage
* Exception handling integration test coverage
* Vehicle integration test coverage
* Maintenance integration test coverage
* Developer template guide

### Changed

* Standardized integration tests around the Arrange / Act / Assert pattern
* Improved Docker-based development and testing configuration
* Updated project toolchain and build configuration for Java 21
* Updated GitHub Actions CI configuration for Java 21
* Improved environment configuration and profile organization
* Expanded architecture documentation
* Improved developer onboarding and template usage documentation
* Updated deployment, API, and design documentation
* Improved Testcontainers and Docker connectivity configuration
* Improved environment and application profile configuration

---

## [0.3.0] - 2026-07-26

### Changed

* Generalized application identity for reusable Spring Boot API template usage
* Renamed the application package to `com.dylanclarke.springbootapitemplate`
* Renamed the Spring Boot application entry point
* Removed remaining Fleet Management-specific application identity
* Generalized API, security, service, repository, and model package references
* Updated OpenAPI metadata for the reusable template
* Generalized environment and Spring profile configuration
* Updated Docker configuration for template usage
* Updated GitHub Actions database configuration for the generalized template
* Updated project documentation to reflect the reusable template structure

### Removed

* Fleet Management-specific `Role` implementation and package references
* Remaining application-specific naming and identity

---

## [0.2.0] - 2026-07-25

### Added

* Business domain decoupling documentation
* Reusable authentication foundation
* Standardized API response and error-handling patterns
* Expanded logging and security foundation

### Changed

* Extracted business-domain dependencies from the original Fleet Management application
* Established clearer separation between reusable application infrastructure and business-domain logic
* Refactored authentication and security components for template reuse
* Refactored controllers, DTOs, services, repositories, and models to support domain extraction
* Updated integration testing infrastructure for the reusable API foundation
* Updated project documentation to reflect the reusable template architecture

### Removed

* Fleet Management-specific company domain and repository
* Application-specific integration tests and authorization tests that depended on the original domain
* Remaining company-specific domain dependencies

---

## [0.1.0] - 2026-07-20

### Added

* Initial standalone Spring Boot API template repository
* Gradle-based project foundation
* Initial PostgreSQL integration
* Initial GitHub Actions CI foundation
* Initial reusable project structure

### Fixed

* Corrected Gradle wrapper execution configuration for CI environments

---

## Release History

| Version   | Date       | Milestone                        |
| --------- | ---------- | -------------------------------- |
| **0.5.0** | 2026-08-02 | Repository Professionalization   |
| **0.4.0** | 2026-07-28 | Template Hardening               |
| **0.3.0** | 2026-07-26 | Template Generalization          |
| **0.2.0** | 2026-07-25 | Architecture & Domain Extraction |
| **0.1.0** | 2026-07-20 | Repository Foundation            |
