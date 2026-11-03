package com.fitlog.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"NOTIFICATION_CHANNEL_ID", "", "TAG", "ensureNotificationChannel", "", "context", "Landroid/content/Context;", "showFitLogNotification", "title", "body", "app_debug"})
public final class NotificationsKt {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String NOTIFICATION_CHANNEL_ID = "fitlog_default";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FitLogPush";
    
    /**
     * Android 8+ needs a channel before any notification can be shown.
     */
    public static final void ensureNotificationChannel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Shows a notification (silently skipped when the user has not allowed notifications on Android 13+).
     */
    public static final void showFitLogNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body) {
    }
}