package com.fitlog.app

import android.app.Application
import android.util.Log
import com.fitlog.app.di.AppContainer

class FitLogApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
        Log.i(TAG, "FitLog application started — dependency container initialised")
    }

    companion object {
        private const val TAG = "FitLogApp"
    }
}
