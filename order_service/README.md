# Order Service

Handles order creation and status tracking. This is the service that actually talks to another service directly — it calls Product Service using OpenFeign to check stock and get the real price before creating the order.

## Port
8083

## Database
`order_db` (MySQL, on AWS RDS)

## How it talks to Product Service
Uses a Feign client (`ProductClient`) that calls `GET /api/products/{id}` on Product Service. Feign uses the service name registered in Eureka (`product-service`) to find it, so we don't hardcode any URL/port.

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/orders | Create order (checks product price/stock first) |
| GET | /api/orders/{id} | Get one order |
| GET | /api/orders | Get all orders |
| GET | /api/orders/user/{userId} | Get orders for a user |
| PUT | /api/orders/{id}/status | Update order status |
| PUT | /api/orders/{id}/cancel | Cancel order |
| DELETE | /api/orders/{id} | Delete order |

## Swagger
http://localhost:8083/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker from the root folder:
```bash
docker compose up order-service
```

## Notes
Had an issue early on where Feign couldn't call Product Service because `product_service` (with underscore) isn't a valid hostname. Had to change `spring.application.name` to use hyphens (`product-service`) instead. Only mattered for services actually called via Feign.