package com.fitlog.app.ui.auth;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\tH\u0002J0\u0010\u0014\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0082@\u00a2\u0006\u0002\u0010\u001bJ$\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120 H\u0082@\u00a2\u0006\u0002\u0010!J$\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\t2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120 J\u001c\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020\'2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120 J,\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\t2\u0006\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\t2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120 R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/fitlog/app/ui/auth/LoginViewModel;", "Landroidx/lifecycle/ViewModel;", "prefs", "Lcom/fitlog/app/data/AppPreferences;", "repository", "Lcom/fitlog/app/data/FitLogRepository;", "(Lcom/fitlog/app/data/AppPreferences;Lcom/fitlog/app/data/FitLogRepository;)V", "_errorMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isLoading", "", "errorMessage", "Lkotlinx/coroutines/flow/StateFlow;", "getErrorMessage", "()Lkotlinx/coroutines/flow/StateFlow;", "isLoading", "fail", "", "message", "finishLogin", "local", "Lcom/fitlog/app/data/UserEntity;", "api", "Lcom/fitlog/app/data/ApiUser;", "hash", "provider", "(Lcom/fitlog/app/data/UserEntity;Lcom/fitlog/app/data/ApiUser;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleGoogleCredential", "result", "Landroidx/credentials/GetCredentialResponse;", "onSuccess", "Lkotlin/Function0;", "(Landroidx/credentials/GetCredentialResponse;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "email", "pass", "loginWithGoogle", "context", "Landroid/content/Context;", "register", "name", "Companion", "app_debug"})
public final class LoginViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.fitlog.app.data.AppPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fitlog.app.data.FitLogRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String TAG = "FitLogAuth";
    @org.jetbrains.annotations.NotNull()
    @java.lang.Deprecated()
    public static final java.lang.String GOOGLE_WEB_CLIENT_ID = "YOUR_WEB_CLIENT_ID.apps.googleusercontent.com";
    @org.jetbrains.annotations.NotNull()
    private static final com.fitlog.app.ui.auth.LoginViewModel.Companion Companion = null;
    
    public LoginViewModel(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.AppPreferences prefs, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getErrorMessage() {
        return null;
    }
    
    /**
     * Normal email + password login. If the REST API is configured and reachable the server is asked first
     * (and a local account made before the API existed is registered on the server once). Without a
     * connection the local account is used, so the app keeps working offline.
     */
    public final void login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String pass, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    public final void register(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String pass, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    /**
     * Make sure a local account exists for this server account, remember the session token, set current user.
     */
    private final java.lang.Object finishLogin(com.fitlog.app.data.UserEntity local, com.fitlog.app.data.ApiUser api, java.lang.String hash, java.lang.String provider, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void fail(java.lang.String message) {
    }
    
    /**
     * Google single sign-on. Credential Manager shows Google's own account picker; we get back a Google
     * ID token (a signed proof of who the user is), read the verified name + email from it and then
     * find-or-create the local account exactly like normal registration does. Email/password login is
     * untouched and still works side by side.
     */
    public final void loginWithGoogle(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    private final java.lang.Object handleGoogleCredential(androidx.credentials.GetCredentialResponse result, kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/fitlog/app/ui/auth/LoginViewModel$Companion;", "", "()V", "GOOGLE_WEB_CLIENT_ID", "", "TAG", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}