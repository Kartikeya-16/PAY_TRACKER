# PayTracker

PayTracker is a microservices-based personal finance and subscription management platform, built for my MCA Phase 2 project.

The idea is to combine expense tracking, income tracking, subscriptions and order/payment flow into one system, built using Spring Boot microservices instead of a single monolithic app.

## Services in this project

| Service | Port | What it does |
|---|---|---|
| Eureka Server | 8761 | Service registry, so all services can find each other |
| API Gateway | 8080 | Single entry point, routes requests to the right service |
| User Service | 8081 | Handles user registration and login |
| Product Service | 8082 | Manages products (CRUD) |
| Order Service | 8083 | Handles orders, talks to Product Service to check price/stock |
| Payment Service | 8084 | Handles payments, talks to Order Service to get order total |

## Tech used

- Java 17, Spring Boot
- Spring Data JPA / Hibernate
- MySQL (hosted on AWS RDS)
- Eureka for service discovery
- Spring Cloud Gateway for routing
- OpenFeign for service-to-service calls
- Swagger/OpenAPI for API docs
- Docker + Docker Compose

## How everything talks to each other

Client -> API Gateway (8080) -> User / Product / Order / Payment Service
Order Service -> Product Service (Feign)
Payment Service -> Order Service (Feign)



Each service has its own database (`user_db`, `product_db`, `order_db`, `payment_db`) so they don't share tables directly.

## How to run it

### Option 1: Docker (easiest)

Make sure Docker Desktop is running, then from this folder:

```bash
docker compose up -d
```

Check everything started:
```bash
docker compose ps
```

To stop everything:
```bash
docker compose down
```

### Option 2: Run manually (for development)

Start in this order (each in its own terminal / IntelliJ run config):
1. Eureka Server
2. API Gateway
3. User Service, Product Service, Order Service, Payment Service (order doesn't matter for these 4)

## Checking it works

- Eureka dashboard: http://localhost:8761 (should show all 5 services once they're up)
- Test through gateway: `curl http://localhost:8080/api/products`
- Each service also has its own Swagger page, see the individual READMEs

## Notes

- I originally started with local MySQL but moved everything to AWS RDS so all services share one DB instance (just different schemas).
- Had a lot of trouble getting the API Gateway working with the newer Spring Cloud version — turns out the property names changed (`spring.cloud.gateway.routes` became `spring.cloud.gateway.server.webflux.routes`) and the actual dependency name changed too (`spring-cloud-starter-gateway` -> `spring-cloud-starter-gateway-server-webflux`). Took a while to figure out.
- Docker was also tricky because some services built as `.war` instead of `.jar` even though I didn't set that anywhere, so I had to adjust the Dockerfile to accept either.