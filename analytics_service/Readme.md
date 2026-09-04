# Analytics & Budget Service

Works out spending trends, budget usage, and spending velocity. Doesn't store any transaction or subscription data itself — it asks Ledger Service and Subscription Service for that whenever it needs it.

## Purpose
This is the "intelligence" layer of the app — it turns raw transactions and subscriptions into things like "you've used 60% of your Food budget but only 30% of the month has passed."

## Port
8084

## Database
`analytics_db` — only stores Budgets. Everything else is calculated fresh each time it's asked.

## How it talks to other services
Uses Feign (a normal REST call under the hood) to reach:
- Ledger Service — to get transactions for a date range
- Subscription Service — to get active subscriptions

## Spending velocity
For each budget, it compares how much of the budget has been used against how much of the month has already gone by. If the spending percentage is higher than the month percentage, that category gets flagged as overspending — the goal is catching it early instead of finding out at the end of the month.

## API Endpoints

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

## Run it
```bash
./mvnw spring-boot:run
```

## Testing via Swagger

Open http://localhost:8084/swagger-ui.html

This service needs data in Ledger Service and Subscription Service first — add a few transactions and a subscription through their Swagger pages before testing here, using the same `userId`.

### Step 1: Set a budget
Expand **POST /api/budgets** -> **Try it out**:
```json
{
  "userId": 7,
  "category": "Food",
  "monthlyLimit": 5000
}
```
Execute. Should return `201` with the created budget.

### Step 2: Check budget status
Expand **GET /api/analytics/user/{userId}/budget-status/{category}** -> enter `userId = 7`, `category = Food` -> Execute:
```json
{
  "category": "Food",
  "monthlyLimit": 5000.00,
  "spentSoFar": 500.00,
  "percentageUsed": 10.0,
  "percentageOfMonthPassed": 13.3,
  "overspending": false
}
```

### Step 3: Check the full dashboard
Expand **GET /api/analytics/user/{userId}/dashboard** -> enter `userId = 7` -> Execute. Returns income/expense totals, subscription cost, every budget's status, and the last 6 months of spending — the same endpoint the frontend dashboard uses.

### Step 4: Try the trend endpoint on its own
**GET /api/analytics/user/{userId}/trend** — just the 6-month spending breakdown by itself.

## Notes
Split the code into a couple of smaller pieces to keep it easy to follow: `BudgetService` just handles saving/reading budgets, `AnalyticsService` fetches data from the other services, and `SpendingCalculator` is a small class that only does the actual math. Kept each piece doing just one job so it's easier to explain.
