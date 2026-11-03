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
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<UserEntity> __insertAdapterOfUserEntity;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfUserEntity = new EntityInsertAdapter<UserEntity>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`name`,`email`,`passwordHash`,`ssoProvider`,`preferredLanguage`,`notificationsEnabled`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final UserEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindText(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getEmail());
        }
        if (entity.getPasswordHash() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getPasswordHash());
        }
        if (entity.getSsoProvider() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getSsoProvider());
        }
        if (entity.getPreferredLanguage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getPreferredLanguage());
        }
        final int _tmp = entity.getNotificationsEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp);
      }
    };
  }

  @Override
  public Object upsert(final UserEntity user, final Continuation<? super Unit> $completion) {
    if (user == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfUserEntity.insert(_connection, user);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Flow<UserEntity> observeUserById(final String id) {
    final String _sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
    return FlowUtil.createFlow(__db, false, new String[] {"users"}, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, id);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfPasswordHash = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passwordHash");
        final int _columnIndexOfSsoProvider = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ssoProvider");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfNotificationsEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationsEnabled");
        final UserEntity _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          final String _tmpPasswordHash;
          if (_stmt.isNull(_columnIndexOfPasswordHash)) {
            _tmpPasswordHash = null;
          } else {
            _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash);
          }
          final String _tmpSsoProvider;
          if (_stmt.isNull(_columnIndexOfSsoProvider)) {
            _tmpSsoProvider = null;
          } else {
            _tmpSsoProvider = _stmt.getText(_columnIndexOfSsoProvider);
          }
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          final boolean _tmpNotificationsEnabled;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfNotificationsEnabled));
          _tmpNotificationsEnabled = _tmp != 0;
          _result = new UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpSsoProvider,_tmpPreferredLanguage,_tmpNotificationsEnabled);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Object getByEmail(final String email, final Continuation<? super UserEntity> $completion) {
    final String _sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (email == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, email);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfEmail = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "email");
        final int _columnIndexOfPasswordHash = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "passwordHash");
        final int _columnIndexOfSsoProvider = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ssoProvider");
        final int _columnIndexOfPreferredLanguage = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "preferredLanguage");
        final int _columnIndexOfNotificationsEnabled = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "notificationsEnabled");
        final UserEntity _result;
        if (_stmt.step()) {
          final String _tmpId;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpId = null;
          } else {
            _tmpId = _stmt.getText(_columnIndexOfId);
          }
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpEmail;
          if (_stmt.isNull(_columnIndexOfEmail)) {
            _tmpEmail = null;
          } else {
            _tmpEmail = _stmt.getText(_columnIndexOfEmail);
          }
          final String _tmpPasswordHash;
          if (_stmt.isNull(_columnIndexOfPasswordHash)) {
            _tmpPasswordHash = null;
          } else {
            _tmpPasswordHash = _stmt.getText(_columnIndexOfPasswordHash);
          }
          final String _tmpSsoProvider;
          if (_stmt.isNull(_columnIndexOfSsoProvider)) {
            _tmpSsoProvider = null;
          } else {
            _tmpSsoProvider = _stmt.getText(_columnIndexOfSsoProvider);
          }
          final String _tmpPreferredLanguage;
          if (_stmt.isNull(_columnIndexOfPreferredLanguage)) {
            _tmpPreferredLanguage = null;
          } else {
            _tmpPreferredLanguage = _stmt.getText(_columnIndexOfPreferredLanguage);
          }
          final boolean _tmpNotificationsEnabled;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfNotificationsEnabled));
          _tmpNotificationsEnabled = _tmp != 0;
          _result = new UserEntity(_tmpId,_tmpName,_tmpEmail,_tmpPasswordHash,_tmpSsoProvider,_tmpPreferredLanguage,_tmpNotificationsEnabled);
        } else {
          _result = null;
        }
        return _result;
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
