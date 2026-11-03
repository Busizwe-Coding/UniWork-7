package com.fitlog.app.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/fitlog/app/data/ReminderScheduler;", "", "()V", "WORK", "", "schedule", "", "context", "Landroid/content/Context;", "frequency", "app_debug"})
public final class ReminderScheduler {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String WORK = "fitlog_reminders";
    @org.jetbrains.annotations.NotNull()
    public static final com.fitlog.app.data.ReminderScheduler INSTANCE = null;
    
    private ReminderScheduler() {
        super();
    }
    
    /**
     * (Re)schedules the reminder for the chosen frequency at about 17:00 local time. "off" cancels it.
     */
    public final void schedule(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String frequency) {
    }
}