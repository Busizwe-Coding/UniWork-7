package com.fitlog.app;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u00b8\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\u001a\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u001a,\u0010\u0010\u001a\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0007\u001ax\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00122\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00172\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\r0#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a\u0016\u0010\'\u001a\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0007\u001a\u0010\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020\u0017H\u0007\u001a&\u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020-2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a\b\u0010/\u001a\u00020\rH\u0007\u001a\"\u00100\u001a\u00020\r2\u0006\u00101\u001a\u00020\t2\u0010\b\u0002\u00102\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010%H\u0007\u001a:\u00103\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u001f\u001a\u00020 2\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\r0#H\u0007\u001aJ\u00105\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\b\u00106\u001a\u0004\u0018\u0001072\f\u00108\u001a\b\u0012\u0004\u0012\u00020\r0%2\u0012\u00104\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\r0#H\u0007\u001a0\u00109\u001a\u00020\r2\u0006\u0010+\u001a\u00020\t2\u0006\u0010:\u001a\u00020\t2\u0006\u0010;\u001a\u00020\t2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0007\u001a\u0018\u0010@\u001a\u00020\r2\u0006\u0010<\u001a\u00020=2\u0006\u0010A\u001a\u00020\tH\u0007\u001a&\u0010B\u001a\u00020\r2\u0006\u0010<\u001a\u00020=2\u0006\u0010\u001f\u001a\u00020 2\f\u0010C\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a?\u0010D\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010E\u001a\u00020F2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020F\u0012\u0004\u0012\u00020\r0#2\u0011\u0010H\u001a\r\u0012\u0004\u0012\u00020\r0%\u00a2\u0006\u0002\bIH\u0007\u001a.\u0010J\u001a\u00020\r2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\t2\u0006\u0010;\u001a\u00020-2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a\u0010\u0010O\u001a\u00020\r2\u0006\u0010<\u001a\u00020=H\u0007\u001aL\u0010P\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\b\u00106\u001a\u0004\u0018\u0001072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00150\u00122\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a\u0018\u0010S\u001a\u00020\r2\u0006\u0010T\u001a\u00020\t2\u0006\u0010U\u001a\u00020\tH\u0003\u001a(\u0010V\u001a\u00020\r2\u0006\u00101\u001a\u00020\t2\u0006\u0010W\u001a\u00020\t2\u000e\b\u0002\u0010N\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a\u008e\u0001\u0010X\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010<\u001a\u00020=2\u0006\u0010\u001f\u001a\u00020 2\b\u00106\u001a\u0004\u0018\u0001072\u0006\u0010Y\u001a\u00020-2\u0006\u0010A\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00150\u00122\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\r0%2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020\r0%2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\r0%2\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a1\u0010]\u001a\u00020\r2\u0006\u00101\u001a\u00020\t2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\r0%2\u0011\u0010H\u001a\r\u0012\u0004\u0012\u00020\r0%\u00a2\u0006\u0002\bIH\u0007\u001a\b\u0010^\u001a\u00020\rH\u0007\u001aj\u0010_\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\f\u0010`\u001a\b\u0012\u0004\u0012\u00020a0\u00122\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\r0%2<\u00108\u001a8\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\bc\u0012\b\b+\u0012\u0004\b\b(+\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020a0\u0012\u00a2\u0006\f\bc\u0012\b\b+\u0012\u0004\b\b(;\u0012\u0004\u0012\u00020\r0bH\u0007\u001a\"\u0010d\u001a\u00020\r2\u0006\u0010U\u001a\u00020\t2\u0006\u0010T\u001a\u00020\t2\b\b\u0002\u0010e\u001a\u00020fH\u0003\u001a8\u0010g\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010Y\u001a\u00020-2\f\u0010h\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a,\u0010i\u001a\u00020\r2\u0006\u00101\u001a\u00020\t2\u0006\u0010,\u001a\u00020-2\u0012\u0010j\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\r0#H\u0007\u001a\u001e\u0010k\u001a\u00020\r2\u0006\u0010l\u001a\u00020\u00132\f\u0010N\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a8\u0010m\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00052\b\u0010n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010Y\u001a\u00020-2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\r0%H\u0007\u001a2\u0010o\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00122\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\t2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020a0\u0012H\u0086@\u00a2\u0006\u0002\u0010p\u001a\u0016\u0010q\u001a\u00020\u00172\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002\u001a\u0016\u0010r\u001a\u00020\t2\u0006\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020-\u001a\u0010\u0010v\u001a\u00020\t2\u0006\u0010:\u001a\u00020\tH\u0002\u001a\u0010\u0010w\u001a\u00020\t2\u0006\u0010:\u001a\u00020\tH\u0002\u001a\u000e\u0010x\u001a\u00020\t2\u0006\u0010y\u001a\u00020z\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\u0006\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0007\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\n\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0002\"\u000e\u0010\u000b\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006{"}, d2 = {"AvatarBg", "Landroidx/compose/ui/graphics/Color;", "J", "AvatarFg", "EN", "Lcom/fitlog/app/Labels;", "NameYellow", "StartRed", "TAG", "", "WelcomeRed", "ZU", "AchievementCard", "", "badge", "Lcom/fitlog/app/data/Achievement;", "AchievementsScreen", "workouts", "", "Lcom/fitlog/app/data/WorkoutEntity;", "sets", "Lcom/fitlog/app/data/WorkoutSetEntity;", "streakWeeks", "", "ActiveWorkoutScreen", "labels", "workoutId", "userId", "workoutName", "initialLogs", "Lcom/fitlog/app/ExerciseLog;", "repo", "Lcom/fitlog/app/data/FitLogRepository;", "restSeconds", "onRestChanged", "Lkotlin/Function1;", "onFinish", "Lkotlin/Function0;", "onBack", "CalendarMock", "DumbbellLogo", "size", "ExerciseRow", "name", "checked", "", "onToggle", "FitLogApp", "Header", "title", "back", "HistoryScreen", "onWorkout", "HomeScreen", "currentUser", "Lcom/fitlog/app/data/UserEntity;", "onStart", "LanguageRow", "code", "selected", "prefs", "Lcom/fitlog/app/data/AppPreferences;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "LanguageScreen", "language", "LoginScreen", "onLogin", "MainScaffold", "current", "Lcom/fitlog/app/Screen;", "navigate", "content", "Landroidx/compose/runtime/Composable;", "NavItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "text", "onClick", "NotificationsScreen", "ProfileScreen", "allSets", "onLogout", "ProfileStat", "value", "label", "SettingRow", "trailing", "SettingsScreen", "unitsMetric", "onNotifications", "onLanguage", "onAchievements", "SimplePage", "SplashScreen", "StartWorkoutScreen", "exercises", "Lcom/fitlog/app/data/ExerciseEntity;", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "StatCard", "modifier", "Landroidx/compose/ui/Modifier;", "SummaryScreen", "onDone", "ToggleRow", "onCheckedChange", "WorkoutCard", "workout", "WorkoutDetailScreen", "id", "buildExerciseLogs", "(Lcom/fitlog/app/data/FitLogRepository;Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "computeWeekStreak", "formatWeight", "kg", "", "metric", "frequencyLabel", "languageLabel", "relativeDateLabel", "timestamp", "", "app_debug"})
public final class MainActivityKt {
    private static final long WelcomeRed = 0L;
    private static final long StartRed = 0L;
    private static final long NameYellow = 0L;
    private static final long AvatarBg = 0L;
    private static final long AvatarFg = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "FitLog";
    @org.jetbrains.annotations.NotNull()
    private static final com.fitlog.app.Labels EN = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.fitlog.app.Labels ZU = null;
    
