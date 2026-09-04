# API Gateway

Single entry point for the whole system. Instead of the frontend (or Postman) hitting each service on its own port, everything goes through the gateway on port 8080, and it routes to the right service based on the URL path.

## Port
8080

## Routes

| Path | Goes to |
|---|---|
| /api/users/** | User Service |
| /api/products/** | Product Service |
| /api/orders/** | Order Service |
| /api/payments/** | Payment Service |

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker from the root folder:
```bash
docker compose up api-gateway
```

## Notes
This one gave me the most trouble in the whole project. It kept returning 404 for every route even though everything looked correct. Turned out to be two separate problems:
1. The `pom.xml` had leftover `spring-boot-starter-web` (servlet) dependency alongside the Gateway dependency, which made it silently run as a normal Tomcat app instead of the reactive Gateway.
2. Even after fixing that, the route properties weren't loading because this version of Spring Cloud Gateway changed the property prefix from `spring.cloud.gateway.routes` to `spring.cloud.gateway.server.webflux.routes`. Also the dependency itself is now called `spring-cloud-starter-gateway-server-webflux` instead of the old `spring-cloud-starter-gateway`.

Once both were fixed, routing worked properly.