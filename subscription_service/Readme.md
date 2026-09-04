# Subscription Service

Tracks recurring subscriptions like Netflix or Spotify — billing cycle, renewal date, and how the price has changed over time.

## Port
8083

## Database
`subscription_db`

## What it does
- Add and manage subscriptions with a billing cycle (monthly/yearly) and renewal date
- Every time a subscription's price changes, it gets logged as a new entry instead of just overwriting the old price — so you can see the full price history
- Checks for subscriptions renewing soon and sends an alert through RabbitMQ

## Endpoints

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
| POST | /api/subscriptions/check-renewals | Check for renewals in the next 3 days and send alerts |

## Swagger
http://localhost:8083/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker:
```bash
docker compose up subscription-service
```

## Notes
`check-renewals` has to be called manually right now instead of running automatically on a schedule — a scheduled version would use Spring's `@Scheduled` to run this once a day.

Messages sent to RabbitMQ are sent as JSON instead of the default Java format, since the default format doesn't allow `LocalDate` fields for security reasons and kept throwing errors.