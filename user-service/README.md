# User Service

Handles user registration and login for PayTracker.

## Port
8081

## Database
`user_db` (MySQL, on AWS RDS)

## What it does
- Users can register with username/email/password
- Password is hashed using BCrypt before saving
- Basic CRUD on users

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/users/register | Register new user |
| POST | /api/users/login | Login |
| GET | /api/users/{id} | Get one user |
| GET | /api/users | Get all users |
| PUT | /api/users/{id} | Update user (email/currency) |
| DELETE | /api/users/{id} | Delete user |

## Swagger
http://localhost:8081/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker from the root folder:
```bash
docker compose up user-service
```

## Notes
Password hashing uses `spring-security-crypto` (just the BCryptPasswordEncoder bean) instead of pulling in full Spring Security, since we don't need the whole auth framework for this project.