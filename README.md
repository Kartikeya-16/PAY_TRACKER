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
- Docker + Docker Compose (optional — can also run everything manually)

## How everything talks to each other
