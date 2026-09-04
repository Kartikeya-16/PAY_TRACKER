# PayTracker

PayTracker is a microservices-based personal finance and subscription management platform

The idea is to bring together two things that are usually tracked separately — everyday income/expense logging and recurring subscription management — into one system, along with an analytics service that works out spending trends and budget usage.

## Services in this project

| Service | Port | What it does |
|---|---|---|
| Eureka Server | 8761 | Service registry, so services can find each other |
| API Gateway | 8080 | Single entry point, routes requests to the right service |
| User & Auth Service | 8081 | Registration, login, JWT tokens |
| Ledger Service | 8082 | Income and expense transactions |
| Subscription Service | 8083 | Recurring subscriptions and price history |
| Analytics & Budget Service | 8084 | Budgets, spending trends, spending velocity |

## Tech used

- Java 17, Spring Boot
- Spring Data JPA / Hibernate
- MySQL (hosted on AWS RDS)
- Eureka for service discovery
- Spring Cloud Gateway for routing requests
- OpenFeign for services calling each other directly
- JWT for login tokens

## How everything talks to each other

Client -> API Gateway (8080) -> User / Ledger / Subscription / Analytics
Analytics -> Ledger (direct call)
Analytics -> Subscription (direct call)


Analytics Service doesn't store transaction or subscription data itself — it calls Ledger Service and Subscription Service directly (using Feign) whenever it needs that data, instead of keeping its own copy.

Each service has its own database (`user_db`, `ledger_db`, `subscription_db`, `analytics_db`) so no service is directly touching another service's tables.

## How to run it

Start in this order, each in its own terminal / IntelliJ run configuration:

1. Eureka Server
2. API Gateway
3. User & Auth Service, Ledger Service, Subscription Service, Analytics & Budget Service (order doesn't matter between these 4)

```bash
./mvnw spring-boot:run
```
(run this inside each service's folder)

## Quick end-to-end test through Swagger

This walks through the whole system using one user, start to finish. See each service's own README for more detail on that service specifically.

1. **Register a user** — http://localhost:8081/swagger-ui.html -> `POST /api/users/register` -> note the returned `id`
2. **Log in** — `POST /api/users/login` -> confirms you get back a token
3. **Add a transaction** — http://localhost:8082/swagger-ui.html -> `POST /api/transactions` with your `userId`
4. **Add a subscription** — http://localhost:8083/swagger-ui.html -> `POST /api/subscriptions` with your `userId`
5. **Set a budget** — http://localhost:8084/swagger-ui.html -> `POST /api/budgets` with your `userId`
6. **Check the dashboard** — `GET /api/analytics/user/{userId}/dashboard` -> should show real numbers pulled from steps 3-5

## Checking it works

- Eureka dashboard: http://localhost:8761 — should show all 5 services (Gateway + 4 microservices) registered
- Test through the gateway: `curl http://localhost:8080/api/transactions/user/7`

## Notes

- Had some trouble with Spring Cloud Gateway's newer version — the dependency name changed (`spring-cloud-starter-gateway-server-webflux` instead of the old name) and the property names changed too (`spring.cloud.gateway.server.webflux.routes` instead of `spring.cloud.gateway.routes`).
- Service names registered with Eureka can't have underscores in them, so even though my project folders use underscores (`user_service`), the actual `spring.application.name` in each service's properties file uses hyphens (`user-service`).
