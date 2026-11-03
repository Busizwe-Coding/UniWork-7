package com.fitlog.app.ui.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitlog.app.data.ApiConfig
import com.fitlog.app.data.ApiResult
import com.fitlog.app.data.ApiUser
import com.fitlog.app.data.AppPreferences
import com.fitlog.app.data.AuthApi
import com.fitlog.app.data.FitLogRepository
import com.fitlog.app.data.PasswordUtil
import com.fitlog.app.data.UserEntity
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
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

    /**
     * Normal email + password login. If the REST API is configured and reachable the server is asked first
     * (and a local account made before the API existed is registered on the server once). Without a
     * connection the local account is used, so the app keeps working offline.
     */
    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            _errorMessage.value = "Please fill in all fields"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            val cleanEmail = email.lowercase().trim()
            val hash = PasswordUtil.hash(pass)
            val local = repository.getUserByEmail(cleanEmail)
            val localMatches = local != null && local.passwordHash == hash

            var server: ApiResult? = null
            if (ApiConfig.isConfigured) {
                server = AuthApi.login(cleanEmail, hash)
                if (server is ApiResult.Rejected && server.code == 401 && localMatches) {
                    Log.d(TAG, "Local-only account found; registering it on the server")
                    server = AuthApi.register(local!!.name, cleanEmail, hash)
                }
            }

            when (server) {
                is ApiResult.Ok -> { finishLogin(local, server.user, hash, "email"); onSuccess() }
                is ApiResult.Rejected -> fail(if (server.code == 401) "Account doesn't exist or incorrect password" else server.message)
                else -> {   // API not configured, or no connection -> local account
                    if (localMatches) {
                        prefs.setCurrentUserId(local!!.id)
                        _isLoading.value = false
                        onSuccess()
                    } else fail("Account doesn't exist or incorrect password")
                }
            }
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
            val cleanEmail = email.lowercase().trim()
            val hash = PasswordUtil.hash(pass)

            if (repository.getUserByEmail(cleanEmail) != null) {
                _errorMessage.value = "An account with this email already exists"
                _isLoading.value = false
                return@launch
            }

            // Brand-new account = fresh id = zero workouts, since every workout query is scoped by userId.
            var server: ApiResult? = null
            if (ApiConfig.isConfigured) server = AuthApi.register(name.trim(), cleanEmail, hash)
            when (server) {
                is ApiResult.Ok -> { finishLogin(null, server.user, hash, "email"); onSuccess() }
                is ApiResult.Rejected -> fail(if (server.code == 409) "An account with this email already exists" else server.message)
                else -> {   // no API / offline: create the account on the phone only
                    val user = UserEntity(UUID.randomUUID().toString(), name.trim(), cleanEmail, passwordHash = hash)
                    repository.upsertUser(user)
                    prefs.setCurrentUserId(user.id)
                    _isLoading.value = false
                    onSuccess()
                }
            }
        }
    }

    /** Make sure a local account exists for this server account, remember the session token, set current user. */
    private suspend fun finishLogin(local: UserEntity?, api: ApiUser, hash: String, provider: String) {
        val user = local ?: UserEntity(id = api.id, name = api.name, email = api.email.lowercase(), passwordHash = hash, ssoProvider = provider)
        if (local == null) repository.upsertUser(user)
        prefs.setAuthToken(api.token)
        prefs.setCurrentUserId(user.id)
        Log.d(TAG, "Signed in as ${user.email} (server session stored)")
        _isLoading.value = false
    }

    private fun fail(message: String) {
        _errorMessage.value = message
        _isLoading.value = false
    }

    /**
     * Google single sign-on. Credential Manager shows Google's own account picker; we get back a Google
     * ID token (a signed proof of who the user is), read the verified name + email from it and then
     * find-or-create the local account exactly like normal registration does. Email/password login is
     * untouched and still works side by side.
     */
    fun loginWithGoogle(context: Context, onSuccess: () -> Unit) {
        if (GOOGLE_WEB_CLIENT_ID.startsWith("YOUR_")) {
            _errorMessage.value = "Google sign-in isn't set up yet: add your Web client ID in LoginViewModel.kt"
            Log.w(TAG, "GOOGLE_WEB_CLIENT_ID is still the placeholder")
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // "Sign in with Google" option: always shows the account chooser, even for first-time users
                val option = GetSignInWithGoogleOption.Builder(GOOGLE_WEB_CLIENT_ID).build()
                val request = GetCredentialRequest.Builder().addCredentialOption(option).build()
                val result = CredentialManager.create(context).getCredential(context, request)
                handleGoogleCredential(result, onSuccess)
            } catch (e: GetCredentialCancellationException) {
                Log.d(TAG, "Google sign-in cancelled by user")
                _isLoading.value = false                       // not an error - they just closed the picker
            } catch (e: NoCredentialException) {
                Log.w(TAG, "No Google account available", e)
                _errorMessage.value = "No Google account found on this phone. Add one in Settings and try again."
                _isLoading.value = false
            } catch (e: GetCredentialException) {
                Log.w(TAG, "Google sign-in failed", e)
                _errorMessage.value = "Google sign-in failed. Check your connection and that the app's Google setup is complete."
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected Google sign-in error", e)
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
            val google = GoogleIdTokenCredential.createFrom(credential.data)
            if (!ApiConfig.isConfigured) {
                fail("Google sign-in needs the server: set API_BASE_URL in gradle.properties")
                return
            }
            // The SERVER verifies the Google ID token (signature, expiry, audience) before trusting it.
            when (val res = AuthApi.google(google.idToken)) {
                is ApiResult.Ok -> {
                    // Find-or-create: a returning Google user keeps their existing id (and workout history)
                    val local = repository.getUserByEmail(res.user.email)
                    finishLogin(local, res.user, "", "google")
                    Log.d(TAG, "Google sign-in verified by server (new=${local == null})")
                    onSuccess()
                }
                is ApiResult.Rejected -> fail(res.message)
                is ApiResult.NetworkError -> fail("Can't reach the server. Google sign-in needs an internet connection.")
            }
        } else {
            fail("Unexpected credential type received")
        }
    }

    private companion object {
        const val TAG = "FitLogAuth"

        // >>> PUT YOUR GOOGLE "WEB APPLICATION" OAUTH CLIENT ID HERE <<<
        // Google Cloud Console -> APIs & Services -> Credentials -> Create OAuth client ID -> Web application.
        // (An "Android" OAuth client for package com.fitlog.app + your SHA-1 must also exist in the same project.)
        const val GOOGLE_WEB_CLIENT_ID = "302125703926-tka3p7k251f1ktc4dkh1b3198hp5his0.apps.googleusercontent.com"
    }
}
