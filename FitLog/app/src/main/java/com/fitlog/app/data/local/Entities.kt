package com.fitlog.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Local (offline-first) mirror of a Workout. Every write from the UI lands
 * here first with syncStatus = PENDING; the SyncWorker later pushes PENDING
 * rows to the REST API and flips them to SYNCED. See data/sync/SyncWorker.kt
 * and the Part 1 design document, Section 2.5 "Offline Mode with Sync".
 */
@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val dateTime: Long,
    val durationSeconds: Int,
    val notes: String,
    val syncStatus: String, // SyncStatus enum name
)

@Entity(tableName = "workout_sets")
data class WorkoutSetEntity(
    @PrimaryKey val id: String,
    val workoutId: String,
    val exerciseId: String,
    val exerciseName: String,
    val setNumber: Int,
    val reps: Int,
    val weightKg: Float,
    val syncStatus: String,
)

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
)
