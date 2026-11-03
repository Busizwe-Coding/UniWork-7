package com.fitlog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.text.input.KeyboardType
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.fitlog.app.data.PushRegistrar
import com.fitlog.app.data.ReminderScheduler
import com.fitlog.app.data.WorkoutApi
import com.fitlog.app.data.ensureNotificationChannel
import com.fitlog.app.data.showFitLogNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import com.fitlog.app.ui.theme.FitFontTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitlog.app.data.Achievement
import com.fitlog.app.data.AppPreferences
import com.fitlog.app.data.ExerciseEntity
import com.fitlog.app.data.ExportUtil
import com.fitlog.app.data.FitLogRepository
import com.fitlog.app.data.UserEntity
import com.fitlog.app.data.WorkoutEntity
import com.fitlog.app.data.WorkoutSetEntity
import com.fitlog.app.data.computeAchievements
import com.fitlog.app.ui.auth.LoginViewModel
import com.fitlog.app.ui.theme.FitBorder
import com.fitlog.app.ui.theme.FitDarkGreen
import com.fitlog.app.ui.theme.FitGreen
import com.fitlog.app.ui.theme.FitLightGreen
import com.fitlog.app.ui.theme.FitLogTheme
import com.fitlog.app.ui.theme.FitMuted
import com.fitlog.app.ui.theme.FitRed
import com.fitlog.app.ui.theme.FitSurface
import com.fitlog.app.ui.theme.FitText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// Colours requested in the latest change list
private val WelcomeRed = Color(0xFF9E0000)   // "Welcome back" heading + Finish Workout button
private val StartRed = Color(0xFFC20000)     // Start Workout button
private val NameYellow = Color(0xFFFFFB00)   // exercise-name cards on the workout screen
private val AvatarBg = Color(0xFF232E78)     // profile picture background
private val AvatarFg = Color(0xFF397DBA)     // profile picture letter
private const val TAG = "FitLog"

public enum class Screen { Splash, Login, Home, StartWorkout, ActiveWorkout, Summary, History, Detail, Settings, Notifications, Language, Achievements, Profile }

public data class Labels(
    val home: String, val history: String, val settings: String, val profile: String,
    val startWorkout: String, val recentWorkouts: String, val currentStreak: String,
    val thisWeek: String, val thisYear: String, val workouts: String, val volume: String,
    val finishWorkout: String, val done: String,
    val workoutSummary: String, val notifications: String,
    val language: String, val save: String, val cancel: String, val edit: String,
    val delete: String, val addSet: String, val addExercises: String,
    val workoutName: String, val startRest: String, val achievements: String,
    val exportData: String, val statWorkouts: String, val statMinutes: String, val statStreak: String,
)

private val EN = Labels(
    home = "Home", history = "History", settings = "Settings", profile = "Profile",
    startWorkout = "Start Workout", recentWorkouts = "Recent workouts", currentStreak = "Current streak",
    thisWeek = "This week", thisYear = "THIS YEAR", workouts = "workouts", volume = "volume",
    finishWorkout = "Finish Workout", done = "Done",
    workoutSummary = "Workout Summary", notifications = "Notifications",
    language = "Language", save = "Save", cancel = "Cancel", edit = "Edit",
    delete = "Delete", addSet = "+ Add set", addExercises = "ADD SELECTED",
    workoutName = "Workout name", startRest = "Start rest", achievements = "Achievements",
    exportData = "Export my data", statWorkouts = "Workouts", statMinutes = "Minutes trained", statStreak = "Week streak",
)
private val ZU = Labels(
    home = "Ikhaya", history = "Umlando", settings = "Izilungiselelo", profile = "Iphrofayela",
    startWorkout = "Qala Ukujima", recentWorkouts = "Ukujima kwakamuva", currentStreak = "Uchungechunge lwamanje",
    thisWeek = "Kuleli sonto", thisYear = "KULUNYAKA", workouts = "ukujima", volume = "umthamo",
    finishWorkout = "Qeda Ukujima", done = "Kwenziwe",
    workoutSummary = "Isifinyezo Sokujima", notifications = "Izaziso",
    language = "Ulimi", save = "Gcina", cancel = "Khansela", edit = "Hlela",
    delete = "Susa", addSet = "+ Engeza iseti", addExercises = "ENGEZA OKUKHETHIWE",
    workoutName = "Igama lokujima", startRest = "Qala ukuphumula", achievements = "Imiklomelo",
    exportData = "Thumela idatha yami", statWorkouts = "Ukujima", statMinutes = "Imizuzu ejimiwe", statStreak = "Uchungechunge lwesonto",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ensureNotificationChannel(this)
        setContent { FitLogTheme { FitFontTheme { FitLogApp() } } }
    }
}

