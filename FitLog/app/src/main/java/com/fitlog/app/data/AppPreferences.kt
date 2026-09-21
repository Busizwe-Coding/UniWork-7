package com.fitlog.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

private val registeredEmailKey = stringPreferencesKey("registered_email")
private val registeredPasswordKey = stringPreferencesKey("registered_password")
private val registeredNameKey = stringPreferencesKey("registered_name")
val Context.dataStore by preferencesDataStore("fitlog_preferences")

class AppPreferences(private val context: Context) {
    private val notificationsKey = booleanPreferencesKey("notifications_enabled")
    private val languageKey = stringPreferencesKey("language")
    private val userNameKey = stringPreferencesKey("user_name")
    private val userEmailKey = stringPreferencesKey("user_email")
    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[notificationsKey] ?: true }
    val language: Flow<String> = context.dataStore.data.map { it[languageKey] ?: "en" }
    val userName: Flow<String> = context.dataStore.data.map { it[userNameKey] ?: "Athlete" }
    val userEmail: Flow<String> = context.dataStore.data.map { it[userEmailKey] ?: "" }
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[isLoggedInKey] ?: false }

    suspend fun registerUser(name: String, email: String, pass: String) {
        context.dataStore.edit { prefs ->
            prefs[registeredNameKey] = name
            prefs[registeredEmailKey] = email.lowercase().trim()
            prefs[registeredPasswordKey] = pass
        }
    }

    suspend fun validateLogin(email: String, pass: String): Boolean {
        val prefs = context.dataStore.data.first()
        val savedEmail = prefs[registeredEmailKey] ?: ""
        val savedPass = prefs[registeredPasswordKey] ?: ""

        return email.lowercase().trim() == savedEmail && pass == savedPass
    }

    fun hasRegisteredAccount(): Flow<Boolean> {
        return context.dataStore.data.map { !it[registeredEmailKey].isNullOrBlank() }
    }
    suspend fun setNotifications(enabled: Boolean) = context.dataStore.edit { it[notificationsKey] = enabled }
    suspend fun setLanguage(language: String) = context.dataStore.edit { it[languageKey] = language }

    suspend fun saveUserSession(name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[userNameKey] = name
            prefs[userEmailKey] = email
            prefs[isLoggedInKey] = true
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs[userNameKey] = "Athlete"
            prefs[userEmailKey] = ""
            prefs[isLoggedInKey] = false
        }
    }
}
