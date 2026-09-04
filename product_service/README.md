# Product Service

Stores product info — name, price, category, stock quantity.

## Port
8082

## Database
`product_db` (MySQL, on AWS RDS)

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/products | Add new product |
| GET | /api/products/{id} | Get one product |
| GET | /api/products | Get all products |
| GET | /api/products/category/{category} | Filter by category |
| GET | /api/products/search?name= | Search by name |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |

## Swagger
http://localhost:8082/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker from the root folder:
```bash
docker compose up product-service
```

## Notes
This service is called by Order Service (via Feign) whenever an order is created, to check the current price and stock.