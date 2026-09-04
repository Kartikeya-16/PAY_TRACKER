# Eureka Server

Just the service registry. Every other service registers itself here on startup, and the Gateway + Feign clients use this to find services by name instead of hardcoding ports.

## Port
8761

## Dashboard
http://localhost:8761 — shows all currently registered services

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker from the root folder:
```bash
docker compose up eureka-server
```

## Notes
This one was pretty straightforward, no real issues. `register-with-eureka` and `fetch-registry` are both set to false here since this app is the registry itself, not a client of it.