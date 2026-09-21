package com.fitlog.app.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlog.app.data.repository.AuthRepository
import com.fitlog.app.data.repository.SettingsRepository
import com.fitlog.app.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val notificationsEnabled: Boolean = true,
    val language: String = "en",
    val unitsMetric: Boolean = true,
)

sealed class SyncUiState {
    object Idle : SyncUiState()
    object Syncing : SyncUiState()
    data class Done(val count: Int) : SyncUiState()
    data class Failed(val message: String) : SyncUiState()
}

/**
 * Backs the Settings screen — the second core Part 2 requirement ("The
 * user must be able to change their settings in the app"). Every setter
 * here writes through SettingsRepository, which updates DataStore
 * immediately and best-effort syncs to the REST API.
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val workoutRepository: WorkoutRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        settingsRepository.notificationsEnabled,
        settingsRepository.language,
        settingsRepository.unitsMetric,
    ) { notifications, language, units ->
        SettingsUiState(notifications, language, units)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    private val _syncState = MutableStateFlow<SyncUiState>(SyncUiState.Idle)
    val syncState: StateFlow<SyncUiState> = _syncState.asStateFlow()

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setNotificationsEnabled(enabled) }
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch { settingsRepository.setLanguage(languageCode) }
    }

    fun setUnitsMetric(metric: Boolean) {
        viewModelScope.launch { settingsRepository.setUnitsMetric(metric) }
    }

    fun syncNow() {
        viewModelScope.launch {
            _syncState.value = SyncUiState.Syncing
            val result = workoutRepository.syncPendingWorkouts()
            _syncState.value = result.fold(
                onSuccess = { SyncUiState.Done(it) },
                onFailure = { SyncUiState.Failed(it.message ?: "Sync failed") },
            )
        }
    }

    fun logOut() {
        authRepository.logOut()
    }
}
