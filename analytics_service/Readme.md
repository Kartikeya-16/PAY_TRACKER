# Analytics & Budget Service

Works out spending trends, budget usage, and spending velocity. Doesn't store any transaction or subscription data itself — it asks Ledger Service and Subscription Service for that whenever it needs it.

## Port
8084

## Database
`analytics_db` — only stores Budgets. Everything else is calculated fresh each time it's asked.

## How it talks to other services
Uses Feign to call:
- Ledger Service, to get transactions for a date range
- Subscription Service, to get active subscriptions

Instead of storing a copy of that data itself, it just asks the other service directly whenever someone asks for analytics.

## Spending velocity
For each budget, it compares how much of the budget has been used against how much of the month has already gone by. If the spending percentage is higher than the month percentage, that category gets flagged as overspending — the idea being to catch it early instead of finding out at the end of the month.

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/budgets | Create or update a budget for a category |
| GET | /api/budgets/user/{userId} | Get all budgets for a user |
| DELETE | /api/budgets/{id} | Delete a budget |
| GET | /api/analytics/user/{userId}/budget-status | Status of every budget |
| GET | /api/analytics/user/{userId}/budget-status/{category} | Status of one category |
| GET | /api/analytics/user/{userId}/trend | Spending for the last 6 months |
| GET | /api/analytics/user/{userId}/subscription-cost | Total monthly subscription cost |
| GET | /api/analytics/user/{userId}/dashboard | Everything combined in one call |

## Swagger
http://localhost:8084/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker:
```bash
docker compose up analytics-service
```

## Notes
Split the code into a couple of smaller pieces to keep it easy to follow: `BudgetService` just handles saving/reading budgets, `AnalyticsService` fetches data from the other services, and `SpendingCalculator` is a small class that only does the actual math (adding things up, working out percentages). Kept each piece doing just one job so it's easier to explain.