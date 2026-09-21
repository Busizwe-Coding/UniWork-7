package com.fitlog.app.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class FitLogRepository(context: Context) {
    private val db = FitLogDatabase.get(context)
    val workouts: Flow<List<WorkoutEntity>> = db.workoutDao().observeWorkouts()
    val exercises: Flow<List<ExerciseEntity>> = db.exerciseDao().observeExercises()
    val user: Flow<UserEntity?> = db.userDao().observeUser()

    suspend fun ensureSeedData() {
        val current = db.userDao().observeUser()
        val seedUser = UserEntity("local-user", "Alex", "alex@email.com")
        db.userDao().upsert(seedUser)
        db.exerciseDao().insertAll(
            listOf(
                ExerciseEntity("bench", "Bench Press"),
                ExerciseEntity("incline", "Incline Dumbbell Press"),
                ExerciseEntity("overhead", "Overhead Press"),
                ExerciseEntity("pushup", "Push-Up"),
                ExerciseEntity("fly", "Cable Fly"),
                ExerciseEntity("squat", "Squat"),
                ExerciseEntity("deadlift", "Deadlift")
            )
        )
        if (db.workoutDao().getById("seed-push") == null) {
            val now = System.currentTimeMillis()
            val workout = WorkoutEntity("seed-push", seedUser.id, "Push Day", now - 86400000L, 42 * 60, syncStatus = "SYNCED")
            db.workoutDao().upsert(workout)
            db.workoutDao().upsertSets(
                listOf(
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "bench", 1, 6, 65f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "bench", 2, 6, 60f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "bench", 3, 6, 60f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "incline", 1, 10, 24f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "incline", 2, 10, 24f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "incline", 3, 10, 24f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "fly", 1, 12, 18f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "fly", 2, 12, 18f, "SYNCED"),
                    WorkoutSetEntity(UUID.randomUUID().toString(), workout.id, "fly", 3, 12, 18f, "SYNCED")
                )
            )
        }
    }

    suspend fun saveWorkout(workout: WorkoutEntity, sets: List<WorkoutSetEntity>) {
        db.workoutDao().upsert(workout.copy(syncStatus = "PENDING"))
        db.workoutDao().upsertSets(sets.map { it.copy(syncStatus = "PENDING") })
    }

    suspend fun getWorkout(id: String): Pair<WorkoutEntity?, List<WorkoutSetEntity>> = db.workoutDao().getById(id) to db.workoutDao().getSets(id)
}
