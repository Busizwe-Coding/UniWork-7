# FitLog — Kotlin Android Workout / Fitness Log

<p align="center">
  <img src="docs/images/logo.png" width="120" alt="FitLog logo" />
</p>

FitLog is a native Android app, built in Kotlin with Jetpack Compose, that lets
gym-goers log strength-training workouts — exercises, sets, reps and weight —
whether or not they have an internet connection, and keeps that data
synchronised across devices through a personal account. This repository
contains both the Android client (`/app`) and the REST API backend it talks
to (`/backend`).

This README **is** the documentation report required for submission: it
covers the app's purpose, its design considerations, and how GitHub and
GitHub Actions are used in the project.

## Colour palette

<p align="center">
  <img src="docs/images/colour_palette.png" width="400" alt="FitLog colour palette" />
</p>

The full set of screen mockups is in `Part1_Planning_and_Design.docx` /
`FitLog_Screen_Mockups.png` submitted alongside this repository — add a
current screenshot or two here once the app is running on your device.

---

## 1. Purpose of the app

Existing workout-log apps on the Play Store (researched in Part 1 — Strong,
Hevy and FitNotes) each make a trade-off: they are either fully offline with
no account (FitNotes), or account-based and connected but treat offline use
as a limitation to work around (Strong, Hevy). FitLog's purpose is to close
that gap: a single app where every screen works with no signal, and syncs
silently the moment a connection returns, without the user ever having to
think about "offline mode" as a separate feature.

## 2. Design considerations

The full design — requirements, wireframes, navigation flow, API design,
UML diagrams, data model and project plan — was produced in Part 1 and is
summarised here as it maps onto the code in this repository:

| Design decision | Where it lives in this repo | Why |
|---|---|---|
| MVVM + offline-first repository pattern | `app/src/main/java/com/fitlog/app/{ui,data}` | The UI never talks to the network directly; the Repository decides whether to read/write Room, the API, or both, so the same code path works on- and offline. |
| Room as the local source of truth | `data/local/*` | Every write lands here first with `syncStatus = PENDING`, so the app is fully usable with no connection. |
| WorkManager background sync | `data/sync/SyncWorker.kt` | Runs only when a network constraint is met, pushing PENDING rows to the API and marking them SYNCED — turns "offline" into "offline **with sync**". |
| Retrofit + a self-hosted REST API | `data/remote/*`, `/backend` | Connects to a REST API this project builds and hosts itself (Node.js/Express + MongoDB Atlas), per the assignment requirement. |
| Two-stage password hashing | `data/security/PasswordHasher.kt`, `backend/src/routes/auth.js` | The client hashes the raw password (SHA-256 + pepper) before it leaves the device; the server hashes that value again with bcrypt (random salt + slow work factor) before storing it. The plain-text password is never stored, logged, or transmitted unencrypted. |
| Encrypted token storage | `data/security/TokenStore.kt` | The session token is kept in `EncryptedSharedPreferences`, backed by the Android Keystore (AES-256), not plain `SharedPreferences`. |
| DataStore-backed settings | `data/local/SettingsDataStore.kt` | Notification toggle and language selection persist locally and sync best-effort to the API so they follow the user to a new device. |
| Multi-language support | `res/values{,-af,-zu}/strings.xml` | English, Afrikaans and isiZulu string resources; `MainActivity` applies the saved language via `AppCompatDelegate.setApplicationLocales`. |
| SSO, full push notifications | Scaffolded in `data/sync/FitLogMessagingService.kt`, commented dependencies in `app/build.gradle.kts` | Marked "PoE only" in the assignment brief — the architecture is in place (see comments) and completed for the final submission. |

### What's deferred to the final PoE submission

Per the assignment brief, Single Sign-On, the choice of Room/SQLite, and
real-time push notifications are explicitly marked "PoE only" and may be
deferred from this prototype. This prototype fully implements:

- Registration and login, with the password encrypted before it ever leaves
  the device and again before it is stored (see table above).
- Changing settings in the app (notifications on/off, language).
- A connection to a REST API this project built and hosts, backed by a
  database (MongoDB Atlas).

...and additionally includes a working version of **offline mode with sync**
(Room + WorkManager) ahead of schedule, since the brief asks for "at least a
few" of the deferred features to be included early.

## 3. Utilisation of GitHub and GitHub Actions

- **Branching & commits**: work is committed incrementally to `main` (or via
  feature branches merged through pull requests) so the project history
  shows how the app was actually built, not a single final upload.
- **GitHub Actions** (`.github/workflows/android-ci.yml`): every push and
  pull request automatically builds the debug APK and runs unit tests on a
  clean Ubuntu runner using `./gradlew assembleDebug` and
  `./gradlew testDebugUnitTest`. This catches a broken build immediately
  rather than at submission time, and the resulting APK is uploaded as a
  workflow artifact so it can be downloaded straight from the Actions tab.
