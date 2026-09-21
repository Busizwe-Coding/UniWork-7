package com.fitlog.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlog.app.data.AppPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class LoginViewModel(private val prefs: AppPreferences) : ViewModel() {
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
            delay(600)

            val isValid = prefs.validateLogin(email, pass)
            if (!isValid) {
                _errorMessage.value = "Account doesn't exist or incorrect password"
                _isLoading.value = false
                return@launch
            }

            val name = email.substringBefore("@").replaceFirstChar { it.uppercase() }
            prefs.saveUserSession(name, email)
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
            delay(600)
            prefs.registerUser(name, email, pass)
            prefs.saveUserSession(name, email)
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
                    .setServerClientId("YOUR_WEB_CLIENT_ID.apps.googleusercontent.com") // Replace with your actual Google Cloud Console Client ID
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

            prefs.saveUserSession(name, email)
            _isLoading.value = false
            onSuccess()
        } else {
            _errorMessage.value = "Unexpected credential type received"
            _isLoading.value = false
        }
    }
}