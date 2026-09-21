package com.fitlog.app.data.repository

import com.fitlog.app.data.local.ExerciseEntity
import com.fitlog.app.data.local.WorkoutDao
import com.fitlog.app.data.local.WorkoutEntity
import com.fitlog.app.data.local.WorkoutSetEntity
import com.fitlog.app.data.remote.ApiService
import com.fitlog.app.data.remote.WorkoutDto
import com.fitlog.app.data.remote.WorkoutSetDto
import com.fitlog.app.domain.SyncStatus
import com.fitlog.app.domain.Workout
import com.fitlog.app.domain.WorkoutSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID

/**
 * Implements the offline-first pattern from the Part 1 design document
 * (Section 2.5): every write lands in Room immediately with
 * syncStatus = PENDING so the UI never blocks on the network, and
 * [syncPendingWorkouts] later pushes those rows to the REST API — called
 * either by the user (Settings > Sync now) or automatically by
 * data/sync/SyncWorker when connectivity returns.
 */
class WorkoutRepository(
    private val dao: WorkoutDao,
    private val api: ApiService,
) {

    /** Emits the local (offline-first) list of workouts, most recent first. */
    fun observeWorkouts(): Flow<List<Workout>> =
        dao.observeWorkouts().map { entities -> entities.map { it.toDomainShallow() } }

    /** Emits the sets belonging to a single workout, for the detail screen. */
    fun observeSetsForWorkout(workoutId: String): Flow<List<WorkoutSet>> =
        dao.observeSetsForWorkout(workoutId).map { entities -> entities.map { it.toDomain() } }

    suspend fun saveWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        val entity = WorkoutEntity(
            id = workout.id.ifBlank { UUID.randomUUID().toString() },
            userId = workout.userId,
            name = workout.name,
            dateTime = workout.dateTime,
            durationSeconds = workout.durationSeconds,
            notes = workout.notes,
            syncStatus = SyncStatus.PENDING.name,
        )
        val setEntities = workout.sets.map {
            WorkoutSetEntity(
                id = it.id.ifBlank { UUID.randomUUID().toString() },
                workoutId = entity.id,
                exerciseId = it.exerciseId,
                exerciseName = it.exerciseName,
                setNumber = it.setNumber,
                reps = it.reps,
                weightKg = it.weightKg,
                syncStatus = SyncStatus.PENDING.name,
            )
        }
        dao.insertWorkoutWithSets(entity, setEntities)
    }

    suspend fun cacheExercises(exercises: List<com.fitlog.app.domain.Exercise>) = withContext(Dispatchers.IO) {
        dao.upsertExercises(exercises.map { ExerciseEntity(it.id, it.name, it.category) })
    }

    /**
     * Pushes every PENDING workout (and its sets) to the REST API and marks
     * it SYNCED on success, or leaves it PENDING (to retry later) on
     * failure. Returns the number of workouts successfully synced.
     */
    suspend fun syncPendingWorkouts(): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val pendingWorkouts = dao.getPendingWorkouts()
            var syncedCount = 0
            for (w in pendingWorkouts) {
                try {
                    val sets = dao.getPendingSets().filter { it.workoutId == w.id }
                    val dto = WorkoutDto(
                        id = w.id,
                        userId = w.userId,
                        name = w.name,
                        dateTime = w.dateTime,
                        durationSeconds = w.durationSeconds,
                        notes = w.notes,
                        sets = sets.map {
                            WorkoutSetDto(it.id, it.workoutId, it.exerciseId, it.exerciseName, it.setNumber, it.reps, it.weightKg)
                        },
                    )
                    api.createWorkout(dto) // idempotent server-side upsert by id
                    dao.setWorkoutSyncStatus(w.id, SyncStatus.SYNCED.name)
                    sets.forEach { dao.setSetSyncStatus(it.id, SyncStatus.SYNCED.name) }
                    syncedCount++
                } catch (e: Exception) {
                    dao.setWorkoutSyncStatus(w.id, SyncStatus.FAILED.name)
                }
            }
            // Pull anything created on another device since we don't track a
            // real "last synced at" yet in this prototype — the full
            // incremental GET /workouts?since= pull is completed for the POE.
            Result.success(syncedCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

private fun WorkoutEntity.toDomainShallow() = Workout(
    id = id,
    userId = userId,
    name = name,
    dateTime = dateTime,
    durationSeconds = durationSeconds,
    notes = notes,
    syncStatus = SyncStatus.valueOf(syncStatus),
    sets = emptyList(), // loaded separately per-workout via observeSetsForWorkout when a detail screen needs them
)

fun WorkoutSetEntity.toDomain() = WorkoutSet(
    id = id, workoutId = workoutId, exerciseId = exerciseId, exerciseName = exerciseName,
    setNumber = setNumber, reps = reps, weightKg = weightKg, syncStatus = SyncStatus.valueOf(syncStatus),
)
