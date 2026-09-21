package com.fitlog.app.di

import android.content.Context
import com.fitlog.app.data.local.FitLogDatabase
import com.fitlog.app.data.local.SettingsDataStore
import com.fitlog.app.data.remote.RetrofitClient
import com.fitlog.app.data.repository.AuthRepository
import com.fitlog.app.data.repository.SettingsRepository
import com.fitlog.app.data.repository.WorkoutRepository
import com.fitlog.app.data.security.TokenStore

/**
 * A small hand-rolled dependency container. A full Hilt/Dagger setup was
 * judged unnecessary for a diploma-scope app; this keeps every dependency's
 * construction in one readable place instead.
 */
class AppContainer(context: Context) {

    private val tokenStore = TokenStore(context)
    private val settingsDataStore = SettingsDataStore(context)
    private val database = FitLogDatabase.getInstance(context)
    private val apiService = RetrofitClient.create(tokenStore)

    val authRepository = AuthRepository(apiService, tokenStore)
    val settingsRepository = SettingsRepository(settingsDataStore, apiService)
    val workoutRepository = WorkoutRepository(database.workoutDao(), apiService)
}
