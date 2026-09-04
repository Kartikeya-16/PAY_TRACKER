# Ledger Service

Tracks income and expenses — the main day-to-day money tracker.

## Purpose
This is where everyday spending and income get logged — groceries, salary, transport, whatever. Every entry belongs to a user (via `userId`) and can be categorized and tagged.

## Port
8082

## Database
`ledger_db`

## What it does
- Log a transaction as either income or expense
- Add a category, payment method, and tags to each one
- Filter transactions by type, category, or date range

## API Endpoints

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

## Run it
```bash
./mvnw spring-boot:run
```

## Testing via Swagger

Open http://localhost:8082/swagger-ui.html

You'll need a `userId` first — register one through User Service's Swagger (http://localhost:8081/swagger-ui.html) if you haven't already.

### Step 1: Add a transaction
Expand **POST /api/transactions** -> **Try it out**:
```json
{
  "userId": 7,
  "type": "EXPENSE",
  "amount": 500,
  "category": "Food",
  "paymentMethod": "UPI",
  "description": "Lunch",
  "tags": "college",
  "transactionDate": "2026-09-04"
}
```
Execute. Should return `201` with the created transaction.

### Step 2: Fetch it back
Expand **GET /api/transactions/user/{userId}** -> Try it out -> enter `userId = 7` -> Execute. You should see the transaction you just created.

### Step 3: Try filtering
- **GET /api/transactions/user/{userId}/type/{type}** — try `type = EXPENSE` or `INCOME`
- **GET /api/transactions/user/{userId}/category/{category}** — try `category = Food`
- **GET /api/transactions/user/{userId}/range** — enter `start=2026-09-01`, `end=2026-09-30`

### Step 4: Update and delete
Take the `id` from Step 1 -> try **PUT /api/transactions/{id}** (send the same body with a different amount) and **DELETE /api/transactions/{id}**.

## Testing via Swagger

Open http://localhost:8082/swagger-ui.html

You'll need a `userId` first — register one through User Service's Swagger (http://localhost:8081/swagger-ui.html) if you haven't already.

### Step 1: Add a transaction
Expand **POST /api/transactions** → **Try it out**:
```json
{
  "userId": 7,
  "type": "EXPENSE",
  "amount": 500,
  "category": "Food",
  "paymentMethod": "UPI",
  "description": "Lunch",
  "tags": "college",
  "transactionDate": "2026-09-04"
}
```
Execute. Should return `201` with the created transaction.

### Step 2: Fetch it back
Expand **GET /api/transactions/user/{userId}** → Try it out → enter `userId = 7` → Execute. You should see the transaction you just created.

### Step 3: Try filtering
- **GET /api/transactions/user/{userId}/type/{type}** — try `type = EXPENSE` or `INCOME`
- **GET /api/transactions/user/{userId}/category/{category}** — try `category = Food`
- **GET /api/transactions/user/{userId}/range** — enter `start=2026-09-01`, `end=2026-09-30`

### Step 4: Update and delete
Take the `id` from Step 1 → try **PUT /api/transactions/{id}** (send the same body with a different amount) and **DELETE /api/transactions/{id}**.

## Notes
This service is used by Analytics Service to work out spending totals and trends — it doesn't know Analytics exists, Analytics just calls its normal API.
