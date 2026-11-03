# Backend + push + Google sign-in setup

1. **MongoDB Atlas** - create a free cluster, copy the connection string.
2. **Render** - New Web Service from this repo, root directory `backend`, build `npm install`, start `npm start`. Environment variables:
   - `MONGODB_URI`, `JWT_SECRET` (any long random text)
   - `GOOGLE_CLIENT_ID` = your Google **Web application** OAuth client id (same as in `LoginViewModel.kt`)
   - `FIREBASE_SERVICE_ACCOUNT` = whole JSON of a Firebase service-account key (Firebase console -> Project settings -> Service accounts -> Generate new private key)
3. Put your Render URL in the app's `gradle.properties` -> `API_BASE_URL` (ends with `/`).
4. **Firebase** - add an Android app with package `com.fitlog.app`, download `google-services.json` into the `app/` folder.
5. **Google sign-in** - in Google Cloud also create an *Android* OAuth client (package `com.fitlog.app` + your SHA-1).
