# FitLog

FitLog is a native Android Kotlin app based on the supplied planning document and UI mockups. It uses a Kotlin DSL Gradle build, Jetpack Compose UI, Room for offline-first workout data, DataStore for settings, WorkManager for the sync boundary, Retrofit/OkHttp for the REST API contract, and Firebase dependencies for the planned Authentication and FCM integration.

## Run

Open the project in Android Studio and let Gradle sync. The included `gradlew` and `gradlew.bat` download Gradle 8.9 automatically when Gradle is not already cached.

From a terminal on macOS/Linux:

    ./gradlew assembleDebug

On Windows:

    gradlew.bat assembleDebug

The debug APK will be produced at `app/build/outputs/apk/debug/app-debug.apk`.

## Notes

The supplied design is implemented as a runnable local-first prototype. Login and Google Sign-In buttons enter the local app without requiring backend credentials, so the UI can be demonstrated immediately. Room is populated with sample workout data matching the mockup. The Retrofit interface contains the documented FitLog REST endpoints, while the sync worker is the extension point for the Node/Express backend described in the planning document.

For real Firebase Google SSO and FCM, add the project's Firebase `google-services.json`, apply the Google services Gradle plugin, and connect the backend endpoints described in the planning document.

The primary design colors are taken from the supplied palette: `#ED3636`, `#FFEE00`, `#20D200`, `#3BB100`, and `#92FF6E`. The supplied mockups use the bright green as the dominant FitLog brand color, so that is used for the app's main actions, headers, splash screen, and navigation.
