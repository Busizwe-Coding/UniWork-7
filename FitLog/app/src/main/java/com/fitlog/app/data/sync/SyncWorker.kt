package com.fitlog.app.data.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.fitlog.app.FitLogApp

/**
 * Runs [WorkoutRepository.syncPendingWorkouts] in the background, only when
 * a network connection is available (see the NetworkType.CONNECTED
 * constraint below). This is what turns "offline mode" into "offline mode
 * WITH sync" per the Part 1 design document, Section 2.5 — the user never
 * has to think about it, it just happens once they're back online.
 */
class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val repository = (applicationContext as FitLogApp).container.workoutRepository
        val result = repository.syncPendingWorkouts()
        return if (result.isSuccess) Result.success() else Result.retry()
    }

    companion object {
        private const val WORK_NAME = "fitlog_sync"

        /** Enqueues a one-off sync as soon as the network constraint is met. */
        fun enqueue(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(WORK_NAME, ExistingWorkPolicy.REPLACE, request)
        }
    }
}
