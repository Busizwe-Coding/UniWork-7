package com.fitlog.app.domain

/**
 * Domain models mirror the entities defined in the Part 1 design document
 * (Section 6: "Data Captured and Stored") so that local (Room), remote (REST
 * API / MongoDB) and UI layers all speak the same shape of data.
 */

data class User(
    val id: String,
    val name: String,
    val email: String,
    val preferredLanguage: String = "en",
    val notificationsEnabled: Boolean = true,
)

enum class SyncStatus { PENDING, SYNCED, FAILED }

data class Exercise(
    val id: String,
    val name: String,
    val category: String,
)

data class WorkoutSet(
    val id: String,
    val workoutId: String,
    val exerciseId: String,
    val exerciseName: String,
    val setNumber: Int,
    val reps: Int,
    val weightKg: Float,
    val syncStatus: SyncStatus = SyncStatus.PENDING,
)

data class Workout(
    val id: String,
    val userId: String,
    val name: String,
    val dateTime: Long,
    val durationSeconds: Int,
    val notes: String = "",
    val syncStatus: SyncStatus = SyncStatus.PENDING,
    val sets: List<WorkoutSet> = emptyList(),
)
