# API Design

## Overview

The Spring Boot API Template provides a production-oriented REST API foundation with authentication, authorization, standardized responses, validation, exception handling, and example domain endpoints.

The current implementation includes an example domain to demonstrate API patterns and architectural practices.

The API follows these principles:

* JSON request and response payloads.
* DTO-based API contracts.
* Consistent response envelopes.
* Centralized error handling.
* JWT-based authentication for protected resources.
* Clear separation between API, business logic, persistence, and infrastructure concerns.

---

# Authentication

The authentication system provides user registration, login, and JWT-based access control.

Authentication is designed as a reusable foundation that can support different application domains.

---

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

```
Authorization: Bearer <JWT_TOKEN>
```

JWT authentication is handled through Spring Security infrastructure.

The security foundation includes:

* JWT token generation.
* JWT validation.
* Request authentication filtering.
* User identity extraction.
* Protected endpoint authorization.

---

# Example Domain Endpoints

The current template includes an example domain to illustrate how additional business functionality can be added on top of the foundation.

These endpoints demonstrate:

* CRUD operations.
* DTO-based request and response handling.
* Validation.
* Pagination.
* Search functionality.
* Relationship-based workflows.

---

# Vehicle Endpoints

Base Route:

```
/api/vehicles
```

---

## GET `/api/vehicles`

Retrieves vehicles with pagination.

### Query Parameters

| Parameter | Description                |
| --------- | -------------------------- |
| page      | Page number                |
| size      | Number of records per page |

Example:

```
GET /api/vehicles?page=0&size=10
```

---

## GET `/api/vehicles/{id}`

Retrieves a single vehicle.

### Response

Returns:

`VehicleResponseDTO`

### Errors

* 404 - Vehicle not found

---

## GET `/api/vehicles/search`

Searches vehicles.

Example:

```
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

Returns:

`VehicleResponseDTO`

Status:

```
201 Created
```

---

## PUT `/api/vehicles/{id}`

Updates an existing vehicle.

### Request

`VehicleRequestDTO`

### Errors

* 404 - Vehicle not found
* 400 - Validation failure

---

## DELETE `/api/vehicles/{id}`

Deletes a vehicle.

### Response

Returns:

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

```
/api/maintenance
```

---

## GET `/api/maintenance`

Retrieves maintenance records with pagination.

---

## GET `/api/maintenance/{id}`

Retrieves a maintenance record by ID.

---

## GET `/api/maintenance/vehicle/{vehicleId}`

Retrieves maintenance history associated with a vehicle.

Supports pagination.

Example:

```
GET /api/maintenance/vehicle/1?page=0&size=10
```

---

## POST `/api/maintenance`

Creates a maintenance record.

Status:

```
201 Created
```

---

## PUT `/api/maintenance/{id}`

Updates an existing maintenance record.

---

## DELETE `/api/maintenance/{id}`

Deletes a maintenance record.

---

# Standard Success Response

All successful API responses follow:

```json
{
  "success": true,
  "data": {},
  "message": "Operation completed successfully",
  "timestamp": "2026-07-12T19:00:00"
}
```

---

# Standard Error Response

All errors follow:

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

---

# Common HTTP Status Codes

| Status | Meaning                  |
| ------ | ------------------------ |
| 400    | Invalid request          |
| 401    | Authentication required  |
| 403    | Insufficient permissions |
| 404    | Resource not found       |
| 409    | Data conflict            |
| 500    | Unexpected server error  |

---

# Design Notes

The API design intentionally separates reusable infrastructure from example business functionality.

Reusable foundation components include:

* Authentication and authorization.
* JWT security.
* DTO-based API contracts.
* Validation framework.
* Exception handling.
* Standardized response formats.
* Logging and traceability.

The example endpoints serve as an implementation reference demonstrating how additional domains can be built on top of the foundation.