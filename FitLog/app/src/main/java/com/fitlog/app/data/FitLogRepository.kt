package com.fitlog.app.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FitLogRepository(context: Context) {
    private val db = FitLogDatabase.get(context)

    val exercises: Flow<List<ExerciseEntity>> = db.exerciseDao().observeExercises()

    fun workoutsForUser(userId: String): Flow<List<WorkoutEntity>> {
        if (userId.isBlank()) return flowOf(emptyList())
        return db.workoutDao().observeWorkouts(userId)
    }

    fun setsForUser(userId: String): Flow<List<WorkoutSetEntity>> {
        if (userId.isBlank()) return flowOf(emptyList())
        return db.workoutDao().observeSetsForUser(userId)
    }

    fun observeUser(userId: String): Flow<UserEntity?> {
        if (userId.isBlank()) return flowOf(null)
        return db.userDao().observeUserById(userId)
    }

    suspend fun getUserByEmail(email: String): UserEntity? = db.userDao().getByEmail(email.lowercase().trim())
    suspend fun upsertUser(user: UserEntity) = db.userDao().upsert(user)

    suspend fun saveWorkout(workout: WorkoutEntity, sets: List<WorkoutSetEntity>) {
        db.workoutDao().upsert(workout.copy(syncStatus = "PENDING"))
        db.workoutDao().upsertSets(sets.map { it.copy(syncStatus = "PENDING") })
    }

    suspend fun getWorkout(id: String): Pair<WorkoutEntity?, List<WorkoutSetEntity>> =
        db.workoutDao().getById(id) to db.workoutDao().getSets(id)

    suspend fun deleteWorkout(id: String) {
        db.workoutDao().deleteSetsForWorkout(id)
        db.workoutDao().deleteWorkout(id)
    }

    suspend fun deleteSet(id: String) = db.workoutDao().deleteSet(id)

    /** "Last time" data for auto-fill — most recent logged sets for this
     *  exercise, newest first. */
    suspend fun getRecentSetsForExercise(userId: String, exerciseId: String, limit: Int = 3): List<WorkoutSetEntity> =
        db.workoutDao().getRecentSetsForExercise(userId, exerciseId, limit)

    /** Reference exercise catalogue — not user-specific, safe to reseed on
     *  every launch (REPLACE is idempotent for identical rows). */
    suspend fun ensureExercisesSeeded() {
        db.exerciseDao().insertAll(DEFAULT_EXERCISES)
    }

    companion object {
        val DEFAULT_EXERCISES = listOf(
            ExerciseEntity("bench", "Bench Press", "Chest"),
            ExerciseEntity("incline", "Incline Dumbbell Press", "Chest"),
            ExerciseEntity("fly", "Cable Fly", "Chest"),
            ExerciseEntity("pushup", "Push-Up", "Chest"),
            ExerciseEntity("overhead", "Overhead Press", "Shoulders"),
            ExerciseEntity("squat", "Squat", "Legs"),
            ExerciseEntity("legpress", "Leg Press", "Legs"),
            ExerciseEntity("deadlift", "Deadlift", "Back"),
            ExerciseEntity("row", "Barbell Row", "Back"),
            ExerciseEntity("pullup", "Pull-Up", "Back"),
            ExerciseEntity("curl", "Bicep Curl", "Arms"),
            ExerciseEntity("tricep", "Tricep Pushdown", "Arms"),
        )
    }
}
