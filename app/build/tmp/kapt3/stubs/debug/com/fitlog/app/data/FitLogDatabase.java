package com.fitlog.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/fitlog/app/data/FitLogDatabase;", "Landroidx/room/RoomDatabase;", "()V", "exerciseDao", "Lcom/fitlog/app/data/ExerciseDao;", "userDao", "Lcom/fitlog/app/data/UserDao;", "workoutDao", "Lcom/fitlog/app/data/WorkoutDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.fitlog.app.data.UserEntity.class, com.fitlog.app.data.WorkoutEntity.class, com.fitlog.app.data.WorkoutSetEntity.class, com.fitlog.app.data.ExerciseEntity.class}, version = 2, exportSchema = false)
public abstract class FitLogDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.fitlog.app.data.FitLogDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.FitLogDatabase.Companion Companion = null;
    
    public FitLogDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fitlog.app.data.UserDao userDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fitlog.app.data.WorkoutDao workoutDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fitlog.app.data.ExerciseDao exerciseDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/fitlog/app/data/FitLogDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/fitlog/app/data/FitLogDatabase;", "get", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fitlog.app.data.FitLogDatabase get(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}