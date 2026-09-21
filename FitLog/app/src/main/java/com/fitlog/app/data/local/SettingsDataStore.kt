package com.fitlog.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "fitlog_settings")

/**
 * Persists the two user-configurable settings required by the brief:
 * notifications on/off and the app's display language. Values are written
 * here immediately for a snappy UI, and mirrored to the REST API
 * (PATCH /users/me) by SettingsRepository so they follow the user to a new
 * device.
 */
class SettingsDataStore(private val context: Context) {

    private object Keys {
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val LANGUAGE = stringPreferencesKey("language")
        val UNITS_METRIC = booleanPreferencesKey("units_metric")
    }

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[Keys.NOTIFICATIONS_ENABLED] ?: true }

    val language: Flow<String> = context.dataStore.data
        .map { it[Keys.LANGUAGE] ?: "en" }

    val unitsMetric: Flow<Boolean> = context.dataStore.data
        .map { it[Keys.UNITS_METRIC] ?: true }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun setLanguage(languageCode: String) {
        context.dataStore.edit { it[Keys.LANGUAGE] = languageCode }
    }

    suspend fun setUnitsMetric(metric: Boolean) {
        context.dataStore.edit { it[Keys.UNITS_METRIC] = metric }
    }
}
