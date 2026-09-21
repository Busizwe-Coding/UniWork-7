package com.fitlog.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.fitlog.app.ui.navigation.FitLogNavGraph
import com.fitlog.app.ui.theme.FitLogTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as FitLogApp
        val startLoggedIn = app.container.authRepository.isLoggedIn()

        // Apply the saved language preference (is eng on default)
        lifecycleScope.launch {
            val savedLanguage = app.container.settingsRepository.language.first()
            applyAppLocale(savedLanguage)
        }

        setContent {
            FitLogTheme {
                FitLogNavGraph(startLoggedIn = startLoggedIn)
            }
        }
    }

    private fun applyAppLocale(languageCode: String) {
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }
}
