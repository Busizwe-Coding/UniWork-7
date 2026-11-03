package com.fitlog.app.data;

/**
 * Small JSON-over-HTTPS client (HttpURLConnection). Every request is logged under tag "FitLogApi".
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J6\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0004H\u0086@\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/fitlog/app/data/ApiHttp;", "", "()V", "TAG", "", "call", "Lcom/fitlog/app/data/HttpResult;", "method", "path", "body", "Lorg/json/JSONObject;", "token", "(Ljava/lang/String;Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ApiHttp {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FitLogApi";
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.ApiHttp INSTANCE = null;
    
    private ApiHttp() {
        super();
    }
    
    /**
     * Throws IOException when there is no connection (callers treat that as "offline").
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object call(@org.jetbrains.annotations.NotNull()
    java.lang.String method, @org.jetbrains.annotations.NotNull()
    java.lang.String path, @org.jetbrains.annotations.Nullable()
    org.json.JSONObject body, @org.jetbrains.annotations.Nullable()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fitlog.app.data.HttpResult> $completion) {
        return null;
    }
}