- **No zip submission**: the complete Kotlin source is committed directly to
  this repository (not zipped), per the submission requirement.

---

## 4. Project structure

```
FitLog/
├── app/                          # Android app (Kotlin, Jetpack Compose, MVVM)
│   └── src/main/java/com/fitlog/app/
│       ├── data/
│       │   ├── local/            # Room entities/DAO + DataStore settings
│       │   ├── remote/           # Retrofit ApiService, DTOs, client
│       │   ├── repository/       # Auth, Settings, Workout repositories
│       │   ├── security/         # Encrypted token store, password hashing
│       │   └── sync/             # WorkManager sync worker, FCM stub
│       ├── di/                   # Manual dependency container
│       ├── domain/               # Plain Kotlin data models
│       └── ui/                   # One package per screen + navigation graph
├── backend/                      # REST API (Node.js + Express + MongoDB)
│   └── src/{models,routes,middleware}/
├── docs/images/                  # Logo, colour palette, diagrams for this README
└── .github/workflows/            # GitHub Actions CI
```

## 5. Setup instructions

### Backend

```bash
cd backend
cp .env.example .env      # fill in MONGODB_URI and JWT_SECRET
npm install
npm run dev                # starts on http://localhost:3000
node seed.js               # populates the exercise catalogue (run once)
```

To host it for real (so the Android app can reach it from a physical
device, not just an emulator), deploy `/backend` to
[Render](https://render.com) (or any Node host): connect this GitHub repo,
set the same environment variables from `.env.example` in Render's
dashboard, and update `API_BASE_URL` in `app/build.gradle.kts` to your
Render URL.

### Android app

1. Open the project root in Android Studio (Koala or newer). Studio will
   offer to generate the Gradle wrapper JAR automatically on first open —
   accept it (this repo intentionally does not commit the binary JAR).
2. If running the backend locally with an emulator, the debug build already
   points at `http://10.0.2.2:3000/` (the emulator's alias for your
   machine's localhost) — no change needed. For a physical device or a
   hosted backend, edit `API_BASE_URL` in `app/build.gradle.kts`.
3. Run the app on an emulator or device (minSdk 26 / Android 8.0+).
4. Register an account, log in, log a workout offline (enable airplane
   mode first to see it), then turn connectivity back on and use
   **Settings → Sync now** to watch it sync.

---

## 6. Use of AI tools during this assessment

*(Write-up, max 500 words — draft below; edit to reflect your own experience
before submitting, since this must describe how **you** used AI tools.)*

Claude (Anthropic) was used as a development assistant throughout this
project's Part 1 and Part 2 work. In Part 1, it was used to research and
summarise three comparable Play Store apps (Strong, Hevy, FitNotes) into a
structured report, and to help translate a set of design decisions
(offline-first architecture, REST API shape, UML diagrams, a project Gantt
chart) into a formatted design document. All factual claims about the
researched apps were checked against their real Play Store listings and
public reviews before being included.

In Part 2, Claude was used to scaffold the Kotlin/Compose Android project
structure (Gradle configuration, MVVM layering, Room entities, Retrofit
client, DataStore settings, WorkManager sync worker) and the matching
Node.js/Express/MongoDB backend, based directly on the architecture already
agreed in the Part 1 design document — no new design decisions were made by
the AI tool that hadn't already been specified. Example of a debugging
interaction: an early version of `WorkoutRepository.observeWorkouts()`
contained an unnecessarily convoluted chain of `Flow` transformations left
over from an earlier draft; this was flagged and simplified to a single
`.map {}` call. Example of a code-generation interaction: the two-stage
password hashing approach (client-side SHA-256 hash before transmission,
server-side bcrypt hash before storage) was proposed by the AI tool in
response to the brief's "encrypt the password of the user" requirement, with
the trade-offs of each stage explained in code comments so the reasoning is
visible on review.

All generated code was read, and run/checked for syntax and module-loading
correctness before being committed (see the backend smoke-test notes in
commit history); UI text, screen content and the specific exercises/workouts
shown are original to this project rather than copied from the researched
apps. No AI tool was used to generate the research report's factual claims
about competitor apps without independent verification against their actual
Play Store listings.

*(Replace this section with your own account of which prompts you used,
what you changed or rejected from the AI's suggestions, and any specific
debugging sessions, before final submission — markers will expect this to
describe your actual working process.)*

---

## 7. Demonstration video

Add the link to your demonstration video here before submitting:

`[VIDEO LINK GOES HERE]`

The video should show: registration and login (with the password hashing
visible in Logcat), changing a setting, logging a workout offline (airplane
mode on), then syncing once back online, all narrated with a voice-over
explaining what data is stored in the hosted database at each step.
