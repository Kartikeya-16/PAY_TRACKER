# Subscription Service

Tracks recurring subscriptions like Netflix or Spotify — billing cycle, renewal date, and how the price has changed over time.

## Purpose
This service treats subscriptions as their own thing instead of just another transaction — with a start date, a billing cycle, a renewal date, and a full history of price changes.

## Port
8083

## Database
`subscription_db`

## What it does
- Add and manage subscriptions with a billing cycle (monthly/yearly) and renewal date
- Every time a subscription's price changes, it's logged as a new entry instead of overwriting the old price — so the full price history is visible
- Can check for subscriptions renewing soon

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/subscriptions | Add a subscription |
| GET | /api/subscriptions/{id} | Get one subscription (includes price history) |
| GET | /api/subscriptions/user/{userId} | Get all subscriptions for a user |
| GET | /api/subscriptions/user/{userId}/active | Get only active subscriptions |
| GET | /api/subscriptions/renewing?start=&end= | Get subscriptions renewing in a date range |
| PUT | /api/subscriptions/{id}/price | Update the price (logs a new price history entry) |
| PUT | /api/subscriptions/{id}/renew | Move to the next renewal date |
| PUT | /api/subscriptions/{id}/cancel | Cancel a subscription |
| DELETE | /api/subscriptions/{id} | Delete a subscription |

## Run it
```bash
./mvnw spring-boot:run
```

## Testing via Swagger

Open http://localhost:8083/swagger-ui.html

### Step 1: Add a subscription
Expand **POST /api/subscriptions** -> **Try it out**:
```json
{
  "userId": 7,
  "name": "Netflix",
  "currentPrice": 649,
  "billingCycle": "MONTHLY",
  "startDate": "2026-01-05",
  "nextRenewalDate": "2026-09-07"
}
```
Execute. Should return `201` with the subscription, including a `priceHistory` array already containing the starting price.

### Step 2: Update the price
Take the `id` from Step 1 -> expand **PUT /api/subscriptions/{id}/price** -> Try it out -> enter the `id` -> body:
```json
{ "newPrice": 799 }
```
Execute, then check **GET /api/subscriptions/{id}** again — `priceHistory` should now have two entries showing both prices.

### Step 3: Try the other actions
- **PUT /api/subscriptions/{id}/renew** — advances the renewal date
- **PUT /api/subscriptions/{id}/cancel** — sets status to CANCELLED
- **GET /api/subscriptions/user/{userId}/active** — only shows ACTIVE subscriptions
- **DELETE /api/subscriptions/{id}** — removes it entirely

## Notes
This service is used by Analytics Service (to get subscription costs) the same way Ledger Service is — a direct API call, no shared database.
