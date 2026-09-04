# User & Auth Service

Handles user registration, login, and issuing JWT tokens.

## Purpose
This is the entry point for anyone using PayTracker — you register here, log in here, and every other service uses the `userId` this service hands out to know whose data is whose.
## Port
8081

## Database
`user_db`

## What it does
- Register with username, email, and password
- Password is hashed with BCrypt before it's saved
- Login checks the password and returns a signed JWT token plus the user's profile
- Register with username, email, and password
- Password is hashed with BCrypt before it's saved
- Login checks the password and returns a signed JWT token plus the user's profile
- Basic user CRUD (view, update, delete)

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/users/register | Register a new user |
| POST | /api/users/login | Login, returns a token and user info |
| GET | /api/users/{id} | Get one user |
| GET | /api/users | Get all users |
| PUT | /api/users/{id} | Update email/currency |
| DELETE | /api/users/{id} | Delete a user |

## Run it
```bash
./mvnw spring-boot:run
```
```bash
./mvnw spring-boot:run
```

or via Docker:
```bash
docker compose up user-service
```

## Testing via Swagger

Open http://localhost:8081/swagger-ui.html

### Step 1: Register a user
Expand **POST /api/users/register** → **Try it out** → paste:
```json
{
  "username": "testuser",
  "email": "test@paytracker.com",
  "password": "pass123"
}
```
Execute. You should get a `201` response — note the returned `id`, you'll need it to test the other services.

Response:
```json
{
  "id": 7,
  "username": "testuser",
  "email": "test@paytracker.com",
  "currency": "INR",
  "createdAt": "2026-09-04T22:01:40.295363"
}
```

### Step 2: Log in
Expand **POST /api/users/login** -> **Try it out**:
```json
{
  "username": "testuser",
  "password": "pass123"
}
```
Execute. Should return `200` with a token and user object:
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "user": { "id": 7, "username": "testuser", "email": "test@paytracker.com", "currency": "INR", "createdAt": "..." }
}
```

### Step 3: Try the rest
Use the `id` from Step 1 to test `GET /api/users/{id}`, `GET /api/users`, `PUT /api/users/{id}`, and `DELETE /api/users/{id}` the same way — expand, Try it out, fill in the fields, Execute.

## Notes
The JWT is signed with a fixed secret key and lasts 24 hours. Right now the token isn't checked on every request across the other services — login and registration genuinely work, but the token isn't yet used as a security gate everywhere.
