package com.fitlog.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlog.app.data.repository.WorkoutRepository
import com.fitlog.app.domain.Workout
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(workoutRepository: WorkoutRepository) : ViewModel() {

    val workouts: StateFlow<List<Workout>> = workoutRepository.observeWorkouts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
