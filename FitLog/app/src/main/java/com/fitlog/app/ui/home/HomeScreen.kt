package com.fitlog.app.ui.home

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
import com.fitlog.app.ui.theme.FitLogGreen
import com.fitlog.app.ui.theme.FitLogGreenDark
import com.fitlog.app.ui.theme.FitLogGreenLight
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(onStartWorkout: () -> Unit) {
    val app = LocalContext.current.applicationContext as FitLogApp
    val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory { HomeViewModel(app.container.workoutRepository) },
    )
    val workouts by viewModel.workouts.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text("FitLog", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }

        LazyColumn(modifier = Modifier.padding(20.dp)) {
            item {
                Text("Welcome back!", color = MaterialTheme.colorScheme.outline)
                Spacer(Modifier.height(12.dp))
                Surface(color = FitLogGreenLight, shape = RoundedCornerShape(14.dp)) {
                    Column(Modifier.padding(14.dp)) {
                        Text("This week", color = FitLogGreenDark, fontWeight = FontWeight.Bold)
                        Text("${workouts.size} workouts logged", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onStartWorkout,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FitLogGreen),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                ) {
                    Text("+ START WORKOUT", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(20.dp))
                Text("Recent workouts", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
            }

            if (workouts.isEmpty()) {
                item {
                    Text(
                        "No workouts yet — tap Start Workout to log your first session.",
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(vertical = 12.dp),
                    )
                }
            }

            items(workouts) { workout ->
                val dateStr = remember(workout.dateTime) {
                    SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(workout.dateTime)
                }
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text(workout.name, fontWeight = FontWeight.Bold)
                        Text("$dateStr • ${workout.syncStatus.name.lowercase()}", color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
        }
    }
}
