# Business Domain Extraction

## Objective

Extract the Fleet Management business domain from the application while preserving a production-ready Spring Boot foundation.

The end result of this sprint should be a secure, runnable application that contains only reusable infrastructure. Authentication, authorization, error handling, logging, validation, testing, Docker, and API standards should remain intact while all Fleet Management functionality is removed.

---

# Success Criteria

At the completion:

* No Fleet Management business logic remains.
* Authentication is domain-neutral.
* The application builds successfully.
* Tests pass.
* Swagger loads correctly.
* Docker starts successfully.
* GitHub Actions passes.
* The application remains a valid foundation for future development.

---

# Phase 1 — Authentication Foundation Decoupling

## Goal

Remove business-specific concepts from the authentication layer before removing the Fleet domain.

### User

* [ ] Remove Company relationship
* [ ] Remove company_id index
* [ ] Remove getCompany()
* [ ] Remove setCompany()

### Authentication

* [ ] Remove CompanyRepository dependency from AuthenticationService
* [ ] Remove Company creation during registration
* [ ] Remove Company assignment to User
* [ ] Remove company logging
* [ ] Remove companyName from RegisterRequest

### Security

* [ ] Remove companyId from CustomUserDetails
* [ ] Update JwtAuthFilter to remove Company dependency
* [ ] Verify JWT authentication still functions
* [ ] Verify authorization still functions

### Verification

* [ ] Registration works
* [ ] Login works
* [ ] JWT authentication works
* [ ] Build passes
* [ ] Tests pass

**Commit**

`refactor: remove company dependency from authentication foundation`

---

# Phase 2 — Fleet Feature Removal

## Controllers

* [ ] Remove VehicleController
* [ ] Remove MaintenanceController

## Services

* [ ] Remove VehicleService
* [ ] Remove MaintenanceService
* [ ] Remove CompanyService

## DTOs

* [ ] Remove VehicleRequestDTO
* [ ] Remove VehicleResponseDTO
* [ ] Remove Maintenance DTOs
* [ ] Remove Fleet-specific mapping classes

## Repositories

* [ ] Remove VehicleRepository
* [ ] Remove MaintenanceRepository
* [ ] Remove CompanyRepository

## Entities

* [ ] Remove Vehicle
* [ ] Remove MaintenanceRecord
* [ ] Remove Company

### Verification

* [ ] Application starts
* [ ] Build passes
* [ ] Tests pass

**Commit**

`refactor: remove fleet management business domain`

---

# Phase 3 — Domain Cleanup

## Remove Remaining Fleet References

### Documentation

* [ ] Fleet README references
* [ ] Fleet API examples
* [ ] Fleet screenshots
* [ ] Fleet terminology

### Configuration

* [ ] OpenAPI descriptions
* [ ] Example JSON
* [ ] Seed data
* [ ] Sample configuration

### Logging

* [ ] Fleet-specific log messages
* [ ] Company IDs in logs
* [ ] Vehicle references

### Code Cleanup

* [ ] Remove unused imports
* [ ] Remove dead code
* [ ] Remove unused dependencies
* [ ] Verify package organization

### Verification

* [ ] Build passes
* [ ] Tests pass
* [ ] Swagger loads
* [ ] Docker starts
* [ ] GitHub Actions passes

**Commit**

`refactor: complete fleet domain extraction`

---

# Sprint Verification Gate

The application must still provide:

## Infrastructure

* [ ] Spring Boot application starts
* [ ] Security configuration
* [ ] JWT authentication
* [ ] Role-based authorization
* [ ] Global exception handling
* [ ] Request validation
* [ ] Logging
* [ ] OpenAPI / Swagger
* [ ] Docker support
* [ ] GitHub Actions CI

## Quality

* [ ] No Fleet Management references remain
* [ ] No Company dependency remains
* [ ] No dead code
* [ ] Clean build
* [ ] Passing test suite

---

# Outcome

At the conclusion, the repository should represent a generic Spring Boot API foundation rather than a Fleet Management application.

Sprint T3 will begin generalizing project identity, package names, and infrastructure to prepare the template for public reuse.
