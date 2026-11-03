package com.fitlog.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J6\u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\u00032\u0014\b\u0001\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ&\u0010\f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\r0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ \u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ2\u0010\u0010\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\r0\u00032\n\b\u0003\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0014\b\u0001\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J*\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\u0014\b\u0001\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J4\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u00052\u0014\b\u0001\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0018J6\u0010\u0019\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00040\u00032\u0014\b\u0001\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\u001a"}, d2 = {"Lcom/fitlog/app/data/FitLogApi;", "", "createWorkout", "Lretrofit2/Response;", "", "", "workout", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWorkout", "", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExercises", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMe", "getWorkouts", "since", "", "(Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerDevice", "body", "updateSettings", "updateWorkout", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyFirebaseToken", "app_debug"})
public abstract interface FitLogApi {
    
    @retrofit2.http.POST(value = "/auth/verify")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object verifyFirebaseToken(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
    @retrofit2.http.GET(value = "/users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMe(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
    @retrofit2.http.PATCH(value = "/users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSettings(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "/workouts")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWorkouts(@retrofit2.http.Query(value = "since")
    @org.jetbrains.annotations.Nullable()
    java.lang.Long since, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<java.util.Map<java.lang.String, java.lang.Object>>>> $completion);
    
    @retrofit2.http.POST(value = "/workouts")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createWorkout(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> workout, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
    @retrofit2.http.PATCH(value = "/workouts/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateWorkout(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> workout, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.DELETE(value = "/workouts/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteWorkout(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "/exercises")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getExercises(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<java.util.Map<java.lang.String, java.lang.Object>>>> $completion);
    
    @retrofit2.http.POST(value = "/devices/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object registerDevice(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}