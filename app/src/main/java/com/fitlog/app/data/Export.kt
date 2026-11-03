package com.fitlog.app.data

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileWriter

//Exports workout history as CSV and hands it to the system share sheet.

object ExportUtil {

    fun exportAndShare(context: Context, workouts: List<WorkoutEntity>, setsByWorkout: Map<String, List<WorkoutSetEntity>>) {
        val file = File(context.cacheDir, "fitlog_export_${System.currentTimeMillis()}.csv")
        FileWriter(file).use { writer ->
            writer.append("Workout,Date,Duration (min),Exercise,Set,Reps,Weight (kg)\n")
            workouts.forEach { workout ->
                val sets = setsByWorkout[workout.id].orEmpty()
                if (sets.isEmpty()) {
                    writer.append("${csv(workout.name)},${workout.dateTime},${workout.durationSeconds / 60},,,,\n")
                } else {
                    sets.forEach { s ->
                        writer.append("${csv(workout.name)},${workout.dateTime},${workout.durationSeconds / 60},${csv(s.exerciseId)},${s.setNumber},${s.reps},${s.weightKg}\n")
                    }
                }
            }
        }

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Export FitLog data"))
    }

    private fun csv(value: String) = "\"${value.replace("\"", "\"\"")}\""
}

