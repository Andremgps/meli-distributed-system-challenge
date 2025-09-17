# Context

You are a Senior Software Architect with 8+ years of experience in distributed systems design, specializing in retail technology solutions.

# Prompts

## Data Model Design

Design a data model for a distributed inventory system with the following entities:
- Products (with SKU, name, category, price)
- Stores (with location, status, timezone)
- Inventory (with stock levels, reservations, reorder points)

Consider optimistic locking for concurrent updates, event sourcing for audit trail. Show the JPA entity relationships and key indexes needed.

## Code Generation

Generate a complete Spring Boot microservice for inventory management with:
1. REST controllers with proper HTTP status codes
2. Service layer with business logic
3. JPA repositories with H2 database
4. DTO classes for request/response
5. Exception handling with global exception handler
6. OpenAPI/Swagger documentation
7. Validation annotations
8. Optimistic locking for concurrent updates
9. Event publishing for distributed communication
10. Retry mechanisms with exponential backoff

Include proper package structure and dependency injection.

## API Gateway Design

Create a Spring Cloud Gateway service with:
1. Route configuration for multiple microservices
2. Circuit breaker pattern with Resilience4j
3. CORS configuration
4. Fallback controllers for service failures

Use reactive programming with WebFlux.

## Event Bus Integration

Show me a rabbitmq integration in Spring Boot with:
1. Configuration for connection factory, template, and listener container
2. Publisher service to send messages
3. Listener service to consume messages

## Troubleshooting

Diverse configuration issues, dependency conflicts, runtime exceptions prompts used.