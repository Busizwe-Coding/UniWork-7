package com.fitlog.app.data

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.fitlog.app.MainActivity
import com.fitlog.app.R
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

const val NOTIFICATION_CHANNEL_ID = "fitlog_default"
private const val TAG = "FitLogPush"

/** Android 8+ needs a channel before any notification can be shown. */
fun ensureNotificationChannel(context: Context) {
    val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "FitLog notifications", NotificationManager.IMPORTANCE_DEFAULT)
        .apply { description = "Workout reminders, weekly recap and push messages" }
    context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
}

/** Shows a notification (silently skipped when the user has not allowed notifications on Android 13+). */
fun showFitLogNotification(context: Context, title: String, body: String) {
    if (Build.VERSION.SDK_INT >= 33 &&
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
    ) { Log.w(TAG, "Notification permission not granted - skipping '$title'"); return }
    ensureNotificationChannel(context)
    val open = PendingIntent.getActivity(
        context, 0, Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
    )
    val n = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_stat_notify)
        .setContentTitle(title)
        .setContentText(body)
        .setStyle(NotificationCompat.BigTextStyle().bigText(body))
        .setColor(0xFF20D200.toInt())
        .setAutoCancel(true)
        .setContentIntent(open)
        .build()
    context.getSystemService(NotificationManager::class.java).notify(System.currentTimeMillis().toInt(), n)
}

// ---------------------------------------------------------------------------------------------
// 1) Scheduled reminders ON THE PHONE (WorkManager) - follow the Daily / Weekly / Monthly / Off choice
// ---------------------------------------------------------------------------------------------

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val prefs = AppPreferences(applicationContext)
        val freq = prefs.notificationFrequency.first()
        val userId = prefs.currentUserId.first()
        if (freq == "off" || userId.isBlank() || !prefs.workoutRemindersEnabled.first()) return Result.success()

        val dayNames = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val today = dayNames[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
        if (freq == "daily" && today !in prefs.reminderDays.first()) return Result.success()   // honour reminder days

        val workouts = FitLogRepository(applicationContext).workoutsForUser(userId).first()
        fun countSince(days: Int) = workouts.count { it.dateTime >= System.currentTimeMillis() - days * 86_400_000L }
        val (title, body) = when (freq) {
            "weekly" -> "Your weekly recap 📊" to "You logged ${countSince(7)} workout(s) in the last 7 days. Keep your streak going!"
            "monthly" -> "Your monthly progress 📅" to "You logged ${countSince(30)} workout(s) in the last 30 days."
            else -> "Time to train 💪" to "Log today's workout in FitLog."
        }
        Log.d(TAG, "Reminder fired ($freq)")
        showFitLogNotification(applicationContext, title, body)
        return Result.success()
    }
}

object ReminderScheduler {
    private const val WORK = "fitlog_reminders"

    /** (Re)schedules the reminder for the chosen frequency at about 17:00 local time. "off" cancels it. */
    fun schedule(context: Context, frequency: String) {
        val wm = WorkManager.getInstance(context)
        val days = when (frequency) { "daily" -> 1L; "weekly" -> 7L; "monthly" -> 30L; else -> { wm.cancelUniqueWork(WORK); return } }
        val next = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 17); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) add(Calendar.DAY_OF_YEAR, 1)
        }
        val delay = next.timeInMillis - System.currentTimeMillis()
        val request = PeriodicWorkRequestBuilder<ReminderWorker>(days, TimeUnit.DAYS).setInitialDelay(delay, TimeUnit.MILLISECONDS).build()
        wm.enqueueUniquePeriodicWork(WORK, ExistingPeriodicWorkPolicy.UPDATE, request)
        Log.d(TAG, "Reminders scheduled: $frequency (first in ${delay / 60000} min)")
    }
}

// ---------------------------------------------------------------------------------------------
// 2) Real-time push FROM THE SERVER (Firebase Cloud Messaging)
// ---------------------------------------------------------------------------------------------

/** Receives pushes sent by our server. While the app is open FCM shows nothing itself, so we do. */
class FitLogMessagingService : FirebaseMessagingService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        Log.d(TAG, "New FCM token")
        scope.launch { AppPreferences(applicationContext).setFcmToken(token); PushRegistrar.registerDevice(applicationContext) }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title ?: message.data["title"] ?: "FitLog"
        val body = message.notification?.body ?: message.data["body"] ?: return
        Log.d(TAG, "Push received: $title")
        showFitLogNotification(this, title, body)
    }
}

/** Gives this phone's FCM token to our server (POST /devices/register) so it can push to it. */
object PushRegistrar {
    private suspend fun currentToken(context: Context): String? {
        if (FirebaseApp.getApps(context).isEmpty()) {
            Log.w(TAG, "Firebase not configured (add app/google-services.json) - server push disabled")
            return null
        }
        return suspendCancellableCoroutine { cont ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { t ->
                if (t.isSuccessful) cont.resume(t.result) else cont.resume(null)
            }
        }
    }

    suspend fun registerDevice(context: Context) {
        try {
            val prefs = AppPreferences(context)
            val auth = prefs.authToken.first()
            if (auth.isBlank()) return                      // not signed in to the server
            val token = currentToken(context) ?: return
            prefs.setFcmToken(token)
            val res = ApiHttp.call("POST", "devices/register", JSONObject().put("fcmToken", token), auth)
            Log.d(TAG, "Device registered for push: HTTP ${res.code}")
        } catch (e: Exception) {
            Log.w(TAG, "Device registration failed (retries next launch): ${e.message}")
        }
    }

    suspend fun unregisterDevice(context: Context) {
        try {
            val prefs = AppPreferences(context)
            val auth = prefs.authToken.first(); val token = prefs.fcmToken.first()
            if (auth.isNotBlank() && token.isNotBlank()) ApiHttp.call("POST", "devices/unregister", JSONObject().put("fcmToken", token), auth)
        } catch (e: Exception) { Log.w(TAG, "Unregister failed: ${e.message}") }
    }

    /** Asks the server to push a test notification to this user's phones. Returns a message for the UI. */
    suspend fun sendServerTest(context: Context): String = try {
        val auth = AppPreferences(context).authToken.first()
        if (auth.isBlank()) "Sign in online to use server push"
        else {
            val res = ApiHttp.call("POST", "devices/test", JSONObject(), auth)
            when {
                res.ok -> "Server push sent"
                res.code == 503 -> "Server push isn't configured yet (Firebase key missing on the server)"
                else -> "Server push failed (HTTP ${res.code})"
            }
        }
    } catch (e: Exception) { "Server not reachable" }
}
