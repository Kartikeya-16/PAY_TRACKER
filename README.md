# PayTracker

PayTracker is a microservices-based personal finance and subscription management platform, built for my MCA Phase 2 project.

The idea is to bring together two things that are usually tracked separately — everyday income/expense logging and recurring subscription management — into one system. On top of that, there's an analytics service that works out spending trends and budget usage, and a notification service that alerts the user before a subscription renews or a budget is about to be exceeded.

## Services in this project

| Service | Port | What it does |
|---|---|---|
| Eureka Server | 8761 | Service registry, so services can find each other |
| API Gateway | 8080 | Single entry point, routes requests to the right service |
| User & Auth Service | 8081 | Registration, login, JWT tokens |
| Ledger Service | 8082 | Income and expense transactions |
| Subscription Service | 8083 | Recurring subscriptions, price history, sends renewal alerts |
| Analytics & Budget Service | 8084 | Budgets, spending trends, spending velocity |
| Notification Service | 8085 | Receives renewal alerts and stores them |
| RabbitMQ | 5672 / 15672 | Message queue used for renewal notifications |

## Tech used

- Java 17, Spring Boot
- Spring Data JPA / Hibernate
- MySQL (hosted on AWS RDS)
- Eureka for service discovery
- Spring Cloud Gateway for routing requests
- OpenFeign for services calling each other directly
- RabbitMQ for sending renewal notifications
- JWT for login tokens
- Docker + Docker Compose

## How everything talks to each other
Client -> API Gateway (8080) -> User / Ledger / Subscription / Analytics / Notification
Analytics -> Ledger (direct call)
Analytics -> Subscription (direct call)
Subscription -> RabbitMQ -> Notification


Most services talk to each other directly using Feign (basically a normal REST call). Only Subscription and Notification use RabbitMQ between them, since a renewal alert doesn't need an instant response the way Analytics needs an instant answer from Ledger.

Each service has its own database (`user_db`, `ledger_db`, `subscription_db`, `analytics_db`, `notification_db`) so no service is directly touching another service's tables.

## How to run it

### Option 1: Docker (easiest)

```bash
docker compose up -d
```

Check everything started:
```bash
docker compose ps
```

Stop everything:
```bash
docker compose down
```

### Option 2: Run manually

Start in this order: Eureka Server → RabbitMQ → API Gateway → the 5 microservices (order doesn't matter between these 5).

## Checking it works

- Eureka dashboard: http://localhost:8761
- RabbitMQ dashboard: http://localhost:15672 (guest/guest)
- Test through the gateway: `curl http://localhost:8080/api/transactions/user/5`

## Notes

- Had some trouble with Spring Cloud Gateway's newer version — the dependency name changed (`spring-cloud-starter-gateway-server-webflux` instead of the old name) and the property names changed too (`spring.cloud.gateway.server.webflux.routes` instead of `spring.cloud.gateway.routes`).
- Service names registered with Eureka can't have underscores in them, so even though my project folders use underscores (`user_service`), the actual `spring.application.name` uses hyphens (`user-service`).
- RabbitMQ's default message format doesn't like Java's `LocalDate` type for security reasons — had to switch to sending messages as JSON instead.