    @androidx.compose.runtime.Composable()
    public static final void FitLogApp() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SplashScreen() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DumbbellLogo(int size) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LoginScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.AppPreferences prefs, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogin) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MainScaffold(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.Screen current, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.fitlog.app.Screen, kotlin.Unit> navigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NavItem(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String text, boolean selected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts, @org.jetbrains.annotations.Nullable()
    com.fitlog.app.data.UserEntity currentUser, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onWorkout) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void Header(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> back) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WorkoutCard(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.WorkoutEntity workout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String relativeDateLabel(long timestamp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String formatWeight(float kg, boolean metric) {
        return null;
    }
    
    private static final java.lang.String languageLabel(java.lang.String code) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StartWorkoutScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.ExerciseEntity> exercises, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.util.List<com.fitlog.app.data.ExerciseEntity>, kotlin.Unit> onStart) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ExerciseRow(@org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean checked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggle) {
    }
    
    /**
     * Reads the user's previous sets for each chosen exercise and builds the editable logs ("Last time" autofill).
     */
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.Object buildExerciseLogs(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.ExerciseEntity> selected, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fitlog.app.ExerciseLog>> $completion) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ActiveWorkoutScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.Nullable()
    java.lang.String workoutId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String workoutName, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.ExerciseLog> initialLogs, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, int restSeconds, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onRestChanged, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFinish, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SummaryScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.Nullable()
    java.lang.String workoutId, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, boolean unitsMetric, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatCard(java.lang.String label, java.lang.String value, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HistoryScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onWorkout) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CalendarMock(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void WorkoutDetailScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, boolean unitsMetric, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.AppPreferences prefs, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.FitLogRepository repo, @org.jetbrains.annotations.Nullable()
    com.fitlog.app.data.UserEntity currentUser, boolean unitsMetric, @org.jetbrains.annotations.NotNull()
    java.lang.String language, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutSetEntity> allSets, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNotifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLanguage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAchievements, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    private static final java.lang.String frequencyLabel(java.lang.String code) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingRow(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String trailing, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ToggleRow(@org.jetbrains.annotations.NotNull()
    java.lang.String title, boolean checked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SimplePage(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NotificationsScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.AppPreferences prefs) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LanguageScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.AppPreferences prefs, @org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LanguageRow(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String selected, @org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.AppPreferences prefs, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AchievementsScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutSetEntity> sets, int streakWeeks) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AchievementCard(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.data.Achievement badge) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileScreen(@org.jetbrains.annotations.NotNull()
    com.fitlog.app.Labels labels, @org.jetbrains.annotations.Nullable()
    com.fitlog.app.data.UserEntity currentUser, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutEntity> workouts, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fitlog.app.data.WorkoutSetEntity> allSets, int streakWeeks, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProfileStat(java.lang.String value, java.lang.String label) {
    }
    
    private static final int computeWeekStreak(java.util.List<com.fitlog.app.data.WorkoutEntity> workouts) {
        return 0;
    }
}