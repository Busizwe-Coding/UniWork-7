package com.fitlog.app.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fitlog.app.FitLogApp
import com.fitlog.app.di.ViewModelFactory
import com.fitlog.app.ui.theme.*

/**
 * Lets the user pick the app's display language (Part 2 requirement:
 * "change their settings"; multi-language support per the Part 1 design
 * doc Section 2.7). Selecting a language updates the value in
 * SettingsRepository/DataStore immediately — applying it to Android's
 * per-app locale (AppCompatDelegate.setApplicationLocales) is wired in
 * MainActivity so every string resource re-resolves without a restart.
 */
@Composable
fun LanguageScreen(onBack: () -> Unit) {
    val app = LocalContext.current.applicationContext as FitLogApp
    val viewModel: SettingsViewModel = viewModel(
        factory = ViewModelFactory {
            SettingsViewModel(app.container.settingsRepository, app.container.workoutRepository, app.container.authRepository)
        },
    )
    val state by viewModel.uiState.collectAsState()
    val languages = listOf("en" to "English", "af" to "Afrikaans", "zu" to "isiZulu")

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text("Language", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }
        Column(Modifier.padding(20.dp)) {
            languages.forEach { (code, label) ->
                val selected = state.language == code
                Surface(
                    color = if (selected) FitLogGreenLight else Color.White,
                    border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) FitLogGreen else BorderGrey),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            viewModel.setLanguage(code)
                            onBack()
                        },
                ) {
                    Row(
                        Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(label, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
                        if (selected) Text("\u2713", color = FitLogGreenDark, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
