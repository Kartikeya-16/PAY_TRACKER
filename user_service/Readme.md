## Testing via Swagger

Once the service is running, open:

👉 http://localhost:8081/swagger-ui.html

### Step 1: Register a user
Expand **POST /api/users/register** → click **Try it out** → paste this into the request body:
```json
{
  "username": "testuser",
  "email": "test@paytracker.com",
  "password": "pass123"
}
```
Click **Execute**. You should get a `201` response with the created user's `id` — note this down, you'll need it to test the other services.

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
Expand **POST /api/users/login** → **Try it out**:
```json
{
  "username": "testuser",
  "password": "pass123"
}
```
Execute. Should return a `200` with a token and the user object:
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9...",
  "user": { "id": 7, "username": "testuser", ... }
}
```

### Step 3: Try the other endpoints
Use the `id` from Step 1 to test `GET /api/users/{id}`, `PUT /api/users/{id}`, and `DELETE /api/users/{id}` the same way — expand, Try it out, fill in the `id` path parameter, Execute.