@Composable
fun FitLogApp() {
    val context = LocalContext.current
    val repo = remember { FitLogRepository(context) }
    val prefs = remember { AppPreferences(context) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { repo.ensureExercisesSeeded() }

    val currentUserId by prefs.currentUserId.collectAsStateWithLifecycle(initialValue = "")
    val currentUser by repo.observeUser(currentUserId).collectAsStateWithLifecycle(initialValue = null)
    val workouts by repo.workoutsForUser(currentUserId).collectAsStateWithLifecycle(initialValue = emptyList())
    val allSets by repo.setsForUser(currentUserId).collectAsStateWithLifecycle(initialValue = emptyList())
    val exercises by repo.exercises.collectAsStateWithLifecycle(initialValue = emptyList())
    val language by prefs.language.collectAsStateWithLifecycle(initialValue = "en")
    val unitsMetric by prefs.unitsMetric.collectAsStateWithLifecycle(initialValue = true)
    val restSeconds by prefs.restSeconds.collectAsStateWithLifecycle(initialValue = 90)

    // Android 13+ asks the user before notifications may be shown
    val notifPermission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        Log.d(TAG, "Notification permission granted=$granted")
    }
    LaunchedEffect(currentUserId) {
        if (currentUserId.isNotBlank()) {
            if (Build.VERSION.SDK_INT >= 33 &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) notifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            ReminderScheduler.schedule(context, prefs.notificationFrequency.first())   // daily/weekly/monthly/off
            PushRegistrar.registerDevice(context.applicationContext)                    // server push (if signed in to the server)
        }
    }

    var screen by remember { mutableStateOf(Screen.Splash) }
    var selectedWorkout by remember { mutableStateOf<String?>(null) }
    var currentWorkoutId by remember { mutableStateOf<String?>(null) }
    var currentWorkoutName by remember { mutableStateOf("Workout") }
    var pendingLogs by remember { mutableStateOf<List<ExerciseLog>>(emptyList()) }

    LaunchedEffect(Unit) {
        delay(900)
        screen = if (currentUserId.isNotBlank()) Screen.Home else Screen.Login
    }

    val labels = if (language == "zu") ZU else EN
    val streakWeeks = remember(workouts) { computeWeekStreak(workouts) }

    fun doLogout() {
        scope.launch {
            PushRegistrar.unregisterDevice(context.applicationContext)   // stop server pushes to this phone
            prefs.clearSession()
        }
        screen = Screen.Login
    }

    when (screen) {
        Screen.Splash -> SplashScreen()
        Screen.Login -> LoginScreen(prefs, repo, onLogin = { screen = Screen.Home })
        Screen.Home -> MainScaffold(labels, Screen.Home, { screen = it }, content = {
            HomeScreen(labels, workouts, currentUser, onStart = { screen = Screen.StartWorkout }, onWorkout = { selectedWorkout = it; screen = Screen.Detail })
        })
        Screen.StartWorkout -> MainScaffold(labels, Screen.Home, { screen = it }, content = {
            StartWorkoutScreen(
                labels, exercises,
                onBack = { screen = Screen.Home },
                onStart = { name, selected ->
                    // Build the "last time" logs BEFORE switching screens so the workout screen
                    // never shows half-loaded (that was the split-second timer flash).
                    scope.launch {
                        val logs = buildExerciseLogs(repo, currentUserId, selected)
                        currentWorkoutId = UUID.randomUUID().toString()
                        currentWorkoutName = name.ifBlank { "Workout" }
                        pendingLogs = logs
                        screen = Screen.ActiveWorkout
                    }
                },
            )
        })
        Screen.ActiveWorkout -> ActiveWorkoutScreen(
            labels, currentWorkoutId, currentUserId, currentWorkoutName, pendingLogs, repo,
            restSeconds = restSeconds,
            onRestChanged = { scope.launch { prefs.setRestSeconds(it) } },
            onFinish = { screen = Screen.Summary },
            onBack = { screen = Screen.Home },
        )
        Screen.Summary -> SummaryScreen(labels, currentWorkoutId, repo, unitsMetric, onDone = { screen = Screen.Home })
        Screen.History -> MainScaffold(labels, Screen.History, { screen = it }, content = {
            HistoryScreen(labels, workouts, repo, onWorkout = { selectedWorkout = it; screen = Screen.Detail })
        })
        Screen.Detail -> WorkoutDetailScreen(labels, selectedWorkout, repo, unitsMetric, onBack = { screen = Screen.History })
        Screen.Settings -> MainScaffold(labels, Screen.Settings, { screen = it }, content = {
            SettingsScreen(
                labels, prefs, repo, currentUser, unitsMetric, language, workouts, allSets,
                onNotifications = { screen = Screen.Notifications },
                onLanguage = { screen = Screen.Language },
                onAchievements = { screen = Screen.Achievements },
                onLogout = { doLogout() },
            )
        })
        Screen.Notifications -> SimplePage(labels.notifications, onBack = { screen = Screen.Settings }) { NotificationsScreen(prefs) }
        Screen.Language -> SimplePage(labels.language, onBack = { screen = Screen.Settings }) { LanguageScreen(prefs, language) }
        Screen.Achievements -> SimplePage(labels.achievements, onBack = { screen = Screen.Settings }) {
            AchievementsScreen(workouts, allSets, streakWeeks)
        }
        Screen.Profile -> MainScaffold(labels, Screen.Profile, { screen = it }, content = {
            ProfileScreen(labels, currentUser, workouts, allSets, streakWeeks, onLogout = { doLogout() })
        })
    }
}

@Composable
fun SplashScreen() {
    Box(Modifier.fillMaxSize().background(FitGreen), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DumbbellLogo(64)
            Spacer(Modifier.height(16.dp))
            Text("FITLOG", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}

@Composable
fun DumbbellLogo(size: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp), modifier = Modifier.height(size.dp)) {
        Box(Modifier.width((size * .13f).dp).height((size * .46f).dp).clip(RoundedCornerShape(2.dp)).background(Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .72f).dp).clip(RoundedCornerShape(2.dp)).background(Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .38f).dp).background(Color.White))
        Box(Modifier.width((size * .30f).dp).height((size * .10f).dp).background(Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .38f).dp).background(Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .72f).dp).clip(RoundedCornerShape(2.dp)).background(Color.White))
        Box(Modifier.width((size * .13f).dp).height((size * .46f).dp).clip(RoundedCornerShape(2.dp)).background(Color.White))
    }
}

