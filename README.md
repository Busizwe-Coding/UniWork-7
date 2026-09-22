# UniWork-7

FitLog is a native Android strength-training and workout tracking application built with Kotlin and Jetpack Compose.

The application is designed to help users create and track workouts, record exercises and sets, review workout history, and manage their profile and settings. FitLog is designed with an offline-first approach so users can continue recording workouts without an active internet connection.

## Features
 
- Google Sign-In authentication
- Workout creation and tracking
- Exercise and set recording
- Workout history  
- User profile
- Application settings
- Offline-first local data storage
- Background synchronization
- Push notifications
- English and isiZulu language support
- REST API integration
- Cloud backup and account synchronization

## Screens

FitLog includes the following main screens:

- Login
- Register
- Home Dashboard
- Active Workout
- Workout History
- Profile
- Settings

## Technology Stack

### Android

- Kotlin
- Jetpack Compose
- Android SDK
- Material Design
- MVVM architecture

### Local Storage

- Room Database
- DataStore

### Networking

- Retrofit
- OkHttp
- REST API

### Background Processing

- WorkManager
- Firebase Cloud Messaging (FCM)

### Authentication

- Firebase Authentication
- Google Sign-In

### Backend

The planned backend uses:

- Node.js
- Express.js
- MongoDB Atlas
- Render
 
## Data Model

The main application entities include:

### User

Stores account and profile information.

### Workout

Represents an individual workout session.

### WorkoutSet

Stores the exercises, repetitions, weight, and other set information recorded during a workout.

## API

The planned REST API includes endpoints such as:

```text
POST   /auth/verify
GET    /users/me
PATCH  /users/me

GET    /workouts
POST   /workouts
PATCH  /workouts
DELETE /workouts

GET    /exercises

POST   /devices/register
```

## Offline-First Design

FitLog is designed to allow users to continue using the application when they are offline.

Workout data is stored locally using Room. When network connectivity is available, background synchronization can be performed using WorkManager.


## Localization

FitLog is intended to support:

```text
English
isiZulu
```

The application can be extended with additional translations in the future.

## Requirements

To build and run FitLog, you will need:

* Android Studio
* JDK 17
* Android SDK
* Android SDK Platform 35
* An Android emulator or physical Android device

## Running the Project

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/FitLog.git
```

Open the `FitLog` folder in Android Studio.

Allow Android Studio to synchronize the Gradle project.

Select an Android emulator or connected Android device.

Then run the `app` configuration.

## GitHub and Git Bash Utilisation

GitHub is used as the version control and remote repository platform for FitLog. The project source code is stored in a GitHub repository so that changes to the Android application can be backed up and tracked over time.

Git Bash is used to push changes from the local FitLog project to the GitHub repository. The Git Bash workflow used for this project is limited to committing and pushing code changes.

GitHub Repository

The GitHub repository serves as the remote location for the FitLog source code. It provides a central location for storing the project and maintaining a history of code changes.

Git Bash is used only for pushing the completed local changes to GitHub. Development and code modifications are performed in Android Studio.


## License

This project is currently intended for educational and development purposes.

A formal open-source license can be added to the repository when the project is ready for public distribution.



