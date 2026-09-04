# Ledger Service

Tracks income and expenses — this is the main day-to-day money tracker.

## Port
8082

## Database
`ledger_db`

## What it does
- Log a transaction as either income or expense
- Add a category, payment method, and tags to each one
- Filter transactions by type, category, or date range

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/transactions | Add a transaction |
| GET | /api/transactions/{id} | Get one transaction |
| GET | /api/transactions/user/{userId} | Get all transactions for a user |
| GET | /api/transactions/user/{userId}/type/{type} | Filter by income or expense |
| GET | /api/transactions/user/{userId}/category/{category} | Filter by category |
| GET | /api/transactions/user/{userId}/range?start=&end= | Filter by date range |
| PUT | /api/transactions/{id} | Update a transaction |
| DELETE | /api/transactions/{id} | Delete a transaction |

## Swagger
http://localhost:8082/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker:
```bash
docker compose up ledger-service
```

## Notes
This service is used by the Analytics service to work out spending totals and trends — it doesn't know Analytics exists, Analytics just calls its normal API.