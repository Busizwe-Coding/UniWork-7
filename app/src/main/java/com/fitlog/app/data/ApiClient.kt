package com.fitlog.app.data

import android.util.Log
import com.fitlog.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Where the hosted FitLog REST API lives (Node/Express on Render + MongoDB Atlas, see /backend).
 * Set API_BASE_URL in gradle.properties. While it still holds the YOUR-SERVICE placeholder, the app
 * never calls the network (so it can't send anything to a wrong server) and stays fully local.
 */
object ApiConfig {
    val BASE_URL: String get() = BuildConfig.API_BASE_URL
    val isConfigured: Boolean get() = !BASE_URL.contains("YOUR-SERVICE")
}

data class HttpResult(val code: Int, val body: String) {
    val ok get() = code in 200..299
    val json: JSONObject? get() = runCatching { JSONObject(body) }.getOrNull()
}

/** Small JSON-over-HTTPS client (HttpURLConnection). Every request is logged under tag "FitLogApi". */
object ApiHttp {
    private const val TAG = "FitLogApi"

    /** Throws IOException when there is no connection (callers treat that as "offline"). */
    suspend fun call(method: String, path: String, body: JSONObject? = null, token: String? = null): HttpResult =
        withContext(Dispatchers.IO) {
            if (!ApiConfig.isConfigured) throw IOException("API_BASE_URL not configured")
            var conn: HttpURLConnection? = null
            try {
                conn = (URL(ApiConfig.BASE_URL + path).openConnection() as HttpURLConnection).apply {
                    requestMethod = method
                    connectTimeout = 20_000
                    readTimeout = 30_000          // Render's free tier can need ~30 s to wake up
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Accept", "application/json")
                    if (token != null) setRequestProperty("Authorization", "Bearer $token")
                    if (body != null) doOutput = true
                }
                Log.d(TAG, "$method ${ApiConfig.BASE_URL}$path")
                if (body != null) conn.outputStream.use { it.write(body.toString().toByteArray()) }
                val code = conn.responseCode
                val text = (if (code in 200..299) conn.inputStream else conn.errorStream)?.bufferedReader()?.use { it.readText() } ?: ""
                Log.d(TAG, "<- HTTP $code ($path)")
                HttpResult(code, text)
            } finally {
                conn?.disconnect()
            }
        }
}

data class ApiUser(val id: String, val name: String, val email: String, val token: String)

sealed class ApiResult {
    data class Ok(val user: ApiUser) : ApiResult()
    data class Rejected(val code: Int, val message: String) : ApiResult()
    data class NetworkError(val message: String) : ApiResult()
}

/**
 * Auth endpoints (backend/src/routes/auth.js).
 *  - register/login send only the SHA-256+pepper password hash (server bcrypts it again).
 *  - google() sends the Google ID token; the SERVER verifies it with Google before trusting it.
 */
object AuthApi {
    suspend fun register(name: String, email: String, passwordHash: String): ApiResult =
        post("auth/register", JSONObject().put("name", name).put("email", email).put("passwordHash", passwordHash))

    suspend fun login(email: String, passwordHash: String): ApiResult =
        post("auth/login", JSONObject().put("email", email).put("passwordHash", passwordHash))

    suspend fun google(idToken: String): ApiResult =
        post("auth/google", JSONObject().put("idToken", idToken))

    private suspend fun post(path: String, body: JSONObject): ApiResult = try {
        val res = ApiHttp.call("POST", path, body)
        val json = res.json
        if (res.ok && json != null) {
            val u = json.getJSONObject("user")
            ApiResult.Ok(ApiUser(u.getString("id"), u.getString("name"), u.getString("email"), json.getString("token")))
        } else {
            ApiResult.Rejected(res.code, json?.optString("error")?.takeIf { it.isNotBlank() } ?: "Server error (${res.code})")
        }
    } catch (e: IOException) {
        ApiResult.NetworkError(e.message ?: "No connection")
    } catch (e: Exception) {
        Log.e("FitLogApi", "Unexpected API error on $path", e)
        ApiResult.NetworkError(e.message ?: "Unexpected error")
    }
}

/** Best-effort upload of a finished workout. The server answers by pushing "Workout saved" to the user's phones. */
object WorkoutApi {
    suspend fun upload(token: String, workout: JSONObject): Boolean = try {
        ApiHttp.call("POST", "workouts", workout, token).ok
    } catch (e: Exception) {
        Log.w("FitLogApi", "Workout upload skipped: ${e.message}")
        false
    }
}