@Composable
fun LoginScreen(prefs: AppPreferences, repo: FitLogRepository, onLogin: () -> Unit) {
    val viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(prefs, repo) as T
        }
    )

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = false)
    val error by viewModel.errorMessage.collectAsStateWithLifecycle(initialValue = null)

    var isRegistering by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
        Box(Modifier.fillMaxWidth().height(72.dp).background(FitGreen), contentAlignment = Alignment.Center) {
            Text(if (isRegistering) "Create Account" else "Welcome Back", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Column(Modifier.fillMaxWidth().padding(horizontal = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(22.dp))

            if (error != null) {
                Text(error!!, color = Color.Red, fontSize = 13.sp)
                Spacer(Modifier.height(10.dp))
            }

            if (isRegistering) {
                OutlinedTextField(name, { name = it }, label = { Text("Full Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(10.dp))
            }

            OutlinedTextField(email, { email = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(password, { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(14.dp))

            Button(
                onClick = {
                    if (isRegistering) viewModel.register(name, email, password, onLogin)
                    else viewModel.login(email, password, onLogin)
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FitDarkGreen)
            ) {
                Text(if (isLoading) "PLEASE WAIT..." else if (isRegistering) "REGISTER" else "LOG IN", fontSize = 14.sp)
            }

            Spacer(Modifier.height(12.dp))
            TextButton(onClick = { isRegistering = !isRegistering }) {
                Text(if (isRegistering) "Already have an account? Log in" else "Don't have an account? Register", color = FitMuted, fontSize = 13.sp)
            }

            if (!isRegistering) {
                Spacer(Modifier.height(8.dp))
                Text("or continue with", color = FitMuted, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { viewModel.loginWithGoogle(context, onLogin) },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Sign in with Google", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun MainScaffold(labels: Labels, current: Screen, navigate: (Screen) -> Unit, content: @Composable () -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        bottomBar = {
            Row(
                Modifier.fillMaxWidth().background(FitGreen).windowInsetsPadding(WindowInsets.navigationBars).padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                NavItem(Icons.Default.Home, labels.home, current == Screen.Home) { navigate(Screen.Home) }
                NavItem(Icons.Default.History, labels.history, current == Screen.History) { navigate(Screen.History) }
                NavItem(Icons.Default.Settings, labels.settings, current == Screen.Settings) { navigate(Screen.Settings) }
                NavItem(Icons.Default.Person, labels.profile, current == Screen.Profile) { navigate(Screen.Profile) }
            }
        }
    ) { padding -> Box(Modifier.padding(padding)) { content() } }
}

@Composable
fun NavItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, selected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }.padding(horizontal = 14.dp, vertical = 2.dp)) {
        Icon(icon, text, tint = if (selected) Color.White else Color.White.copy(alpha = .6f), modifier = Modifier.size(21.dp))
        Text(text, color = if (selected) Color.White else Color.White.copy(alpha = .6f), fontSize = 10.sp)
    }
}

@Composable
fun HomeScreen(labels: Labels, workouts: List<WorkoutEntity>, currentUser: UserEntity?, onStart: () -> Unit, onWorkout: (String) -> Unit) {
    // Workouts logged in the current calendar year -> "THIS YEAR: X WORKOUTS"
    val thisYearCount = remember(workouts) {
        val year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        workouts.count { java.util.Calendar.getInstance().apply { timeInMillis = it.dateTime }.get(java.util.Calendar.YEAR) == year }
    }
    Column(Modifier.fillMaxSize().background(Color.White)) {
        Header("FitLog")
        // The history list scrolls by itself; the Start Workout button stays pinned underneath it.
        LazyColumn(Modifier.weight(1f).fillMaxWidth().padding(horizontal = 14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Spacer(Modifier.height(10.dp))
                Text("Welcome back, ${currentUser?.name ?: "Athlete"}", color = WelcomeRed, fontWeight = FontWeight.Bold, fontSize = 26.sp)
                Spacer(Modifier.height(10.dp))
                Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "${labels.thisYear}: $thisYearCount ${labels.workouts.uppercase()}",
                        fontWeight = FontWeight.Bold, fontSize = 18.sp, maxLines = 1, softWrap = false,
                        modifier = Modifier.padding(14.dp),
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(labels.recentWorkouts, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
            if (workouts.isEmpty()) {
                item {
                    Text(
                        "No workouts yet — tap ${labels.startWorkout} to log your first session.",
                        color = FitMuted, fontSize = 12.sp, modifier = Modifier.padding(vertical = 8.dp),
                    )
                }
            }
            items(workouts) { workout -> WorkoutCard(workout) { onWorkout(workout.id) } }
            item { Spacer(Modifier.height(8.dp)) }
        }
        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp).height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = StartRed, contentColor = Color.White),
        ) { Text(labels.startWorkout, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
    }
}

@Composable
fun Header(title: String, back: (() -> Unit)? = null) {
    Column(Modifier.fillMaxWidth().background(FitGreen)) {
        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        Row(
            Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (back != null) IconButton(onClick = back) { Icon(Icons.Default.ArrowBack, null, tint = Color.White) }
            DumbbellLogo(25)
            Spacer(Modifier.size(9.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        }
    }
}

@Composable
fun WorkoutCard(workout: WorkoutEntity, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = FitSurface), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Column(Modifier.padding(12.dp)) {
            Text(workout.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text("${relativeDateLabel(workout.dateTime)} • ${workout.syncStatus.lowercase()}", color = FitMuted, fontSize = 11.sp)
        }
    }
}

fun relativeDateLabel(timestamp: Long): String {
    val days = ((System.currentTimeMillis() - timestamp) / 86_400_000L).toInt()
    return when {
        days <= 0 -> "Today"
        days == 1 -> "Yesterday"
        days < 7 -> "$days days ago"
        else -> SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(timestamp))
    }
}

fun formatWeight(kg: Float, metric: Boolean): String {
    val value = if (metric) kg else kg * 2.20462f
    val rounded = "%.1f".format(value).trimEnd('0').trimEnd('.')
    return if (metric) "${rounded}kg" else "${rounded}lb"
}

private fun languageLabel(code: String) = when (code) { "zu" -> "isiZulu"; else -> "English" }

@Composable
fun StartWorkoutScreen(
    labels: Labels,
    exercises: List<ExerciseEntity>,
    onBack: () -> Unit,
    onStart: (name: String, selected: List<ExerciseEntity>) -> Unit,
) {
    var workoutName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val selectedIds = remember { mutableStateListOf<String>() }
    val categories = remember(exercises) { listOf("All") + exercises.map { it.category }.distinct() }
    val filtered = remember(exercises, category, searchQuery) {
        exercises
            .filter { category == "All" || it.category == category }
            .filter { searchQuery.isBlank() || it.name.contains(searchQuery.trim(), ignoreCase = true) }
    }

    Column(Modifier.fillMaxSize().navigationBarsPadding()) {
        Header(labels.startWorkout, onBack)
        Column(Modifier.padding(14.dp)) {
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                searchQuery, { searchQuery = it },
                placeholder = { Text("Search workout name…") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            Text("Search or pick exercises (${selectedIds.size} selected)", color = FitMuted, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                categories.forEach { cat ->
                    val active = cat == category
                    Box(
                        Modifier.clip(RoundedCornerShape(15.dp))
                            .background(if (active) FitGreen else FitSurface)
                            .border(1.dp, FitBorder, RoundedCornerShape(15.dp))
                            .clickable { category = cat }
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                    ) { Text(cat, fontSize = 11.sp, color = if (active) Color.White else FitText) }
                }
            }
            Spacer(Modifier.height(10.dp))
            LazyColumn(Modifier.weight(1f)) {
                if (filtered.isEmpty()) {
                    item { Text("No workouts match \"$searchQuery\".", color = FitMuted, fontSize = 12.sp, modifier = Modifier.padding(vertical = 12.dp)) }
                }
                items(filtered) { exercise ->
                    val checked = exercise.id in selectedIds
                    ExerciseRow(exercise.name, checked) {
                        if (checked) selectedIds.remove(exercise.id) else selectedIds.add(exercise.id)
                    }
                    Spacer(Modifier.height(7.dp))
                }
            }
            Button(
                onClick = {
                    val selected = exercises.filter { it.id in selectedIds }
                    onStart(workoutName, selected)
                },
                enabled = selectedIds.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FitGreen),
            ) { Text("${labels.addExercises} (${selectedIds.size}) →", fontSize = 13.sp) }
        }
    }
}

@Composable
fun ExerciseRow(name: String, checked: Boolean, onToggle: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .border(1.dp, if (checked) FitGreen else FitBorder, RoundedCornerShape(10.dp))
            .background(if (checked) FitLightGreen else Color.White)
            .clickable { onToggle() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(name, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Checkbox(checked = checked, onCheckedChange = { onToggle() })
    }
}

class SetInput(reps: String, weight: String) {
    var reps by mutableStateOf(reps)
    var weight by mutableStateOf(weight)
}

class ExerciseLog(val exerciseId: String, val exerciseName: String, val lastTimeLabel: String, initialSets: List<SetInput>) {
    val sets = mutableStateListOf(*initialSets.toTypedArray())
}

/** Reads the user's previous sets for each chosen exercise and builds the editable logs ("Last time" autofill). */
suspend fun buildExerciseLogs(repo: FitLogRepository, userId: String, selected: List<ExerciseEntity>): List<ExerciseLog> =
    selected.map { ex ->
        val recent = repo.getRecentSetsForExercise(userId, ex.id, limit = 3)
        val lastTimeLabel = if (recent.isNotEmpty()) {
            "Last time: " + recent.take(3).joinToString(", ") { "${it.weightKg.toInt()}kg × ${it.reps}" }
        } else "No previous data — starting fresh"
        val initialSets = if (recent.isNotEmpty()) {
            recent.take(3).map { SetInput(it.reps.toString(), it.weightKg.toInt().toString()) }
        } else {
            listOf(SetInput("8", "20"), SetInput("8", "20"), SetInput("8", "20"))
        }
        ExerciseLog(ex.id, ex.name, lastTimeLabel, initialSets)
    }

@Composable
fun ActiveWorkoutScreen(
    labels: Labels,
    workoutId: String?,
    userId: String,
    workoutName: String,
    initialLogs: List<ExerciseLog>,
    repo: FitLogRepository,
    restSeconds: Int,
    onRestChanged: (Int) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val appContext = LocalContext.current.applicationContext
    val startTime = remember { System.currentTimeMillis() }
    val logs = remember { mutableStateListOf(*initialLogs.toTypedArray()) }

    var restDuration by remember { mutableIntStateOf(restSeconds) }      // user-adjustable rest length (seconds), saved in Settings
    var restSecondsLeft by remember { mutableIntStateOf(restSeconds) }
    var timerRunning by remember { mutableStateOf(false) }
    var showEditRest by remember { mutableStateOf(false) }
    var restInput by remember { mutableStateOf("90") }
    LaunchedEffect(timerRunning) {
        while (timerRunning && restSecondsLeft > 0) {
            delay(1000)
            restSecondsLeft--
        }
        if (restSecondsLeft <= 0) timerRunning = false
    }

    Column(Modifier.fillMaxSize().navigationBarsPadding()) {
        Header(workoutName, onBack)
        LazyColumn(Modifier.weight(1f).padding(12.dp)) {
            items(logs.size) { logIndex ->
                val log = logs[logIndex]
                Card(colors = CardDefaults.cardColors(containerColor = NameYellow), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(log.exerciseName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                        Text(log.lastTimeLabel, fontSize = 11.sp, color = Color(0xFF4A4A00))
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text("SET", Modifier.weight(1f), fontSize = 11.sp, color = FitMuted)
                    Text("KG", Modifier.weight(1.2f), fontSize = 11.sp, color = FitMuted)
                    Text("REPS", Modifier.weight(1.2f), fontSize = 11.sp, color = FitMuted)
                    Spacer(Modifier.width(36.dp))
                }
                log.sets.forEachIndexed { setIndex, s ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("${setIndex + 1}", Modifier.weight(1f), fontSize = 12.sp)
                        OutlinedTextField(s.weight, { s.weight = it }, singleLine = true, modifier = Modifier.weight(1.2f).height(50.dp))
                        OutlinedTextField(s.reps, { s.reps = it }, singleLine = true, modifier = Modifier.weight(1.2f).height(50.dp))
                        Icon(Icons.Default.Check, null, tint = FitGreen, modifier = Modifier.size(24.dp))
                    }
                }
                Row {
                    TextButton(onClick = { log.sets.add(SetInput("8", log.sets.lastOrNull()?.weight ?: "20")) }) {
                        Text(labels.addSet, color = FitGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    // Deletes the last set of this exercise (at least one row stays)
                    TextButton(
                        onClick = { if (log.sets.size > 1) log.sets.removeAt(log.sets.lastIndex) },
                        enabled = log.sets.size > 1,
                    ) {
                        Text("− Delete set", color = if (log.sets.size > 1) FitRed else FitMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = FitSurface), shape = RoundedCornerShape(9.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, null, tint = FitGreen)
                        Spacer(Modifier.width(7.dp))
                        Text("${restSecondsLeft / 60}:${(restSecondsLeft % 60).toString().padStart(2, '0')}", fontSize = 14.sp, modifier = Modifier.weight(1f))
                        TextButton(onClick = { restInput = restDuration.toString(); showEditRest = true }) {
                            Text("Edit rest", fontSize = 12.sp, color = FitMuted)
                        }
                        if (timerRunning) {
                            TextButton(onClick = { timerRunning = false }) { Text("Pause", fontSize = 12.sp) }
                        } else {
                            TextButton(onClick = { restSecondsLeft = restDuration; timerRunning = true }) { Text(labels.startRest, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FitGreen) }
                        }
                    }
                }
                Spacer(Modifier.height(100.dp))
            }
        }
        Button(
            onClick = {
                val wid = workoutId ?: return@Button
                val durationSeconds = ((System.currentTimeMillis() - startTime) / 1000).toInt()
                val setEntities = logs.flatMap { log ->
                    log.sets.mapIndexed { i, s ->
                        WorkoutSetEntity(
                            id = UUID.randomUUID().toString(),
                            workoutId = wid,
                            exerciseId = log.exerciseId,
                            setNumber = i + 1,
                            reps = s.reps.toIntOrNull() ?: 0,
                            weightKg = s.weight.toFloatOrNull() ?: 0f,
                        )
                    }
                }
                val nameById = logs.associate { it.exerciseId to it.exerciseName }
                scope.launch {
                    repo.saveWorkout(WorkoutEntity(wid, userId, workoutName, startTime, durationSeconds), setEntities)
                    // Best-effort upload so the server can push "Workout saved" to the user's phones.
                    // Detached scope: it must finish even though this screen closes right away.
                    CoroutineScope(Dispatchers.IO).launch {
                        val token = AppPreferences(appContext).authToken.first()
                        if (token.isNotBlank()) {
                            val arr = JSONArray()
                            setEntities.forEach { st ->
                                arr.put(JSONObject().put("id", st.id).put("exerciseId", st.exerciseId)
                                    .put("exerciseName", nameById[st.exerciseId] ?: st.exerciseId)
                                    .put("setNumber", st.setNumber).put("reps", st.reps).put("weightKg", st.weightKg.toDouble()))
                            }
                            WorkoutApi.upload(token, JSONObject().put("id", wid).put("name", workoutName).put("dateTime", startTime)
                                .put("durationSeconds", durationSeconds).put("notes", "").put("sets", arr))
                        }
                    }
                    onFinish()
                }
            },
            enabled = logs.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().padding(12.dp).height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WelcomeRed, contentColor = Color.White),
        ) { Text(labels.finishWorkout, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
    }

    if (showEditRest) {
        AlertDialog(
            onDismissRequest = { showEditRest = false },
            title = { Text("Edit rest") },
            text = {
                OutlinedTextField(
                    restInput, { restInput = it.filter(Char::isDigit).take(4) },
                    label = { Text("Rest length (seconds)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val secs = restInput.toIntOrNull()?.coerceIn(5, 3600) ?: restDuration
                    restDuration = secs
                    onRestChanged(secs)               // remember it as the new default
                    if (!timerRunning) restSecondsLeft = secs
                    Log.d(TAG, "Rest length set to $secs s")
                    showEditRest = false
                }) { Text(labels.save, color = FitGreen, fontWeight = FontWeight.Bold) }
            },
            dismissButton = { TextButton(onClick = { showEditRest = false }) { Text(labels.cancel) } },
        )
    }
}

@Composable
fun SummaryScreen(labels: Labels, workoutId: String?, repo: FitLogRepository, unitsMetric: Boolean, onDone: () -> Unit) {
    var workout by remember { mutableStateOf<WorkoutEntity?>(null) }
    var sets by remember { mutableStateOf<List<WorkoutSetEntity>>(emptyList()) }
    LaunchedEffect(workoutId) {
        if (workoutId != null) { val (w, s) = repo.getWorkout(workoutId); workout = w; sets = s }
    }
    val totalVolume = sets.sumOf { (it.weightKg * it.reps).toDouble() }.toFloat()
    val durationMin = (workout?.durationSeconds ?: 0) / 60

    Column(Modifier.fillMaxSize().navigationBarsPadding()) {
        Header(labels.workoutSummary)
        LazyColumn(Modifier.weight(1f).padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${workout?.name ?: "Workout"} complete!", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Spacer(Modifier.height(6.dp))
                        Text("Great session — here's how it went.", fontSize = 12.sp, color = FitDarkGreen)
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    StatCard("Duration", "$durationMin min", Modifier.weight(1f))
                    StatCard("Volume", formatWeight(totalVolume, unitsMetric), Modifier.weight(1f))
                    StatCard("Sets", "${sets.size}", Modifier.weight(1f))
                }
            }
            item { Text("Exercises", fontWeight = FontWeight.Bold, fontSize = 16.sp) }
            if (sets.isEmpty()) {
                item { Text("No sets were logged.", color = FitMuted, fontSize = 12.sp) }
            }
            sets.groupBy { it.exerciseId }.forEach { (exerciseId, exerciseSets) ->
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = FitSurface), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(14.dp)) {
                            Text(exerciseId.replaceFirstChar { it.uppercase() }, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(6.dp))
                            Text("${exerciseSets.size} sets", fontSize = 12.sp, color = FitMuted)
                            Spacer(Modifier.height(4.dp))
                            exerciseSets.forEach { s ->
                                Text("Set ${s.setNumber}:  ${formatWeight(s.weightKg, unitsMetric)} × ${s.reps}", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth().padding(14.dp).height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = FitGreen)) { Text(labels.done, fontSize = 14.sp) }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = FitSurface), shape = RoundedCornerShape(12.dp), modifier = modifier) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FitDarkGreen)
            Text(label, fontSize = 10.sp, color = FitMuted)
        }
    }
}

@Composable
fun HistoryScreen(labels: Labels, workouts: List<WorkoutEntity>, repo: FitLogRepository, onWorkout: (String) -> Unit) {
    val scope = rememberCoroutineScope()
    var pendingDelete by remember { mutableStateOf<WorkoutEntity?>(null) }
    var renaming by remember { mutableStateOf<WorkoutEntity?>(null) }
    var renameValue by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        Header(labels.history)
        LazyColumn(Modifier.fillMaxSize().padding(14.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            item {
                val monthLabel = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date()) }
                Text(monthLabel, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                CalendarMock(workouts)
            }
            item { Text(labels.recentWorkouts, fontWeight = FontWeight.Bold, fontSize = 15.sp) }
            if (workouts.isEmpty()) {
                item { Text("No workouts logged yet.", color = FitMuted, fontSize = 12.sp) }
            }
            items(workouts) { workout ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Box(Modifier.weight(1f)) { WorkoutCard(workout) { onWorkout(workout.id) } }
                    IconButton(onClick = { renameValue = workout.name; renaming = workout }) {
                        Icon(Icons.Default.Edit, contentDescription = "${labels.edit} ${labels.workoutName}", tint = FitDarkGreen)
                    }
                    IconButton(onClick = { pendingDelete = workout }) {
                        Icon(Icons.Default.Delete, contentDescription = labels.delete, tint = FitRed)
                    }
                }
            }
        }
    }

    renaming?.let { workout ->
        AlertDialog(
            onDismissRequest = { renaming = null },
            title = { Text("${labels.edit} ${labels.workoutName.lowercase()}") },
            text = { OutlinedTextField(renameValue, { renameValue = it }, singleLine = true, label = { Text(labels.workoutName) }, modifier = Modifier.fillMaxWidth()) },
            confirmButton = {
                TextButton(onClick = {
                    val newName = renameValue.trim()
                    if (newName.isNotEmpty()) {
                        scope.launch {
                            val (w, sets) = repo.getWorkout(workout.id)
                            if (w != null) { repo.saveWorkout(w.copy(name = newName), sets); Log.d(TAG, "Renamed workout to '$newName'") }
                        }
                    }
                    renaming = null
                }) { Text(labels.save, color = FitGreen, fontWeight = FontWeight.Bold) }
            },
            dismissButton = { TextButton(onClick = { renaming = null }) { Text(labels.cancel) } },
        )
    }

    pendingDelete?.let { workout ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("${labels.delete} \"${workout.name}\"?") },
            text = { Text("This can't be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch { repo.deleteWorkout(workout.id) }
                    pendingDelete = null
                }) { Text(labels.delete, color = FitRed) }
            },
            dismissButton = { TextButton(onClick = { pendingDelete = null }) { Text(labels.cancel) } },
        )
    }
}

@Composable
fun CalendarMock(workouts: List<WorkoutEntity>) {
    val calendar = remember { java.util.Calendar.getInstance() }
    val today = calendar.get(java.util.Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(java.util.Calendar.MONTH)
    val currentYear = calendar.get(java.util.Calendar.YEAR)
    val daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

    val loggedDays = remember(workouts) {
        workouts.mapNotNull { w ->
            val c = java.util.Calendar.getInstance().apply { timeInMillis = w.dateTime }
            if (c.get(java.util.Calendar.MONTH) == currentMonth && c.get(java.util.Calendar.YEAR) == currentYear) {
                c.get(java.util.Calendar.DAY_OF_MONTH)
            } else null
        }.toSet()
    }

    Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { Text(it, fontSize = 10.sp, color = FitMuted) }
        }
        Spacer(Modifier.height(5.dp))
        (1..daysInMonth).chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                week.forEach { day ->
                    val logged = day in loggedDays
                    Box(
                        Modifier.size(30.dp).clip(RoundedCornerShape(8.dp)).background(if (logged) FitGreen else Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            "$day", fontSize = 10.sp,
                            color = if (logged) Color.White else if (day == today) FitGreen else FitText,
                            fontWeight = if (day == today) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutDetailScreen(labels: Labels, id: String?, repo: FitLogRepository, unitsMetric: Boolean, onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    var workout by remember { mutableStateOf<WorkoutEntity?>(null) }
    var sets by remember { mutableStateOf<List<WorkoutSetEntity>>(emptyList()) }
    var editing by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf("") }
    val editState = remember { mutableStateListOf<SetInput>() }

    LaunchedEffect(id) {
        if (id != null) {
            val (w, s) = repo.getWorkout(id)
            workout = w; sets = s
        }
    }

    val totalVolumeKg = sets.sumOf { (it.weightKg * it.reps).toDouble() }.toFloat()
    val exerciseGroups = sets.groupBy { it.exerciseId }

    Column(Modifier.fillMaxSize().navigationBarsPadding()) {
        Header(workout?.name ?: "Workout Detail", onBack)
        Column(Modifier.weight(1f).padding(14.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Text("Total volume: ${formatWeight(totalVolumeKg, unitsMetric)}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("${sets.size} sets across ${exerciseGroups.size} exercises", fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(10.dp))

            if (!editing) {
              // Exercise cards scroll, so very long workouts stay reachable; the edit button stays pinned below.
              Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                if (sets.isEmpty()) Text("No sets recorded for this workout.", color = FitMuted, fontSize = 12.sp)
                exerciseGroups.forEach { (exerciseId, exerciseSets) ->
                    val summary = exerciseSets.joinToString(", ") { "${formatWeight(it.weightKg, unitsMetric)} x${it.reps}" }
                    Card(colors = CardDefaults.cardColors(containerColor = FitSurface), modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                        Column(Modifier.padding(12.dp)) {
                            Text(exerciseId.replaceFirstChar { it.uppercase() }, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Text(summary, fontSize = 12.sp, color = FitMuted)
                        }
                    }
                }
              }
                Spacer(Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        editName = workout?.name ?: ""
                        editState.clear()
                        editState.addAll(sets.map { SetInput(it.reps.toString(), it.weightKg.toInt().toString()) })
                        editing = true
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                ) { Text("${labels.edit.uppercase()} ${labels.workoutName.uppercase()}", fontSize = 12.sp) }
            } else {
                OutlinedTextField(
                    editName, { editName = it },
                    label = { Text(labels.workoutName) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(Modifier.height(10.dp))
                LazyColumn(Modifier.weight(1f)) {
                    items(sets.size) { i ->
                        val original = sets[i]
                        val edit = editState[i]
                        Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(original.exerciseId.replaceFirstChar { it.uppercase() }, Modifier.weight(1.4f), fontSize = 12.sp)
                            OutlinedTextField(edit.weight, { edit.weight = it }, singleLine = true, modifier = Modifier.weight(1f).height(50.dp))
                            OutlinedTextField(edit.reps, { edit.reps = it }, singleLine = true, modifier = Modifier.weight(1f).height(50.dp))
                        }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = { editing = false }, modifier = Modifier.weight(1f).height(48.dp)) { Text(labels.cancel, fontSize = 13.sp) }
                    Button(
                        onClick = {
                            val updated = sets.mapIndexed { i, s ->
                                s.copy(reps = editState[i].reps.toIntOrNull() ?: s.reps, weightKg = editState[i].weight.toFloatOrNull() ?: s.weightKg)
                            }
                            scope.launch {
                                val w = workout
                                if (w != null) {
                                    val renamed = w.copy(name = editName.trim().ifBlank { w.name })
                                    repo.saveWorkout(renamed, updated)
                                    workout = renamed
                                }
                                sets = updated
                                editing = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FitGreen),
                        modifier = Modifier.weight(1f).height(48.dp),
                    ) { Text(labels.save, fontSize = 13.sp) }
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    labels: Labels,
    prefs: AppPreferences,
    repo: FitLogRepository,
    currentUser: UserEntity?,
    unitsMetric: Boolean,
    language: String,
    workouts: List<WorkoutEntity>,
    allSets: List<WorkoutSetEntity>,
    onNotifications: () -> Unit,
    onLanguage: () -> Unit,
    onAchievements: () -> Unit,
    onLogout: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val notifFrequency by prefs.notificationFrequency.collectAsStateWithLifecycle(initialValue = "weekly")
    var editField by remember { mutableStateOf<String?>(null) }
    var editValue by remember { mutableStateOf("") }
    var editError by remember { mutableStateOf<String?>(null) }
    val restSecs by prefs.restSeconds.collectAsStateWithLifecycle(initialValue = 90)
    var showRestDialog by remember { mutableStateOf(false) }
    var restText by remember { mutableStateOf("90") }

    Column(Modifier.fillMaxSize()) {
        Header(labels.settings)
        // scrollable so every row is reachable on small phones
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(14.dp)) {
            Text("ACCOUNT", color = FitMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            SettingRow("Profile", "${currentUser?.name ?: "—"} ›", onClick = {
                editValue = currentUser?.name ?: ""; editError = null; editField = "name"
            })
            SettingRow("Email", "${currentUser?.email ?: "—"} ›", onClick = {
                editValue = currentUser?.email ?: ""; editError = null; editField = "email"
            })
            Spacer(Modifier.height(14.dp))
            Text("PREFERENCES", color = FitMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            // Opens the page with Daily / Weekly / Monthly / Off
            SettingRow("Push notifications", "${frequencyLabel(notifFrequency)} ›", onNotifications)
            SettingRow("Rest timer length", "${restSecs / 60}:${(restSecs % 60).toString().padStart(2, '0')} ›", onClick = {
                restText = restSecs.toString(); showRestDialog = true
            })
            SettingRow("Language", "${languageLabel(language)} ›", onLanguage)
            SettingRow(
                "Units",
                if (unitsMetric) "Metric (kg) ›" else "Imperial (lb) ›",
                onClick = { scope.launch { prefs.setUnitsMetric(!unitsMetric) } },
            )
            Spacer(Modifier.height(14.dp))
            Text("PROGRESS", color = FitMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            SettingRow(labels.achievements, "›", onAchievements)
            Spacer(Modifier.height(14.dp))
            Text("DATA", color = FitMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            SettingRow(labels.exportData, "›", onClick = {
                val byWorkout = allSets.groupBy { it.workoutId }
                ExportUtil.exportAndShare(context, workouts, byWorkout)
            })
            SettingRow("Log out", "›", onClick = onLogout)
        }
    }

    if (showRestDialog) {
        AlertDialog(
            onDismissRequest = { showRestDialog = false },
            title = { Text("Rest timer length") },
            text = {
                OutlinedTextField(
                    restText, { restText = it.filter(Char::isDigit).take(4) },
                    label = { Text("Seconds (5 - 3600)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    restText.toIntOrNull()?.let { secs -> scope.launch { prefs.setRestSeconds(secs) } }
                    showRestDialog = false
                }) { Text(labels.save, color = FitGreen, fontWeight = FontWeight.Bold) }
            },
            dismissButton = { TextButton(onClick = { showRestDialog = false }) { Text(labels.cancel) } },
        )
    }

    val fieldBeingEdited = editField
    if (fieldBeingEdited != null) {
        val user = currentUser
        AlertDialog(
            onDismissRequest = { editField = null },
            title = { Text(if (fieldBeingEdited == "name") "Edit profile name" else "Edit email") },
            text = {
                OutlinedTextField(
                    editValue, { editValue = it },
                    singleLine = true,
                    label = { Text(if (fieldBeingEdited == "name") "Name" else "Email") },
                    isError = editError != null,
                    supportingText = { editError?.let { Text(it, color = FitRed) } },
                    keyboardOptions = KeyboardOptions(keyboardType = if (fieldBeingEdited == "email") KeyboardType.Email else KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val value = editValue.trim()
                    if (user == null) { editField = null; return@TextButton }
                    if (fieldBeingEdited == "name") {
                        if (value.isEmpty()) { editError = "Name can't be empty"; return@TextButton }
                        scope.launch { repo.upsertUser(user.copy(name = value)); Log.d(TAG, "Profile name updated") }
                        editField = null
                    } else {
                        val email = value.lowercase()
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { editError = "Enter a valid email address"; return@TextButton }
                        scope.launch {
                            val existing = repo.getUserByEmail(email)
                            if (existing != null && existing.id != user.id) {
                                editError = "That email is already in use"
                            } else {
                                repo.upsertUser(user.copy(email = email))   // saved to the Room database
                                Log.d(TAG, "Email updated in database")
                                editField = null
                            }
                        }
                    }
                }) { Text(labels.save, color = FitGreen, fontWeight = FontWeight.Bold) }
            },
            dismissButton = { TextButton(onClick = { editField = null }) { Text(labels.cancel) } },
        )
    }
}

private fun frequencyLabel(code: String) = when (code) { "daily" -> "Daily"; "monthly" -> "Monthly"; "off" -> "Off"; else -> "Weekly" }

@Composable
fun SettingRow(title: String, trailing: String, onClick: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth().border(1.dp, FitBorder, RoundedCornerShape(10.dp)).clickable { onClick() }.padding(horizontal = 13.dp, vertical = 13.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 13.sp, modifier = Modifier.weight(1f)); Text(trailing, fontSize = 12.sp, color = FitMuted)
    }
    Spacer(Modifier.height(6.dp))
}

@Composable
fun ToggleRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().border(1.dp, FitBorder, RoundedCornerShape(10.dp)).padding(horizontal = 13.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 13.sp, modifier = Modifier.weight(1f)); Switch(checked, onCheckedChange)
    }
    Spacer(Modifier.height(6.dp))
}

@Composable
fun SimplePage(title: String, onBack: () -> Unit, content: @Composable () -> Unit) {
    Column(Modifier.fillMaxSize().navigationBarsPadding()) { Header(title, onBack); content() }
}

@Composable
fun NotificationsScreen(prefs: AppPreferences) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var testStatus by remember { mutableStateOf<String?>(null) }
    val frequency by prefs.notificationFrequency.collectAsStateWithLifecycle(initialValue = "weekly")
    val workoutReminders by prefs.workoutRemindersEnabled.collectAsStateWithLifecycle(initialValue = true)
    val weeklyRecap by prefs.weeklyRecapEnabled.collectAsStateWithLifecycle(initialValue = true)
    val personalRecords by prefs.personalRecordsEnabled.collectAsStateWithLifecycle(initialValue = true)
    val streakRisk by prefs.streakRiskAlertsEnabled.collectAsStateWithLifecycle(initialValue = false)
    val reminderDays by prefs.reminderDays.collectAsStateWithLifecycle(initialValue = setOf("Mon", "Wed", "Fri"))

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(14.dp)) {
        Text("How often should we notify you?", color = FitMuted, fontSize = 12.sp)
        Spacer(Modifier.height(10.dp))
        listOf("daily" to "Daily", "weekly" to "Weekly", "monthly" to "Monthly", "off" to "Off").forEach { (code, label) ->
            val selected = frequency == code
            Row(
                Modifier.fillMaxWidth().padding(bottom = 7.dp)
                    .border(1.dp, if (selected) FitGreen else FitBorder, RoundedCornerShape(10.dp))
                    .background(if (selected) FitLightGreen else Color.White, RoundedCornerShape(10.dp))
                    .clickable {
                        scope.launch { prefs.setNotificationFrequency(code) }
                        ReminderScheduler.schedule(context, code)      // (re)schedule the on-phone reminders
                    }
                    .padding(13.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (selected) Icon(Icons.Default.Check, null, tint = FitDarkGreen)
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                showFitLogNotification(context, "FitLog test 🔔", "Notifications are working on this phone.")
                scope.launch { testStatus = PushRegistrar.sendServerTest(context.applicationContext) }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
        ) { Text("Send test notification", fontSize = 13.sp) }
        testStatus?.let { Text(it, color = FitMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp)) }
        Spacer(Modifier.height(12.dp))
        if (frequency == "off") {
            Text("Notifications are off. Pick Daily, Weekly or Monthly to choose what you get.", color = FitMuted, fontSize = 12.sp)
            return@Column
        }
        Text("What to notify me about", color = FitMuted, fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        ToggleRow("Workout reminders", workoutReminders) { scope.launch { prefs.setWorkoutReminders(it) } }
        ToggleRow("Weekly recap", weeklyRecap) { scope.launch { prefs.setWeeklyRecap(it) } }
        ToggleRow("New personal records", personalRecords) { scope.launch { prefs.setPersonalRecords(it) } }
        ToggleRow("Streak risk alerts", streakRisk) { scope.launch { prefs.setStreakRiskAlerts(it) } }
        Spacer(Modifier.height(8.dp))
        Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                Text("Reminder days", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                        val active = day in reminderDays
                        Box(
                            Modifier.size(34.dp).clip(RoundedCornerShape(17.dp))
                                .background(if (active) FitGreen else Color.White)
                                .border(1.dp, FitGreen, RoundedCornerShape(17.dp))
                                .clickable { scope.launch { prefs.toggleReminderDay(day) } },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(day.take(1), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (active) Color.White else FitDarkGreen)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun LanguageScreen(prefs: AppPreferences, language: String) {
    val scope = rememberCoroutineScope()
    Column(Modifier.padding(14.dp)) {
        Text("Choose your language", color = FitMuted, fontSize = 12.sp)
        Spacer(Modifier.height(10.dp))
        LanguageRow("English", "en", language, prefs, scope)
        Spacer(Modifier.height(7.dp))
        LanguageRow("isiZulu", "zu", language, prefs, scope)
    }
}

@Composable
fun LanguageRow(name: String, code: String, selected: String, prefs: AppPreferences, scope: kotlinx.coroutines.CoroutineScope) {
    Row(
        Modifier.fillMaxWidth()
            .border(1.dp, if (selected == code) FitGreen else FitBorder, RoundedCornerShape(10.dp))
            .background(if (selected == code) FitLightGreen else Color.White)
            .clickable { scope.launch { prefs.setLanguage(code) } }
            .padding(13.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(name, fontSize = 13.sp, modifier = Modifier.weight(1f))
        if (selected == code) Icon(Icons.Default.Check, null, tint = FitGreen)
    }
}

@Composable
fun AchievementsScreen(workouts: List<WorkoutEntity>, sets: List<WorkoutSetEntity>, streakWeeks: Int) {
    val achievements = remember(workouts, sets, streakWeeks) { computeAchievements(workouts, sets, streakWeeks) }
    LazyColumn(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
        items(achievements) { badge -> AchievementCard(badge) }
    }
}

@Composable
fun AchievementCard(badge: Achievement) {
    Card(
        colors = CardDefaults.cardColors(containerColor = if (badge.unlocked) FitLightGreen else FitSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(badge.emoji, fontSize = 26.sp, modifier = Modifier.width(44.dp))
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    badge.title, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                    color = if (badge.unlocked) FitText else FitMuted,
                )
                Text(
                    badge.description, fontSize = 12.sp,
                    color = if (badge.unlocked) FitMuted else FitMuted.copy(alpha = 0.7f),
                )
            }
            if (!badge.unlocked) {
                Text("Locked", fontSize = 11.sp, color = FitMuted)
            }
        }
    }
}

@Composable
fun ProfileScreen(labels: Labels, currentUser: UserEntity?, workouts: List<WorkoutEntity>, allSets: List<WorkoutSetEntity>, streakWeeks: Int, onLogout: () -> Unit) {
    val unlocked = remember(workouts, allSets, streakWeeks) { computeAchievements(workouts, allSets, streakWeeks).filter { it.unlocked } }
    val totalWorkouts = workouts.size
    val totalMinutes = workouts.sumOf { it.durationSeconds } / 60

    Column(Modifier.fillMaxSize()) {
        Header(labels.profile)
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(68.dp).clip(CircleShape).background(AvatarBg), contentAlignment = Alignment.Center) {
                val initial = (currentUser?.name?.firstOrNull() ?: 'A').uppercaseChar()
                Text("$initial", color = AvatarFg, fontWeight = FontWeight.Bold, fontSize = 28.sp)
            }
            Spacer(Modifier.height(10.dp))
            Text(currentUser?.name ?: "Athlete", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(currentUser?.email ?: "", color = FitMuted, fontSize = 12.sp)

            Spacer(Modifier.height(18.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                ProfileStat("$totalWorkouts", labels.statWorkouts)
                ProfileStat("$streakWeeks", labels.statStreak)
                ProfileStat("$totalMinutes", labels.statMinutes)
            }

            Spacer(Modifier.height(20.dp))
            Text("${labels.achievements} (${unlocked.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            if (unlocked.isEmpty()) {
                Text("Nothing unlocked yet — finish a workout to earn your first badge.", color = FitMuted, fontSize = 12.sp, modifier = Modifier.fillMaxWidth())
            } else {
                unlocked.forEach { badge -> AchievementCard(badge); Spacer(Modifier.height(8.dp)) }
            }
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth().border(1.dp, FitBorder, RoundedCornerShape(10.dp)).clickable { onLogout() }.padding(15.dp),
            ) {
                Text("Log out", color = FitRed, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ProfileStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = FitDarkGreen)
        Text(label, color = FitMuted, fontSize = 11.sp)
    }
}

private fun computeWeekStreak(workouts: List<WorkoutEntity>): Int {
    if (workouts.isEmpty()) return 0
    val weeksWithWorkout = workouts.map {
        val c = java.util.Calendar.getInstance().apply { timeInMillis = it.dateTime }
        c.get(java.util.Calendar.YEAR) * 100 + c.get(java.util.Calendar.WEEK_OF_YEAR)
    }.toSet()

    var streak = 0
    val probe = java.util.Calendar.getInstance()
    while (true) {
        val key = probe.get(java.util.Calendar.YEAR) * 100 + probe.get(java.util.Calendar.WEEK_OF_YEAR)
        if (key in weeksWithWorkout) { streak++; probe.add(java.util.Calendar.WEEK_OF_YEAR, -1) } else break
    }
    return streak
}