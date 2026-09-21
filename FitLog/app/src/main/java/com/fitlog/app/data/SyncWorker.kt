package com.fitlog.app.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        // The REST API is intentionally optional in this runnable client build.
        // Pending Room records remain available for a future backend connection.
        return Result.success()
    }
}
