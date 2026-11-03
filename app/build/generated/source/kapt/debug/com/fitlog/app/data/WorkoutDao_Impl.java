package com.fitlog.app.data;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.coroutines.FlowUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class WorkoutDao_Impl implements WorkoutDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<WorkoutEntity> __insertAdapterOfWorkoutEntity;

  private final EntityInsertAdapter<WorkoutSetEntity> __insertAdapterOfWorkoutSetEntity;

  public WorkoutDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfWorkoutEntity = new EntityInsertAdapter<WorkoutEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workouts` (`id`,`userId`,`name`,`dateTime`,`durationSeconds`,`notes`,`syncStatus`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final WorkoutEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getUserId());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getName());
        }
        statement.bindLong(4, entity.getDateTime());
        statement.bindLong(5, entity.getDurationSeconds());
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getNotes());
        }
        if (entity.getSyncStatus() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getSyncStatus());
        }
      }
    };
    this.__insertAdapterOfWorkoutSetEntity = new EntityInsertAdapter<WorkoutSetEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `workout_sets` (`id`,`workoutId`,`exerciseId`,`setNumber`,`reps`,`weightKg`,`syncStatus`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final WorkoutSetEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getWorkoutId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getWorkoutId());
        }
        if (entity.getExerciseId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getExerciseId());
        }
        statement.bindLong(4, entity.getSetNumber());
        statement.bindLong(5, entity.getReps());
        statement.bindDouble(6, entity.getWeightKg());
        if (entity.getSyncStatus() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getSyncStatus());
        }
      }
    };
  }

  @Override
  public Object upsert(final WorkoutEntity workout, final Continuation<? super Unit> $completion) {
    if (workout == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfWorkoutEntity.insert(_connection, workout);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object upsertSets(final List<WorkoutSetEntity> sets,
      final Continuation<? super Unit> $completion) {
    if (sets == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfWorkoutSetEntity.insert(_connection, sets);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<List<WorkoutEntity>> observeWorkouts(final String userId) {
    final String _sql = "SELECT * FROM workouts WHERE userId = ? ORDER BY dateTime DESC";
    return FlowUtil.createFlow(__db, false, new String[] {"workouts"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (userId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, userId);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDateTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateTime");
        final int _columnIndexOfDurationSeconds = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationSeconds");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfSyncStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "syncStatus");
        final List<WorkoutEntity> _result = new ArrayList<WorkoutEntity>();
        while (_stmt.step()) {
          final WorkoutEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpUserId;
          if (_stmt.isNull(_columnIndexOfUserId)) {
            _tmpUserId = null;
          } else {
            _tmpUserId = _stmt.getText(_columnIndexOfUserId);
          }
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final long _tmpDateTime;
          _tmpDateTime = _stmt.getLong(_columnIndexOfDateTime);
          final int _tmpDurationSeconds;
          _tmpDurationSeconds = (int) (_stmt.getLong(_columnIndexOfDurationSeconds));
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          final String _tmpSyncStatus;
          if (_stmt.isNull(_columnIndexOfSyncStatus)) {
            _tmpSyncStatus = null;
          } else {
            _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus);
          }
          _item = new WorkoutEntity(_tmpId,_tmpUserId,_tmpName,_tmpDateTime,_tmpDurationSeconds,_tmpNotes,_tmpSyncStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getById(final String id, final Continuation<? super WorkoutEntity> $completion) {
    final String _sql = "SELECT * FROM workouts WHERE id = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfUserId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userId");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfDateTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dateTime");
        final int _columnIndexOfDurationSeconds = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "durationSeconds");
        final int _columnIndexOfNotes = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notes");
        final int _columnIndexOfSyncStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "syncStatus");
        final WorkoutEntity _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpUserId;
          if (_stmt.isNull(_columnIndexOfUserId)) {
            _tmpUserId = null;
          } else {
            _tmpUserId = _stmt.getText(_columnIndexOfUserId);
          }
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final long _tmpDateTime;
          _tmpDateTime = _stmt.getLong(_columnIndexOfDateTime);
          final int _tmpDurationSeconds;
          _tmpDurationSeconds = (int) (_stmt.getLong(_columnIndexOfDurationSeconds));
          final String _tmpNotes;
          if (_stmt.isNull(_columnIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _stmt.getText(_columnIndexOfNotes);
          }
          final String _tmpSyncStatus;
          if (_stmt.isNull(_columnIndexOfSyncStatus)) {
            _tmpSyncStatus = null;
          } else {
            _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus);
          }
          _result = new WorkoutEntity(_tmpId,_tmpUserId,_tmpName,_tmpDateTime,_tmpDurationSeconds,_tmpNotes,_tmpSyncStatus);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getSets(final String workoutId,
      final Continuation<? super List<WorkoutSetEntity>> $completion) {
    final String _sql = "SELECT * FROM workout_sets WHERE workoutId = ? ORDER BY setNumber";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (workoutId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, workoutId);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWorkoutId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "workoutId");
        final int _columnIndexOfExerciseId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exerciseId");
        final int _columnIndexOfSetNumber = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "setNumber");
        final int _columnIndexOfReps = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reps");
        final int _columnIndexOfWeightKg = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weightKg");
        final int _columnIndexOfSyncStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "syncStatus");
        final List<WorkoutSetEntity> _result = new ArrayList<WorkoutSetEntity>();
        while (_stmt.step()) {
          final WorkoutSetEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpWorkoutId;
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null;
          } else {
            _tmpWorkoutId = _stmt.getText(_columnIndexOfWorkoutId);
          }
          final String _tmpExerciseId;
          if (_stmt.isNull(_columnIndexOfExerciseId)) {
            _tmpExerciseId = null;
          } else {
            _tmpExerciseId = _stmt.getText(_columnIndexOfExerciseId);
          }
          final int _tmpSetNumber;
          _tmpSetNumber = (int) (_stmt.getLong(_columnIndexOfSetNumber));
          final int _tmpReps;
          _tmpReps = (int) (_stmt.getLong(_columnIndexOfReps));
          final float _tmpWeightKg;
          _tmpWeightKg = (float) (_stmt.getDouble(_columnIndexOfWeightKg));
          final String _tmpSyncStatus;
          if (_stmt.isNull(_columnIndexOfSyncStatus)) {
            _tmpSyncStatus = null;
          } else {
            _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus);
          }
          _item = new WorkoutSetEntity(_tmpId,_tmpWorkoutId,_tmpExerciseId,_tmpSetNumber,_tmpReps,_tmpWeightKg,_tmpSyncStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Flow<List<WorkoutSetEntity>> observeSetsForUser(final String userId) {
    final String _sql = "\n"
            + "        SELECT ws.* FROM workout_sets ws\n"
            + "        INNER JOIN workouts w ON w.id = ws.workoutId\n"
            + "        WHERE w.userId = ?\n"
            + "    ";
    return FlowUtil.createFlow(__db, false, new String[] {"workout_sets",
        "workouts"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (userId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, userId);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWorkoutId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "workoutId");
        final int _columnIndexOfExerciseId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exerciseId");
        final int _columnIndexOfSetNumber = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "setNumber");
        final int _columnIndexOfReps = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reps");
        final int _columnIndexOfWeightKg = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weightKg");
        final int _columnIndexOfSyncStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "syncStatus");
        final List<WorkoutSetEntity> _result = new ArrayList<WorkoutSetEntity>();
        while (_stmt.step()) {
          final WorkoutSetEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpWorkoutId;
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null;
          } else {
            _tmpWorkoutId = _stmt.getText(_columnIndexOfWorkoutId);
          }
          final String _tmpExerciseId;
          if (_stmt.isNull(_columnIndexOfExerciseId)) {
            _tmpExerciseId = null;
          } else {
            _tmpExerciseId = _stmt.getText(_columnIndexOfExerciseId);
          }
          final int _tmpSetNumber;
          _tmpSetNumber = (int) (_stmt.getLong(_columnIndexOfSetNumber));
          final int _tmpReps;
          _tmpReps = (int) (_stmt.getLong(_columnIndexOfReps));
          final float _tmpWeightKg;
          _tmpWeightKg = (float) (_stmt.getDouble(_columnIndexOfWeightKg));
          final String _tmpSyncStatus;
          if (_stmt.isNull(_columnIndexOfSyncStatus)) {
            _tmpSyncStatus = null;
          } else {
            _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus);
          }
          _item = new WorkoutSetEntity(_tmpId,_tmpWorkoutId,_tmpExerciseId,_tmpSetNumber,_tmpReps,_tmpWeightKg,_tmpSyncStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getRecentSetsForExercise(final String userId, final String exerciseId,
      final int limit, final Continuation<? super List<WorkoutSetEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT ws.* FROM workout_sets ws\n"
            + "        INNER JOIN workouts w ON w.id = ws.workoutId\n"
            + "        WHERE w.userId = ? AND ws.exerciseId = ?\n"
            + "        ORDER BY w.dateTime DESC, ws.setNumber DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (userId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, userId);
        }
        _argIndex = 2;
        if (exerciseId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, exerciseId);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, limit);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfWorkoutId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "workoutId");
        final int _columnIndexOfExerciseId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "exerciseId");
        final int _columnIndexOfSetNumber = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "setNumber");
        final int _columnIndexOfReps = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "reps");
        final int _columnIndexOfWeightKg = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weightKg");
        final int _columnIndexOfSyncStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "syncStatus");
        final List<WorkoutSetEntity> _result = new ArrayList<WorkoutSetEntity>();
        while (_stmt.step()) {
          final WorkoutSetEntity _item;
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpWorkoutId;
          if (_stmt.isNull(_columnIndexOfWorkoutId)) {
            _tmpWorkoutId = null;
          } else {
            _tmpWorkoutId = _stmt.getText(_columnIndexOfWorkoutId);
          }
          final String _tmpExerciseId;
          if (_stmt.isNull(_columnIndexOfExerciseId)) {
            _tmpExerciseId = null;
          } else {
            _tmpExerciseId = _stmt.getText(_columnIndexOfExerciseId);
          }
          final int _tmpSetNumber;
          _tmpSetNumber = (int) (_stmt.getLong(_columnIndexOfSetNumber));
          final int _tmpReps;
          _tmpReps = (int) (_stmt.getLong(_columnIndexOfReps));
          final float _tmpWeightKg;
          _tmpWeightKg = (float) (_stmt.getDouble(_columnIndexOfWeightKg));
          final String _tmpSyncStatus;
          if (_stmt.isNull(_columnIndexOfSyncStatus)) {
            _tmpSyncStatus = null;
          } else {
            _tmpSyncStatus = _stmt.getText(_columnIndexOfSyncStatus);
          }
          _item = new WorkoutSetEntity(_tmpId,_tmpWorkoutId,_tmpExerciseId,_tmpSetNumber,_tmpReps,_tmpWeightKg,_tmpSyncStatus);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object deleteWorkout(final String id, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM workouts WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object deleteSetsForWorkout(final String id,
      final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM workout_sets WHERE workoutId = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object deleteSet(final String id, final Continuation<? super Unit> $completion) {
    final String _sql = "DELETE FROM workout_sets WHERE id = ?";
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        _stmt.step();
        return Unit.INSTANCE;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
