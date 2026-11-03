package com.fitlog.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J6\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000b0\u000e\u00a8\u0006\u0010"}, d2 = {"Lcom/fitlog/app/data/ExportUtil;", "", "()V", "csv", "", "value", "exportAndShare", "", "context", "Landroid/content/Context;", "workouts", "", "Lcom/fitlog/app/data/WorkoutEntity;", "setsByWorkout", "", "Lcom/fitlog/app/data/WorkoutSetEntity;", "app_debug"})
public final class ExportUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.ExportUtil INSTANCE = null;
    
    private ExportUtil() {
        super();
    }
    
    public final void exportAndShare(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.util.List<com.fitlog.app.data.WorkoutSetEntity>> setsByWorkout) {
    }
    
    private final java.lang.String csv(java.lang.String value) {
        return null;
    }
}