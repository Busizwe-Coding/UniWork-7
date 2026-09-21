package com.fitlog.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    // --- Workouts -------------------------------------------------------

    @Query("SELECT * FROM workouts ORDER BY dateTime DESC")
    fun observeWorkouts(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE syncStatus = 'PENDING'")
    suspend fun getPendingWorkouts(): List<WorkoutEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorkout(workout: WorkoutEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorkouts(workouts: List<WorkoutEntity>)

    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Query("UPDATE workouts SET syncStatus = :status WHERE id = :workoutId")
    suspend fun setWorkoutSyncStatus(workoutId: String, status: String)

    // --- Sets -------------------------------------------------------------

    @Query("SELECT * FROM workout_sets WHERE workoutId = :workoutId ORDER BY setNumber")
    fun observeSetsForWorkout(workoutId: String): Flow<List<WorkoutSetEntity>>

    @Query("SELECT * FROM workout_sets WHERE syncStatus = 'PENDING'")
    suspend fun getPendingSets(): List<WorkoutSetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSet(set: WorkoutSetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSets(sets: List<WorkoutSetEntity>)

    @Query("UPDATE workout_sets SET syncStatus = :status WHERE id = :setId")
    suspend fun setSetSyncStatus(setId: String, status: String)

    @Transaction
    suspend fun insertWorkoutWithSets(workout: WorkoutEntity, sets: List<WorkoutSetEntity>) {
        upsertWorkout(workout)
        upsertSets(sets)
    }

    // --- Exercises (cached master list) -----------------------------------

    @Query("SELECT * FROM exercises ORDER BY name")
    fun observeExercises(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertExercises(exercises: List<ExerciseEntity>)
}
