# API Design

## Overview

The Spring Boot API Template provides a production-oriented REST API foundation with authentication, authorization, standardized responses, validation, exception handling, pagination, and example domain endpoints.

The current implementation includes an example Fleet Management domain to demonstrate API patterns and architectural practices. The example domain is intended to serve as a reference implementation rather than a required part of applications built from the template.

The API follows these principles:

* JSON request and response payloads.
* DTO-based API contracts.
* Consistent success response envelopes.
* Standardized error responses.
* Centralized exception handling.
* Request validation using Jakarta Bean Validation.
* JWT-based authentication for protected resources.
* Pagination for collection endpoints where appropriate.
* Clear separation between API, business logic, persistence, and infrastructure concerns.
* Conventional HTTP status codes.

---

# Authentication

The authentication system provides user registration, login, and JWT-based access control.

Authentication is implemented as reusable infrastructure that can support different application domains.

## POST `/api/auth/register`

Creates a new user account.

### Request

```json
{
  "username": "exampleuser",
  "email": "user@example.com",
  "password": "password123"
}
```

### Response

```json
{
  "success": true,
  "data": "Registration successful",
  "message": "User registered successfully",
  "timestamp": "2026-07-12T19:00:00"
}
```

---

## POST `/api/auth/login`

Authenticates a user and returns a JWT token.

### Request

```json
{
  "username": "exampleuser",
  "password": "password123"
}
```

### Response

```json
{
  "success": true,
  "data": "eyJhbGciOiJIUzI1...",
  "message": "Login successful",
  "timestamp": "2026-07-12T19:00:00"
}
```

---

# Authorization

Protected endpoints require:

```text
Authorization: Bearer <JWT_TOKEN>
```

JWT authentication is handled through the Spring Security infrastructure.

The security foundation includes:

* JWT token generation.
* JWT validation.
* Request authentication filtering.
* User identity extraction.
* Authentication context population.
* Protected endpoint authorization.

Authentication endpoints are excluded from JWT authentication requirements so users can register and authenticate before obtaining a token.

---

# Example Domain Endpoints

The current template includes a Fleet Management example domain to illustrate how business functionality can be implemented on top of the reusable API foundation.

The example endpoints demonstrate:

* CRUD operations.
* DTO-based request and response handling.
* Request validation.
* Pagination.
* Search functionality.
* Relationship-based workflows.
* Standardized success and error responses.

The example domain is not required for applications built from the template and can be replaced or removed when implementing a different business domain.

---

# Vehicle Endpoints

Base Route:

```text
/api/vehicles
```

Protected endpoints require JWT authentication.

---

## GET `/api/vehicles`

Retrieves vehicles using pagination.

### Query Parameters

| Parameter | Description                |
| --------- | -------------------------- |
| `page`    | Zero-based page number     |
| `size`    | Number of records per page |

Example:

```text
GET /api/vehicles?page=0&size=10
```

---

## GET `/api/vehicles/{id}`

Retrieves a single vehicle by ID.

### Response

Returns a `VehicleResponseDTO` within the standard success response structure.

### Errors

* `404` - Vehicle not found

---

## GET `/api/vehicles/search`

Searches vehicles using a query parameter and pagination.

Example:

```text
GET /api/vehicles/search?q=Ford&page=0&size=10
```

---

## POST `/api/vehicles`

Creates a new vehicle.

### Request

```json
{
  "title": "Truck 1",
  "vin": "1HGBH41JXMN109186",
  "licensePlate": "ABC123",
  "make": "Ford",
  "model": "F-150",
  "vehicleYear": 2020,
  "location": "Warehouse 1"
}
```

### Response

Returns a `VehicleResponseDTO` within the standard success response structure.

### Status

```text
201 Created
```

---

## PUT `/api/vehicles/{id}`

Updates an existing vehicle.

### Request

`VehicleRequestDTO`

### Errors

* `400` - Validation failure
* `404` - Vehicle not found

---

## DELETE `/api/vehicles/{id}`

Deletes an existing vehicle.

### Response

```json
{
  "success": true,
  "data": null,
  "message": "Vehicle deleted successfully"
}
```

---

# Maintenance Endpoints

Base Route:

