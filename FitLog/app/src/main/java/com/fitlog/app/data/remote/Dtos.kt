package com.fitlog.app.data.remote

/**
 * Wire-format DTOs matching the endpoint table in the Part 1 design document
 * (Section 4, "API Design and Hosting") and backend/src/routes/*.js.
 */

data class RegisterRequest(val name: String, val email: String, val passwordHash: String)
data class LoginRequest(val email: String, val passwordHash: String)

data class AuthResponse(val token: String, val user: UserDto)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val preferredLanguage: String,
    val notificationsEnabled: Boolean,
)

data class UpdateSettingsRequest(
    val preferredLanguage: String? = null,
    val notificationsEnabled: Boolean? = null,
)

data class ExerciseDto(val id: String, val name: String, val category: String)

data class WorkoutSetDto(
    val id: String,
    val workoutId: String,
    val exerciseId: String,
    val exerciseName: String,
    val setNumber: Int,
    val reps: Int,
    val weightKg: Float,
)

data class WorkoutDto(
    val id: String,
    val userId: String,
    val name: String,
    val dateTime: Long,
    val durationSeconds: Int,
    val notes: String,
    val sets: List<WorkoutSetDto>,
)

data class RegisterDeviceRequest(val fcmToken: String, val platform: String = "android")
