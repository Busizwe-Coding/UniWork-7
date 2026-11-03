package com.fitlog.app.data;

/**
 * Best-effort upload of a finished workout. The server answers by pushing "Workout saved" to the user's phones.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2 = {"Lcom/fitlog/app/data/WorkoutApi;", "", "()V", "upload", "", "token", "", "workout", "Lorg/json/JSONObject;", "(Ljava/lang/String;Lorg/json/JSONObject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class WorkoutApi {
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.WorkoutApi INSTANCE = null;
    
    private WorkoutApi() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object upload(@org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    org.json.JSONObject workout, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}