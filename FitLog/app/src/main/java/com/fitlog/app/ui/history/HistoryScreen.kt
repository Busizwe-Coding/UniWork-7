package com.fitlog.app.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.fitlog.app.ui.home.HomeViewModel
import com.fitlog.app.ui.theme.FitLogGreen
import com.fitlog.app.ui.theme.FitLogGreenDark
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreen() {
    val app = LocalContext.current.applicationContext as FitLogApp
    // Reuses HomeViewModel's workout stream — both screens read the same
    // offline-first Room-backed list, just presented differently.
    val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory { HomeViewModel(app.container.workoutRepository) },
    )
    val workouts by viewModel.workouts.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().height(64.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text("History", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }

        if (workouts.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No workouts logged yet.", color = MaterialTheme.colorScheme.outline)
            }
            return@Column
        }

        LazyColumn(Modifier.padding(20.dp)) {
            items(workouts) { workout ->
                val dateStr = remember(workout.dateTime) {
                    SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(workout.dateTime)
                }
                Surface(
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text(workout.name, fontWeight = FontWeight.Bold)
                        Text(dateStr, color = MaterialTheme.colorScheme.outline)
                        Text(
                            "Sync status: ${workout.syncStatus.name}",
                            color = if (workout.syncStatus.name == "SYNCED") FitLogGreenDark else Color(0xFFC4A000),
                        )
                    }
                }
            }
        }
    }
}