```text
/api/maintenance
```

Protected endpoints require JWT authentication.

---

## GET `/api/maintenance`

Retrieves maintenance records using pagination.

---

## GET `/api/maintenance/{id}`

Retrieves a maintenance record by ID.

---

## GET `/api/maintenance/vehicle/{vehicleId}`

Retrieves maintenance history associated with a vehicle.

Supports pagination.

Example:

```text
GET /api/maintenance/vehicle/1?page=0&size=10
```

---

## POST `/api/maintenance`

Creates a maintenance record.

### Status

```text
201 Created
```

---

## PUT `/api/maintenance/{id}`

Updates an existing maintenance record.

---

## DELETE `/api/maintenance/{id}`

Deletes an existing maintenance record.

---

# Request Validation

Request DTOs use Jakarta Bean Validation to enforce input constraints at the API boundary.

Invalid requests are rejected before reaching business logic and are converted into the standardized error response format through centralized exception handling.

Typical validation failures return:

```text
400 Bad Request
```

Validation rules are defined on the request DTOs rather than directly on persistence entities, keeping API contracts separate from the database model.

---

# Pagination

Collection endpoints use Spring's pagination support through `Pageable`.

Pagination follows these conventions:

* `page` is zero-based.
* `size` specifies the requested number of records.
* Pagination parameters are supplied as query parameters.
* Collection endpoints can expose paginated results without requiring clients to retrieve the entire dataset.

Example:

```text
GET /api/vehicles?page=0&size=10
```

The same pagination approach is used for vehicle searches and maintenance history.

---

# Standard Success Response

Successful API responses use a consistent response envelope:

```json
{
  "success": true,
  "data": {},
  "message": "Operation completed successfully",
  "timestamp": "2026-07-12T19:00:00"
}
```

The `data` field contains the operation-specific response payload. For operations that do not return a resource, such as deletion, `data` may be `null`.

---

# Standard Error Response

Errors are handled centrally and returned using a standardized structure:

```json
{
  "status": 404,
  "error": "Resource Not Found",
  "message": "Vehicle not found",
  "path": "/api/vehicles/10",
  "timestamp": "2026-07-12T19:00:00",
  "traceId": "abc-123"
}
```

The global exception handling layer is responsible for translating application and validation exceptions into this consistent API format.

The `traceId` provides a correlation identifier that can be used to associate an API error with application logs.

---

# Common HTTP Status Codes

| Status | Meaning                                          |
| ------ | ------------------------------------------------ |
| `200`  | Successful request                               |
| `201`  | Resource created                                 |
| `400`  | Invalid request or validation failure            |
| `401`  | Authentication required or authentication failed |
| `403`  | Insufficient permissions                         |
| `404`  | Resource not found                               |
| `409`  | Data conflict                                    |
| `500`  | Unexpected server error                          |

The API uses conventional HTTP status codes to communicate the outcome of requests.

---

# API Testing

The API is verified through both unit and integration testing.

## Unit Testing

Unit tests validate individual components in isolation, including:

* Service-layer business logic.
* Authentication behavior.
* Security filtering.
* Controller behavior where appropriate.
* Validation and error scenarios.

## Integration Testing

Integration tests use Spring Boot Test, MockMvc, and PostgreSQL Testcontainers to validate application behavior across multiple layers.

Integration tests validate:

* Authentication workflows.
* Protected endpoint behavior.
* Request validation.
* Resource lifecycle operations.
* Standardized error responses.
* Database interactions.
* Repository and persistence behavior.

The integration tests provide reusable patterns and helpers that can be extended when additional resources are added to applications built from the template.

---

# Design Principles

The API design intentionally separates reusable infrastructure from example business functionality.

Reusable foundation components include:

* Authentication and authorization.
* JWT security.
* DTO-based API contracts.
* Request validation.
* Centralized exception handling.
* Standardized response formats.
* Pagination support.
* Logging and traceability.
* Unit and integration testing infrastructure.

The Fleet Management example domain serves as an implementation reference demonstrating how additional business domains can be built on top of the reusable foundation.

Applications built from the template should preserve these API conventions while adapting the domain-specific resources, DTOs, services, and persistence models to their own requirements.
