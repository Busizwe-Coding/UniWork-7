package com.fitlog.app.data.repository

import com.fitlog.app.data.local.SettingsDataStore
import com.fitlog.app.data.remote.ApiService
import com.fitlog.app.data.remote.UpdateSettingsRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Backs the Settings screen (Part 2 core requirement: "The user must be
 * able to change their settings in the app"). Writes are applied to
 * DataStore immediately (so the UI updates instantly and works offline),
 * then best-effort pushed to the REST API so the preference follows the
 * user to another device. A failed push is not fatal — the local value
 * remains the source of truth for this device.
 */
class SettingsRepository(
    private val dataStore: SettingsDataStore,
    private val api: ApiService,
) {
    val notificationsEnabled: Flow<Boolean> = dataStore.notificationsEnabled
    val language: Flow<String> = dataStore.language
    val unitsMetric: Flow<Boolean> = dataStore.unitsMetric

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.setNotificationsEnabled(enabled)
        pushToApi(UpdateSettingsRequest(notificationsEnabled = enabled))
    }

    suspend fun setLanguage(languageCode: String) {
        dataStore.setLanguage(languageCode)
        pushToApi(UpdateSettingsRequest(preferredLanguage = languageCode))
    }

    suspend fun setUnitsMetric(metric: Boolean) {
        dataStore.setUnitsMetric(metric)
    }

    private suspend fun pushToApi(body: UpdateSettingsRequest) = withContext(Dispatchers.IO) {
        try {
            api.updateSettings(body)
        } catch (e: Exception) {
            // Offline or API unreachable — the local DataStore value already
            // applied, and a future sync (or the next successful settings
            // change) will reconcile with the server.
        }
    }
}
