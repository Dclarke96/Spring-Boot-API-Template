# Deployment Guide

## Overview

This guide describes the deployment requirements and recommended practices for applications built from the Spring Boot API Template foundation.

The application is designed around externalized configuration, environment-specific Spring profiles, containerized deployment, and health monitoring to support modern cloud hosting platforms.

The deployment approach focuses on keeping infrastructure configuration separate from application code while providing a consistent foundation for local, staging, and production environments.

---

# Production Requirements

Before deploying the application, ensure the following requirements are available.

## Java

- Java 17+

## Database

- PostgreSQL database instance
- Database user with appropriate permissions
- Network access from the application environment

## Build Tool

- Gradle
- Gradle Wrapper (recommended)

## Container Runtime (Optional)

- Docker
- Docker Compose

---

# Spring Profiles

The application supports multiple runtime profiles.

| Profile | Purpose |
|----------|---------|
| local | Local development |
| test | Automated testing |
| docker | Docker-based local development |
| prod | Production deployment |

Production deployments should use:

```text
SPRING_PROFILES_ACTIVE=prod