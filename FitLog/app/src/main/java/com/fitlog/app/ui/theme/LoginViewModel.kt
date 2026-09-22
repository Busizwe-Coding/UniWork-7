package com.fitlog.app.ui.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlog.app.data.AppPreferences
import com.fitlog.app.data.FitLogRepository
import com.fitlog.app.data.PasswordUtil
import com.fitlog.app.data.UserEntity
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class LoginViewModel(
    private val prefs: AppPreferences,
    private val repository: FitLogRepository,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Please fill in all fields"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val user = repository.getUserByEmail(email)
            if (user == null || user.passwordHash != PasswordUtil.hash(pass)) {
                _errorMessage.value = "Account doesn't exist or incorrect password"
                _isLoading.value = false
                return@launch
            }

            // Returning user: their workouts are already tagged with this
            // same userId in Room, so they reappear automatically.
            prefs.setCurrentUserId(user.id)
            _isLoading.value = false
            onSuccess()
        }
    }

    fun register(name: String, email: String, pass: String, onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Please fill in all fields to register"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            if (repository.getUserByEmail(email) != null) {
                _errorMessage.value = "An account with this email already exists"
                _isLoading.value = false
                return@launch
            }

            // Brand-new account = fresh id = zero workouts, since every
            // workout query is scoped by userId.
            val user = UserEntity(
                id = UUID.randomUUID().toString(),
                name = name.trim(),
                email = email.lowercase().trim(),
                passwordHash = PasswordUtil.hash(pass),
            )
            repository.upsertUser(user)
            prefs.setCurrentUserId(user.id)
            _isLoading.value = false
            onSuccess()
        }
    }

    fun loginWithGoogle(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("YOUR_WEB_CLIENT_ID.apps.googleusercontent.com")
                    .setAutoSelectEnabled(true)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(context, request)
                handleGoogleCredential(result, onSuccess)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Google sign-in failed"
                _isLoading.value = false
            }
        }
    }

    private suspend fun handleGoogleCredential(result: GetCredentialResponse, onSuccess: () -> Unit) {
        val credential = result.credential
        if (credential is androidx.credentials.CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val name = googleIdTokenCredential.displayName ?: "Google User"
            val email = googleIdTokenCredential.id

            // Find-or-create: a returning Google user keeps their existing
            // id (and workout history); a new one gets a fresh account.
            val existing = repository.getUserByEmail(email)
            val user = existing ?: UserEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                email = email.lowercase().trim(),
                passwordHash = "",
                ssoProvider = "google",
            )
            if (existing == null) repository.upsertUser(user)

            prefs.setCurrentUserId(user.id)
            _isLoading.value = false
            onSuccess()
        } else {
            _errorMessage.value = "Unexpected credential type received"
            _isLoading.value = false
        }
    }
}