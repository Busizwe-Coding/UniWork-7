package com.fitlog.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("fitlog_preferences")

class AppPreferences(private val context: Context) {
    private val notificationsKey = booleanPreferencesKey("notifications_enabled")
    private val languageKey = stringPreferencesKey("language")
    private val unitsMetricKey = booleanPreferencesKey("units_metric")
    private val currentUserIdKey = stringPreferencesKey("current_user_id")
    private val restTimerSoundKey = booleanPreferencesKey("rest_timer_sound")

    // Individual notification categories — Notifications sub-screen.
    private val notifWorkoutRemindersKey = booleanPreferencesKey("notif_workout_reminders")
    private val notifWeeklyRecapKey = booleanPreferencesKey("notif_weekly_recap")
    private val notifPersonalRecordsKey = booleanPreferencesKey("notif_personal_records")
    private val notifStreakRiskKey = booleanPreferencesKey("notif_streak_risk")
    private val reminderDaysKey = stringSetPreferencesKey("reminder_days")

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[notificationsKey] ?: true }
    val language: Flow<String> = context.dataStore.data.map { it[languageKey] ?: "en" }
    val unitsMetric: Flow<Boolean> = context.dataStore.data.map { it[unitsMetricKey] ?: true }
    val currentUserId: Flow<String> = context.dataStore.data.map { it[currentUserIdKey] ?: "" }
    val restTimerSoundEnabled: Flow<Boolean> = context.dataStore.data.map { it[restTimerSoundKey] ?: true }

    val workoutRemindersEnabled: Flow<Boolean> = context.dataStore.data.map { it[notifWorkoutRemindersKey] ?: true }
    val weeklyRecapEnabled: Flow<Boolean> = context.dataStore.data.map { it[notifWeeklyRecapKey] ?: true }
    val personalRecordsEnabled: Flow<Boolean> = context.dataStore.data.map { it[notifPersonalRecordsKey] ?: true }
    val streakRiskAlertsEnabled: Flow<Boolean> = context.dataStore.data.map { it[notifStreakRiskKey] ?: false }
    /** Which weekdays a reminder would fire on — stored so the preference
     *  survives, ready for the POE-phase push implementation to read. */
    val reminderDays: Flow<Set<String>> = context.dataStore.data.map { it[reminderDaysKey] ?: setOf("Mon", "Wed", "Fri") }

    suspend fun setNotifications(enabled: Boolean) = context.dataStore.edit { it[notificationsKey] = enabled }
    suspend fun setLanguage(language: String) = context.dataStore.edit { it[languageKey] = language }
    suspend fun setUnitsMetric(metric: Boolean) = context.dataStore.edit { it[unitsMetricKey] = metric }
    suspend fun setRestTimerSound(enabled: Boolean) = context.dataStore.edit { it[restTimerSoundKey] = enabled }

    suspend fun setWorkoutReminders(enabled: Boolean) = context.dataStore.edit { it[notifWorkoutRemindersKey] = enabled }
    suspend fun setWeeklyRecap(enabled: Boolean) = context.dataStore.edit { it[notifWeeklyRecapKey] = enabled }
    suspend fun setPersonalRecords(enabled: Boolean) = context.dataStore.edit { it[notifPersonalRecordsKey] = enabled }
    suspend fun setStreakRiskAlerts(enabled: Boolean) = context.dataStore.edit { it[notifStreakRiskKey] = enabled }

    suspend fun toggleReminderDay(day: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[reminderDaysKey] ?: setOf("Mon", "Wed", "Fri")
            prefs[reminderDaysKey] = if (day in current) current - day else current + day
        }
    }

    suspend fun setCurrentUserId(userId: String) {
        context.dataStore.edit { it[currentUserIdKey] = userId }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it[currentUserIdKey] = "" }
    }
}
