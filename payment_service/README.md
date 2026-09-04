# Payment Service

Handles payments for orders. Calls Order Service (via Feign) to get the order total before recording the payment, so the amount isn't just trusted from the request.

## Port
8084

## Database
`payment_db` (MySQL, on AWS RDS)

## How it talks to Order Service
Uses a Feign client (`OrderClient`) that calls `GET /api/orders/{id}` on Order Service to fetch the real order total and user ID.

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/payments | Process payment for an order |
| GET | /api/payments/{id} | Get one payment |
| GET | /api/payments/order/{orderId} | Get payment for an order |
| GET | /api/payments | Get all payments |
| GET | /api/payments/user/{userId} | Get payments for a user |
| PUT | /api/payments/{id}/refund | Refund a payment |

## Swagger
http://localhost:8084/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker from the root folder:
```bash
docker compose up payment-service
```

## Notes
Payment is "simulated" — it always succeeds and just generates a transaction reference. No real payment gateway integration since that's outside the scope of this assignment.