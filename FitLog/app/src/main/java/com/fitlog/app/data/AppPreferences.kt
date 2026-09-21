package com.fitlog.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("fitlog_preferences")

class AppPreferences(private val context: Context) {
    private val notificationsKey = booleanPreferencesKey("notifications_enabled")
    private val languageKey = stringPreferencesKey("language")

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[notificationsKey] ?: true }
    val language: Flow<String> = context.dataStore.data.map { it[languageKey] ?: "en" }

    suspend fun setNotifications(enabled: Boolean) = context.dataStore.edit { it[notificationsKey] = enabled }
    suspend fun setLanguage(language: String) = context.dataStore.edit { it[languageKey] = language }
}
