package com.fitlog.app.ui.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlog.app.data.repository.WorkoutRepository
import com.fitlog.app.data.sync.SyncWorker
import com.fitlog.app.domain.Workout
import com.fitlog.app.domain.WorkoutSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class LoggedSet(val exerciseName: String, val exerciseId: String, val setNumber: Int, val reps: Int, val weightKg: Float)

data class ActiveWorkoutUiState(
    val workoutName: String = "Push Day",
    val loggedSets: List<LoggedSet> = emptyList(),
    val isSaving: Boolean = false,
    val saved: Boolean = false,
)

/**
 * Demonstrates the offline-first requirement end-to-end: finishing a
 * workout writes straight to Room (via WorkoutRepository.saveWorkout) with
 * no network call on the critical path, then kicks off SyncWorker, which
 * will push it to the REST API the moment a connection is available.
 */
class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository,
    private val userId: String,
    private val enqueueSync: () -> Unit = {},
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActiveWorkoutUiState())
    val uiState: StateFlow<ActiveWorkoutUiState> = _uiState.asStateFlow()

    private val startTime = System.currentTimeMillis()

    fun logSet(exerciseName: String, exerciseId: String, reps: Int, weightKg: Float) {
        val nextSetNumber = _uiState.value.loggedSets.count { it.exerciseId == exerciseId } + 1
        val newSet = LoggedSet(exerciseName, exerciseId, nextSetNumber, reps, weightKg)
        _uiState.value = _uiState.value.copy(loggedSets = _uiState.value.loggedSets + newSet)
    }

    fun finishWorkout() {
        _uiState.value = _uiState.value.copy(isSaving = true)
        viewModelScope.launch {
            val workoutId = UUID.randomUUID().toString()
            val workout = Workout(
                id = workoutId,
                userId = userId,
                name = _uiState.value.workoutName,
                dateTime = startTime,
                durationSeconds = ((System.currentTimeMillis() - startTime) / 1000).toInt(),
                sets = _uiState.value.loggedSets.mapIndexed { index, s ->
                    WorkoutSet(
                        id = UUID.randomUUID().toString(),
                        workoutId = workoutId,
                        exerciseId = s.exerciseId,
                        exerciseName = s.exerciseName,
                        setNumber = s.setNumber,
                        reps = s.reps,
                        weightKg = s.weightKg,
                    )
                },
            )
            workoutRepository.saveWorkout(workout) // written to Room immediately — works with no connection
            enqueueSync() // ask WorkManager to push it once online; no-op if already offline
            _uiState.value = _uiState.value.copy(isSaving = false, saved = true)
        }
    }
}
