package com.fitlog.app.ui.settings

import androidx.compose.foundation.background
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

@Composable
fun SettingsScreen(onLoggedOut: () -> Unit, onOpenLanguage: () -> Unit) {
    val app = LocalContext.current.applicationContext as FitLogApp
    val viewModel: SettingsViewModel = viewModel(
        factory = ViewModelFactory {
            SettingsViewModel(app.container.settingsRepository, app.container.workoutRepository, app.container.authRepository)
        },
    )
    val state by viewModel.uiState.collectAsState()
    val syncState by viewModel.syncState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text("Settings", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }

        Column(modifier = Modifier.padding(20.dp)) {
            SectionLabel("PREFERENCES")

            SettingRow("Push notifications") {
                Switch(
                    checked = state.notificationsEnabled,
                    onCheckedChange = viewModel::setNotificationsEnabled,
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = FitLogGreen),
                )
            }
            SettingRow("Language", onClick = onOpenLanguage) {
                Text(languageLabel(state.language), color = TextGrey)
            }
            SettingRow("Units (metric)") {
                Switch(
                    checked = state.unitsMetric,
                    onCheckedChange = viewModel::setUnitsMetric,
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = FitLogGreen),
                )
            }

            Spacer(Modifier.height(16.dp))
            SectionLabel("DATA")
            SettingRow("Sync now", onClick = viewModel::syncNow) {
                when (val s = syncState) {
                    is SyncUiState.Syncing -> CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    is SyncUiState.Done -> Text("Synced ${s.count}", color = FitLogGreenDark)
                    is SyncUiState.Failed -> Text("Failed", color = FitLogRed)
                    SyncUiState.Idle -> Text(">", color = TextGrey)
                }
            }

            Spacer(Modifier.height(16.dp))
            Surface(
                color = FitLogRedLight,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().clickableRow {
                    viewModel.logOut()
                    onLoggedOut()
                },
            ) {
                Text(
                    "Log out",
                    color = FitLogRedDark,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(text, color = TextGrey, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(bottom = 8.dp, top = 8.dp))
}

@Composable
private fun SettingRow(label: String, onClick: (() -> Unit)? = null, trailing: @Composable () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGrey),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .let { if (onClick != null) it.clickableRow(onClick) else it },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label)
            trailing()
        }
    }
}

private fun Modifier.clickableRow(onClick: () -> Unit): Modifier =
    this.then(androidx.compose.foundation.clickable(onClick = onClick))

private fun languageLabel(code: String) = when (code) {
    "af" -> "Afrikaans"
    "zu" -> "isiZulu"
    else -> "English"
}
