package com.fitlog.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0001,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0013\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0014J.\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\t2\u0006\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00102\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J*\u0010\u001f\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\t0 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\b2\u0006\u0010\u0017\u001a\u00020\u0010J$\u0010#\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020!2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00160\tH\u0086@\u00a2\u0006\u0002\u0010&J\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\t0\b2\u0006\u0010\u0017\u001a\u00020\u0010J\u0016\u0010(\u001a\u00020\u000e2\u0006\u0010)\u001a\u00020\u001dH\u0086@\u00a2\u0006\u0002\u0010*J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\t0\b2\u0006\u0010\u0017\u001a\u00020\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006-"}, d2 = {"Lcom/fitlog/app/data/FitLogRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "db", "Lcom/fitlog/app/data/FitLogDatabase;", "exercises", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/fitlog/app/data/ExerciseEntity;", "getExercises", "()Lkotlinx/coroutines/flow/Flow;", "deleteSet", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWorkout", "ensureExercisesSeeded", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentSetsForExercise", "Lcom/fitlog/app/data/WorkoutSetEntity;", "userId", "exerciseId", "limit", "", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserByEmail", "Lcom/fitlog/app/data/UserEntity;", "email", "getWorkout", "Lkotlin/Pair;", "Lcom/fitlog/app/data/WorkoutEntity;", "observeUser", "saveWorkout", "workout", "sets", "(Lcom/fitlog/app/data/WorkoutEntity;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setsForUser", "upsertUser", "user", "(Lcom/fitlog/app/data/UserEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "workoutsForUser", "Companion", "app_debug"})
public final class FitLogRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.fitlog.app.data.FitLogDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.fitlog.app.data.ExerciseEntity>> exercises = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.fitlog.app.data.ExerciseEntity> DEFAULT_EXERCISES = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.FitLogRepository.Companion Companion = null;
    
    public FitLogRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.fitlog.app.data.ExerciseEntity>> getExercises() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.fitlog.app.data.WorkoutEntity>> workoutsForUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.fitlog.app.data.WorkoutSetEntity>> setsForUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.fitlog.app.data.UserEntity> observeUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserByEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fitlog.app.data.UserEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object upsertUser(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.UserEntity user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveWorkout(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.WorkoutEntity workout, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutSetEntity> sets, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getWorkout(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Pair<com.fitlog.app.data.WorkoutEntity, ? extends java.util.List<com.fitlog.app.data.WorkoutSetEntity>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteWorkout(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteSet(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * "Last time" data for auto-fill — most recent logged sets for this
     * exercise, newest first.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getRecentSetsForExercise(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String exerciseId, int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fitlog.app.data.WorkoutSetEntity>> $completion) {
        return null;
    }
    
    /**
     * Reference exercise catalogue — not user-specific, safe to reseed on
     * every launch (REPLACE is idempotent for identical rows).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object ensureExercisesSeeded(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/fitlog/app/data/FitLogRepository$Companion;", "", "()V", "DEFAULT_EXERCISES", "", "Lcom/fitlog/app/data/ExerciseEntity;", "getDEFAULT_EXERCISES", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fitlog.app.data.ExerciseEntity> getDEFAULT_EXERCISES() {
            return null;
        }
    }
}