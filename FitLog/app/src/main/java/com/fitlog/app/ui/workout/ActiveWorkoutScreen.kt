package com.fitlog.app.ui.workout

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
import com.fitlog.app.data.sync.SyncWorker
import com.fitlog.app.di.ViewModelFactory
import com.fitlog.app.ui.theme.*

// A small built-in exercise list keeps the prototype usable offline with no
// setup. The REST API's GET /exercises endpoint (see ApiService) is the
// source of truth for the full catalogue and is cached into Room via
// WorkoutRepository.cacheExercises for the final build.
private val QUICK_EXERCISES = listOf(
    "ex-bench" to "Bench Press",
    "ex-incline" to "Incline Dumbbell Press",
    "ex-ohp" to "Overhead Press",
    "ex-fly" to "Cable Fly",
)

@Composable
fun ActiveWorkoutScreen(userId: String, onFinished: () -> Unit) {
    val context = LocalContext.current
    val app = context.applicationContext as FitLogApp
    val viewModel: WorkoutViewModel = viewModel(
        factory = ViewModelFactory {
            WorkoutViewModel(app.container.workoutRepository, userId, enqueueSync = { SyncWorker.enqueue(context) })
        },
    )
    val state by viewModel.uiState.collectAsState()

    var selectedExercise by remember { mutableStateOf(QUICK_EXERCISES.first()) }
    var reps by remember { mutableStateOf("8") }
    var weight by remember { mutableStateOf("20") }

    LaunchedEffect(state.saved) {
        if (state.saved) onFinished()
    }

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().height(64.dp)
                .background(Brush.horizontalGradient(listOf(FitLogGreen, FitLogGreenDark))),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(state.workoutName, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 20.dp))
        }

        Column(Modifier.padding(20.dp).weight(1f)) {
            Text("Exercise", style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QUICK_EXERCISES.forEach { ex ->
                    val selected = ex == selectedExercise
                    Surface(
                        color = if (selected) FitLogGreen else Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) FitLogGreen else BorderGrey),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.clickableRow { selectedExercise = ex },
                    ) {
                        Text(ex.second, color = if (selected) Color.White else Color.Black, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("KG") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = reps, onValueChange = { reps = it }, label = { Text("Reps") }, modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    val repsInt = reps.toIntOrNull()
                    val weightFloat = weight.toFloatOrNull()
                    if (repsInt != null && repsInt > 0 && weightFloat != null && weightFloat >= 0) {
                        viewModel.logSet(selectedExercise.second, selectedExercise.first, repsInt, weightFloat)
                    }
                    // Invalid input is simply ignored rather than crashing the
                    // app, per the brief's "handle invalid inputs... without
                    // crashing" requirement.
                },
                colors = ButtonDefaults.buttonColors(containerColor = FitLogGreenLight, contentColor = FitLogGreenDark),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("+ Add set", fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(20.dp))
            Text("Logged this session", style = MaterialTheme.typography.labelLarge)
            LazyColumn(Modifier.weight(1f)) {
                items(state.loggedSets) { s ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    ) {
                        Row(
                            Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text("${s.exerciseName} — set ${s.setNumber}")
                            Text("${s.weightKg}kg x ${s.reps}")
                        }
                    }
                }
            }
        }

        Button(
            onClick = viewModel::finishWorkout,
            enabled = state.loggedSets.isNotEmpty() && !state.isSaving,
            colors = ButtonDefaults.buttonColors(containerColor = FitLogGreen),
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.fillMaxWidth().height(56.dp),
        ) {
            if (state.isSaving) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
            } else {
                Text("FINISH WORKOUT", fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun Modifier.clickableRow(onClick: () -> Unit): Modifier =
    this.then(androidx.compose.foundation.clickable(onClick = onClick))
