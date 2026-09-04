# User & Auth Service

Handles user registration, login, and issuing JWT tokens.

## Port
8081

## Database
`user_db`

## What it does
- Register with username, email, and password
- Password is hashed with BCrypt before it's saved
- Login checks the password and returns a signed JWT token plus the user's profile
- Basic user CRUD

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/users/register | Register a new user |
| POST | /api/users/login | Login, returns a token and user info |
| GET | /api/users/{id} | Get one user |
| GET | /api/users | Get all users |
| PUT | /api/users/{id} | Update email/currency |
| DELETE | /api/users/{id} | Delete a user |

## Swagger
http://localhost:8081/swagger-ui.html

## Run it
```bash
./mvnw spring-boot:run
```
or via Docker:
```bash
docker compose up user-service
```

## Notes
The JWT is signed with a fixed secret key. Token lasts 24 hours. Right now the token isn't checked on every request across the other services — login and registration genuinely work, but the token isn't yet used as a security gate everywhere.