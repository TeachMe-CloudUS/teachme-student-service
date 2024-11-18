# teachme-student-service

This repository contains the TeachMe Student Service API, a microservice for managing student profiles, enrollments, and course completions in the TeachMe platform.

## Public Access

The service is deployed and accessible publicly at:

- **Public API URL:** https://teachme-student-service.info
- **Swagger UI Documentation:** https://teachme-student-service.info/swagger-ui/index.html

## Development Setup

### Prerequisites

Ensure you have the following installed:

- **Docker**

### Starting the Development Environment

The project includes Dockerfile.dev and docker-compose.dev.yml files for setting up the development environment.

1. Clone the repository
2. Start the development container with Docker Compose:

```bash
docker-compose -f docker-compose.dev.yml up --build
```

This command will build and start the service along with a MongoDB container if needed for local development.

### Running Tests

Tests include unit and integration tests, with Flapdoodle for embedded MongoDB integration testing.
Integration tests are excluded by mvn test by configuration, but can be run manually if needed.

To execute tests, run:

```bash
mvn test
```
