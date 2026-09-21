package com.fitlog.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val ssoProvider: String = "email",
    val preferredLanguage: String = "en",
    val notificationsEnabled: Boolean = true
)

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val name: String,
    val dateTime: Long,
    val durationSeconds: Int,
    val notes: String = "",
    val syncStatus: String = "PENDING"
)

@Entity(tableName = "workout_sets")
data class WorkoutSetEntity(
    @PrimaryKey val id: String,
    val workoutId: String,
    val exerciseId: String,
    val setNumber: Int,
    val reps: Int,
    val weightKg: Float,
    val syncStatus: String = "PENDING"
)

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String = "Strength"
)

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1") fun observeUser(): Flow<UserEntity?>
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE) suspend fun upsert(user: UserEntity)
}

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY dateTime DESC") fun observeWorkouts(): Flow<List<WorkoutEntity>>
    @Query("SELECT * FROM workouts WHERE id = :id LIMIT 1") suspend fun getById(id: String): WorkoutEntity?
    @Query("SELECT * FROM workout_sets WHERE workoutId = :workoutId ORDER BY setNumber") suspend fun getSets(workoutId: String): List<WorkoutSetEntity>
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE) suspend fun upsert(workout: WorkoutEntity)
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE) suspend fun upsertSets(sets: List<WorkoutSetEntity>)
}

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY name") fun observeExercises(): Flow<List<ExerciseEntity>>
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE) suspend fun insertAll(items: List<ExerciseEntity>)
}

@Database(entities = [UserEntity::class, WorkoutEntity::class, WorkoutSetEntity::class, ExerciseEntity::class], version = 1, exportSchema = false)
abstract class FitLogDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile private var INSTANCE: FitLogDatabase? = null
        fun get(context: Context): FitLogDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, FitLogDatabase::class.java, "fitlog.db").build().also { INSTANCE = it }
        }
    }
}
