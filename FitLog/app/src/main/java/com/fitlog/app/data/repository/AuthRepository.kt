package com.fitlog.app.data.repository

import com.fitlog.app.data.remote.ApiService
import com.fitlog.app.data.remote.LoginRequest
import com.fitlog.app.data.remote.RegisterRequest
import com.fitlog.app.data.security.PasswordHasher
import com.fitlog.app.data.security.TokenStore
import com.fitlog.app.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

/**
 * Handles registration and login (Part 2 core requirement: "The user must
 * be able to register and log in to the app, and encrypt the password of
 * the user"). The raw password never reaches this class in a form suitable
 * for storage — it is hashed client-side (PasswordHasher) before being sent
 * to the API, which hashes it again with bcrypt before persisting it.
 */
class AuthRepository(
    private val api: ApiService,
    private val tokenStore: TokenStore,
) {

    suspend fun register(name: String, email: String, rawPassword: String): AuthResult =
        withContext(Dispatchers.IO) {
            try {
                val hashed = PasswordHasher.hash(rawPassword)
                val response = api.register(RegisterRequest(name, email, hashed))
                tokenStore.saveToken(response.token)
                tokenStore.saveUserId(response.user.id)
                AuthResult.Success(response.user.toDomain())
            } catch (e: Exception) {
                AuthResult.Error(e.message ?: "Registration failed")
            }
        }

    suspend fun login(email: String, rawPassword: String): AuthResult =
        withContext(Dispatchers.IO) {
            try {
                val hashed = PasswordHasher.hash(rawPassword)
                val response = api.login(LoginRequest(email, hashed))
                tokenStore.saveToken(response.token)
                tokenStore.saveUserId(response.user.id)
                AuthResult.Success(response.user.toDomain())
            } catch (e: Exception) {
                AuthResult.Error(e.message ?: "Login failed")
            }
        }

    fun isLoggedIn(): Boolean = tokenStore.isLoggedIn()

    fun logOut() = tokenStore.clear()
}

private fun com.fitlog.app.data.remote.UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email,
    preferredLanguage = preferredLanguage,
    notificationsEnabled = notificationsEnabled,
)
