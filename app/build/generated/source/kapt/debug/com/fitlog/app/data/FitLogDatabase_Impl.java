package com.fitlog.app.data;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class FitLogDatabase_Impl extends FitLogDatabase {
  private volatile UserDao _userDao;

  private volatile WorkoutDao _workoutDao;

  private volatile ExerciseDao _exerciseDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(2, "f9634134b5f3f5890964fec44f62de1d", "e539c37bd6ff0b20dfc282a470f67d73") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `passwordHash` TEXT NOT NULL, `ssoProvider` TEXT NOT NULL, `preferredLanguage` TEXT NOT NULL, `notificationsEnabled` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE UNIQUE INDEX IF NOT EXISTS `index_users_email` ON `users` (`email`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `workouts` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `name` TEXT NOT NULL, `dateTime` INTEGER NOT NULL, `durationSeconds` INTEGER NOT NULL, `notes` TEXT NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `workout_sets` (`id` TEXT NOT NULL, `workoutId` TEXT NOT NULL, `exerciseId` TEXT NOT NULL, `setNumber` INTEGER NOT NULL, `reps` INTEGER NOT NULL, `weightKg` REAL NOT NULL, `syncStatus` TEXT NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `exercises` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, PRIMARY KEY(`id`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f9634134b5f3f5890964fec44f62de1d')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `users`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `workouts`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `workout_sets`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `exercises`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(7);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("ssoProvider", new TableInfo.Column("ssoProvider", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("preferredLanguage", new TableInfo.Column("preferredLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("notificationsEnabled", new TableInfo.Column("notificationsEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_email", true, Arrays.asList("email"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(connection, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenDelegate.ValidationResult(false, "users(com.fitlog.app.data.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final Map<String, TableInfo.Column> _columnsWorkouts = new HashMap<String, TableInfo.Column>(7);
        _columnsWorkouts.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("dateTime", new TableInfo.Column("dateTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("durationSeconds", new TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkouts.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysWorkouts = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesWorkouts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkouts = new TableInfo("workouts", _columnsWorkouts, _foreignKeysWorkouts, _indicesWorkouts);
        final TableInfo _existingWorkouts = TableInfo.read(connection, "workouts");
        if (!_infoWorkouts.equals(_existingWorkouts)) {
          return new RoomOpenDelegate.ValidationResult(false, "workouts(com.fitlog.app.data.WorkoutEntity).\n"
                  + " Expected:\n" + _infoWorkouts + "\n"
                  + " Found:\n" + _existingWorkouts);
        }
        final Map<String, TableInfo.Column> _columnsWorkoutSets = new HashMap<String, TableInfo.Column>(7);
        _columnsWorkoutSets.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSets.put("workoutId", new TableInfo.Column("workoutId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSets.put("exerciseId", new TableInfo.Column("exerciseId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSets.put("setNumber", new TableInfo.Column("setNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSets.put("reps", new TableInfo.Column("reps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSets.put("weightKg", new TableInfo.Column("weightKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWorkoutSets.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysWorkoutSets = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesWorkoutSets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWorkoutSets = new TableInfo("workout_sets", _columnsWorkoutSets, _foreignKeysWorkoutSets, _indicesWorkoutSets);
        final TableInfo _existingWorkoutSets = TableInfo.read(connection, "workout_sets");
        if (!_infoWorkoutSets.equals(_existingWorkoutSets)) {
          return new RoomOpenDelegate.ValidationResult(false, "workout_sets(com.fitlog.app.data.WorkoutSetEntity).\n"
                  + " Expected:\n" + _infoWorkoutSets + "\n"
                  + " Found:\n" + _existingWorkoutSets);
        }
        final Map<String, TableInfo.Column> _columnsExercises = new HashMap<String, TableInfo.Column>(3);
        _columnsExercises.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExercises.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysExercises = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesExercises = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExercises = new TableInfo("exercises", _columnsExercises, _foreignKeysExercises, _indicesExercises);
        final TableInfo _existingExercises = TableInfo.read(connection, "exercises");
        if (!_infoExercises.equals(_existingExercises)) {
          return new RoomOpenDelegate.ValidationResult(false, "exercises(com.fitlog.app.data.ExerciseEntity).\n"
                  + " Expected:\n" + _infoExercises + "\n"
                  + " Found:\n" + _existingExercises);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users", "workouts", "workout_sets", "exercises");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "users", "workouts", "workout_sets", "exercises");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WorkoutDao.class, WorkoutDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExerciseDao.class, ExerciseDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public WorkoutDao workoutDao() {
    if (_workoutDao != null) {
      return _workoutDao;
    } else {
      synchronized(this) {
        if(_workoutDao == null) {
          _workoutDao = new WorkoutDao_Impl(this);
        }
        return _workoutDao;
      }
    }
  }

  @Override
  public ExerciseDao exerciseDao() {
    if (_exerciseDao != null) {
      return _exerciseDao;
    } else {
      synchronized(this) {
        if(_exerciseDao == null) {
          _exerciseDao = new ExerciseDao_Impl(this);
        }
        return _exerciseDao;
      }
    }
  }
}
