package com.fitlog.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WorkoutEntity::class, WorkoutSetEntity::class, ExerciseEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class FitLogDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile private var INSTANCE: FitLogDatabase? = null

        fun getInstance(context: Context): FitLogDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FitLogDatabase::class.java,
                    "fitlog.db",
                ).build().also { INSTANCE = it }
            }
    }
}
