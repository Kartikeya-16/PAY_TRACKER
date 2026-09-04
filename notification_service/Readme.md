# Notification Service

Listens for renewal alerts from Subscription Service and saves them as notifications.

## Port
8085

## Database
`notification_db`

## How it works
Subscription Service sends a message to a RabbitMQ queue whenever a subscription is about to renew. This service listens to that queue in the background — whenever a message shows up, it automatically saves it as a notification and prints it to the console (standing in for actually sending an email, which is outside the scope of this project).

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/notifications/user/{userId} | Get all notifications for a user |
| GET | /api/notifications | Get all notifications (for testing) |

There's no endpoint to create a notification directly — they only get created by the RabbitMQ listener picking up a message.

## Swagger
http://localhost:8085/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker:
```bash
docker compose up notification-service
```

## Notes
This is the only part of the whole project that uses RabbitMQ instead of a normal REST call — everything else talks directly service-to-service. Used RabbitMQ here specifically because a notification doesn't need to be handled immediately, unlike something like Analytics needing data from Ledger right away.