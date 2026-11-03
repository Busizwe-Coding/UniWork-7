package com.fitlog.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val passwordHash: String = "",
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
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun observeUserById(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserEntity)
}

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts WHERE userId = :userId ORDER BY dateTime DESC")
    fun observeWorkouts(userId: String): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): WorkoutEntity?

    @Query("SELECT * FROM workout_sets WHERE workoutId = :workoutId ORDER BY setNumber")
    suspend fun getSets(workoutId: String): List<WorkoutSetEntity>

    // All of a user's sets across every workout — powers Achievements
    // (total sets/volume) and CSV export without an extra round trip per
    // workout.
    @Query("""
        SELECT ws.* FROM workout_sets ws
        INNER JOIN workouts w ON w.id = ws.workoutId
        WHERE w.userId = :userId
    """)
    fun observeSetsForUser(userId: String): Flow<List<WorkoutSetEntity>>

    // "Last time" auto-fill: the most recent sets logged for this exercise
    // by this user, most recent workout first.
    @Query("""
        SELECT ws.* FROM workout_sets ws
        INNER JOIN workouts w ON w.id = ws.workoutId
        WHERE w.userId = :userId AND ws.exerciseId = :exerciseId
        ORDER BY w.dateTime DESC, ws.setNumber DESC
        LIMIT :limit
    """)
    suspend fun getRecentSetsForExercise(userId: String, exerciseId: String, limit: Int): List<WorkoutSetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(workout: WorkoutEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSets(sets: List<WorkoutSetEntity>)

    @Query("DELETE FROM workouts WHERE id = :id")
    suspend fun deleteWorkout(id: String)

    @Query("DELETE FROM workout_sets WHERE workoutId = :id")
    suspend fun deleteSetsForWorkout(id: String)

    @Query("DELETE FROM workout_sets WHERE id = :id")
    suspend fun deleteSet(id: String)
}

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises ORDER BY name")
    fun observeExercises(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ExerciseEntity>)
}

@Database(
    entities = [UserEntity::class, WorkoutEntity::class, WorkoutSetEntity::class, ExerciseEntity::class],
    version = 2,
    exportSchema = false
)
abstract class FitLogDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile private var INSTANCE: FitLogDatabase? = null
        fun get(context: Context): FitLogDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, FitLogDatabase::class.java, "fitlog.db")
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}
