package com.fitlog.app.data;

/**
 * Auth endpoints (backend/src/routes/auth.js).
 * - register/login send only the SHA-256+pepper password hash (server bcrypts it again).
 * - google() sends the Google ID token; the SERVER verifies it with Google before trusting it.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u0010J&\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0086@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/fitlog/app/data/AuthApi;", "", "()V", "google", "Lcom/fitlog/app/data/ApiResult;", "idToken", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "email", "passwordHash", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "post", "path", "body", "Lorg/json/JSONObject;", "(Ljava/lang/String;Lorg/json/JSONObject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AuthApi {
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.AuthApi INSTANCE = null;
    
    private AuthApi() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object register(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String passwordHash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fitlog.app.data.ApiResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String passwordHash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fitlog.app.data.ApiResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object google(@org.jetbrains.annotations.NotNull()
    java.lang.String idToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fitlog.app.data.ApiResult> $completion) {
        return null;
    }
    
    private final java.lang.Object post(java.lang.String path, org.json.JSONObject body, kotlin.coroutines.Continuation<? super com.fitlog.app.data.ApiResult> $completion) {
        return null;
    }
}