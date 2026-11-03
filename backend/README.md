# FitLog API

Node.js/Express REST API for the FitLog Android app, backed by MongoDB Atlas.
See the top-level `/README.md` for the full project documentation — this
file covers just the backend.

## Endpoints

| Method & path | Auth required | Purpose |
|---|---|---|
| POST `/auth/register` | No | Create an account. Body: `{ name, email, passwordHash }` |
| POST `/auth/login` | No | Log in. Body: `{ email, passwordHash }` |
| POST `/auth/verify` | No | (POE phase) Verify a Firebase SSO ID token |
| GET `/users/me` | Yes | Fetch the logged-in user's profile/settings |
| PATCH `/users/me` | Yes | Update settings: `{ preferredLanguage?, notificationsEnabled? }` |
| GET `/workouts?since=<ms>` | Yes | Fetch workouts changed since a timestamp (sync pull) |
| POST `/workouts` | Yes | Create/update a workout by client-generated id (sync push) |
| PATCH `/workouts/:id` | Yes | Update a workout |
| DELETE `/workouts/:id` | Yes | Delete a workout |
| GET `/exercises` | Yes | Master exercise catalogue |
| POST `/devices/register` | Yes | Register an FCM token (POE phase) |

Authenticated requests need `Authorization: Bearer <token>`, where `<token>`
is the value returned by `/auth/register` or `/auth/login`.

## Local development

```bash
npm install
cp .env.example .env   # then fill in MONGODB_URI and JWT_SECRET
npm run dev
node seed.js            # populate the exercise catalogue once
```

## Deploying to Render

1. Push this repository to GitHub (already done, since the whole project
   lives in one repo).
2. In Render: **New → Web Service**, connect this repo, set the root
   directory to `backend`.
3. Build command: `npm install`. Start command: `npm start`.
4. Add environment variables `MONGODB_URI` and `JWT_SECRET` under
   Render's "Environment" tab (same values as your local `.env`).
5. Once deployed, copy the Render URL into `API_BASE_URL` in
   `app/build.gradle.kts` (release build type) and rebuild the app.

## Database

MongoDB Atlas (free M0 tier is enough for this project). Create a cluster,
add a database user, allow access from anywhere (0.0.0.0/0) or Render's IP
range under Network Access, then copy the connection string from
**Database → Connect → Drivers** into `MONGODB_URI`.
