package com.fitlog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitlog.app.data.AppPreferences
import com.fitlog.app.data.FitLogRepository
import com.fitlog.app.data.WorkoutEntity
import com.fitlog.app.data.WorkoutSetEntity
import com.fitlog.app.ui.theme.FitBorder
import com.fitlog.app.ui.theme.FitGreen
import com.fitlog.app.ui.theme.FitDarkGreen
import com.fitlog.app.ui.theme.FitLightGreen
import com.fitlog.app.ui.theme.FitLogTheme
import com.fitlog.app.ui.theme.FitMuted
import com.fitlog.app.ui.theme.FitSurface
import com.fitlog.app.ui.theme.FitText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

public enum class Screen { Splash, Login, Home, StartWorkout, ActiveWorkout, Summary, History, Detail, Settings, Notifications, Language, Profile }

public data class Labels(
    val home: String, val history: String, val settings: String, val profile: String,
    val startWorkout: String, val recentWorkouts: String, val currentStreak: String,
    val thisWeek: String, val workouts: String, val volume: String, val pushDay: String,
    val legDay: String, val pullDay: String, val finishWorkout: String, val done: String,
    val activeWorkout: String, val workoutSummary: String, val notifications: String,
    val language: String, val english: String, val isiZulu: String, val save: String
)

private val EN = Labels("Home", "History", "Settings", "Profile", "Start Workout", "Recent workouts", "Current streak: 4 weeks", "This week", "workouts", "volume", "Push Day", "Leg Day", "Pull Day", "Finish Workout", "Done", "Active Workout", "Workout Summary", "Notifications", "Language", "English", "isiZulu", "Save")
private val ZU = Labels("Ikhaya", "Umlando", "Izilungiselelo", "Iphrofayela", "Qala Ukujima", "Ukujima kwakamuva", "Uchungechunge lwamanje: amasonto angu-4", "Kuleli sonto", "ukujima", "umthamo", "Usuku Lokucindezela", "Usuku Lwemilenze", "Usuku Lokudonsa", "Qeda Ukujima", "Kwenziwe", "Ukujima Okuqhubekayo", "Isifinyezo Sokujima", "Izaziso", "Ulimi", "IsiNgisi", "isiZulu", "Gcina")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { FitLogTheme { FitLogApp() } }
    }
}

@Composable
fun FitLogApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repo = remember { FitLogRepository(context) }
    val prefs = remember { AppPreferences(context) }
    val scope = rememberCoroutineScope()
    val workouts by repo.workouts.collectAsStateWithLifecycle(initialValue = emptyList())
    val language by prefs.language.collectAsStateWithLifecycle(initialValue = "en")
    var screen by remember { mutableStateOf(Screen.Splash) }
    var selectedWorkout by remember { mutableStateOf<String?>(null) }
    var currentWorkoutId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        repo.ensureSeedData()
        delay(900)
        screen = Screen.Login
    }

    val labels = if (language == "zu") ZU else EN
    when (screen) {
        Screen.Splash -> SplashScreen()
        Screen.Login -> LoginScreen(onLogin = { screen = Screen.Home })
        Screen.Home -> MainScaffold(labels, Screen.Home, { screen = it }, onStart = { screen = Screen.StartWorkout }, content = {
            HomeScreen(labels, workouts, onStart = { screen = Screen.StartWorkout }, onWorkout = { selectedWorkout = it; screen = Screen.Detail })
        })
        Screen.StartWorkout -> MainScaffold(labels, Screen.Home, { screen = it }, onStart = { screen = Screen.StartWorkout }, content = {
            StartWorkoutScreen(labels, onBack = { screen = Screen.Home }, onStart = {
                val id = UUID.randomUUID().toString()
                currentWorkoutId = id
                scope.launch {
                    repo.saveWorkout(WorkoutEntity(id, "local-user", "Push Day", System.currentTimeMillis(), 0), emptyList())
                }
                screen = Screen.ActiveWorkout
            })
        })
        Screen.ActiveWorkout -> ActiveWorkoutScreen(labels, onFinish = { screen = Screen.Summary }, onBack = { screen = Screen.Home })
        Screen.Summary -> SummaryScreen(labels, onDone = { screen = Screen.Home })
        Screen.History -> MainScaffold(labels, Screen.History, { screen = it }, onStart = { screen = Screen.StartWorkout }, content = {
            HistoryScreen(labels, workouts, onWorkout = { selectedWorkout = it; screen = Screen.Detail })
        })
        Screen.Detail -> WorkoutDetailScreen(labels, selectedWorkout, workouts, onBack = { screen = Screen.History })
        Screen.Settings -> MainScaffold(labels, Screen.Settings, { screen = it }, onStart = { screen = Screen.StartWorkout }, content = {
            SettingsScreen(labels, prefs, onNotifications = { screen = Screen.Notifications }, onLanguage = { screen = Screen.Language })
        })
        Screen.Notifications -> SimplePage(labels.notifications, onBack = { screen = Screen.Settings }) { NotificationsScreen(prefs) }
        Screen.Language -> SimplePage(labels.language, onBack = { screen = Screen.Settings }) { LanguageScreen(prefs, language) }
        Screen.Profile -> MainScaffold(labels, Screen.Profile, { screen = it }, onStart = { screen = Screen.StartWorkout }, content = {
            ProfileScreen(labels)
        })
    }
}

@Composable
fun SplashScreen() {
    Box(Modifier.fillMaxSize().background(FitGreen), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            DumbbellLogo(64)
            Spacer(Modifier.height(16.dp))
            Text("FITLOG", color = androidx.compose.ui.graphics.Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}

@Composable
fun DumbbellLogo(size: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp), modifier = Modifier.height(size.dp)) {
        Box(Modifier.width((size * .13f).dp).height((size * .46f).dp).clip(RoundedCornerShape(2.dp)).background(androidx.compose.ui.graphics.Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .72f).dp).clip(RoundedCornerShape(2.dp)).background(androidx.compose.ui.graphics.Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .38f).dp).background(androidx.compose.ui.graphics.Color.White))
        Box(Modifier.width((size * .30f).dp).height((size * .10f).dp).background(androidx.compose.ui.graphics.Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .38f).dp).background(androidx.compose.ui.graphics.Color.White))
        Box(Modifier.width((size * .10f).dp).height((size * .72f).dp).clip(RoundedCornerShape(2.dp)).background(androidx.compose.ui.graphics.Color.White))
        Box(Modifier.width((size * .13f).dp).height((size * .46f).dp).clip(RoundedCornerShape(2.dp)).background(androidx.compose.ui.graphics.Color.White))
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
        Box(Modifier.fillMaxWidth().height(72.dp).background(FitGreen), contentAlignment = Alignment.Center) { DumbbellLogo(30) }
        Column(Modifier.fillMaxWidth().padding(horizontal = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(22.dp))
            Text("Track your training, anywhere.", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(email, { email = it }, label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(password, { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(username, { username = it }, label = { Text("Username") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            Button(onClick = onLogin, modifier = Modifier.fillMaxWidth().height(46.dp), colors = ButtonDefaults.buttonColors(containerColor = FitDarkGreen)) { Text("LOG IN") }
            Spacer(Modifier.height(12.dp))
            Text("or continue with", color = FitMuted, fontSize = 11.sp)
            Spacer(Modifier.height(10.dp))
            OutlinedButton(onClick = onLogin, modifier = Modifier.fillMaxWidth().height(46.dp)) { Text("G Sign in with Google") }
        }
        Spacer(Modifier.weight(1f))
        Box(Modifier.fillMaxWidth().height(56.dp).background(FitGreen))
    }
}

@Composable
fun MainScaffold(labels: Labels, current: Screen, navigate: (Screen) -> Unit, onStart: () -> Unit, content: @Composable () -> Unit) {
    Scaffold(
        bottomBar = {
            Row(Modifier.fillMaxWidth().background(FitGreen).windowInsetsPadding(WindowInsets.navigationBars).padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
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
        Icon(icon, text, tint = if (selected) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.White.copy(alpha = .55f), modifier = Modifier.size(19.dp))
        Text(text, color = if (selected) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.White.copy(alpha = .55f), fontSize = 9.sp)
    }
}

@Composable
fun HomeScreen(labels: Labels, workouts: List<WorkoutEntity>, onStart: () -> Unit, onWorkout: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(ColorWhite)) {
        Header("FitLog")
        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 14.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            item {
                Spacer(Modifier.height(2.dp))
                Text("Welcome back, Alex", color = FitMuted, fontSize = 11.sp)
                Spacer(Modifier.height(8.dp))
                Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text(labels.thisWeek, color = FitDarkGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text("3 workouts", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("42 sets • 1,240 kg volume", fontSize = 9.sp, color = FitText)
                    }
                }
                Spacer(Modifier.height(9.dp))
                Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(44.dp), colors = ButtonDefaults.buttonColors(containerColor = FitGreen), shape = RoundedCornerShape(9.dp)) { Icon(Icons.Default.PlayArrow, null); Text("  ${labels.startWorkout}") }
                Spacer(Modifier.height(12.dp))
                Text(labels.currentStreak, color = FitDarkGreen, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Spacer(Modifier.height(7.dp))
                Text(labels.recentWorkouts, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            items(if (workouts.isEmpty()) emptyList() else workouts.take(4)) { workout ->
                WorkoutCard(workout) { onWorkout(workout.id) }
            }
            item { Spacer(Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun Header(title: String, back: (() -> Unit)? = null) {
    Row(Modifier.fillMaxWidth().height(53.dp).background(FitGreen).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        if (back != null) IconButton(onClick = back) { Icon(Icons.Default.ArrowBack, null, tint = androidx.compose.ui.graphics.Color.White) }
        DumbbellLogo(25)
        Spacer(Modifier.size(9.dp))
        Text(title, color = androidx.compose.ui.graphics.Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun WorkoutCard(workout: WorkoutEntity, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = FitSurface), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Column(Modifier.padding(11.dp)) {
            Text(workout.name, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            Text(if (workout.name == "Push Day") "Yesterday • 5 exercises" else "3 days ago • 6 exercises", color = FitMuted, fontSize = 9.sp)
        }
    }
}

@Composable
fun StartWorkoutScreen(labels: Labels, onBack: () -> Unit, onStart: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Header(labels.startWorkout, onBack)
        Column(Modifier.padding(14.dp)) {
            Text("Search or pick an exercise", color = FitMuted, fontSize = 10.sp)
            OutlinedTextField("", {}, placeholder = { Text("Search exercises...") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("All", "Chest", "Back", "Legs", "Arms").forEachIndexed { i, item ->
                    Box(Modifier.clip(RoundedCornerShape(15.dp)).background(if (i == 0) FitGreen else FitSurface).border(1.dp, FitBorder, RoundedCornerShape(15.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) { Text(item, fontSize = 9.sp, color = if (i == 0) androidx.compose.ui.graphics.Color.White else FitText) }
                }
            }
            Spacer(Modifier.height(10.dp))
            listOf("Bench Press", "Incline Dumbbell Press", "Overhead Press", "Push-Up", "Cable Fly").forEach { exercise ->
                ExerciseRow(exercise)
                Spacer(Modifier.height(7.dp))
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(45.dp), colors = ButtonDefaults.buttonColors(containerColor = FitGreen)) { Text("ADD SELECTED →") }
        }
    }
}

@Composable
fun ExerciseRow(name: String) {
    Row(Modifier.fillMaxWidth().border(1.dp, FitBorder, RoundedCornerShape(10.dp)).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(name, fontSize = 11.sp, modifier = Modifier.weight(1f))
        Text("+", color = FitGreen, fontSize = 19.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ActiveWorkoutScreen(labels: Labels, onFinish: () -> Unit, onBack: () -> Unit) {
    val sets = remember { mutableStateListOf(mutableStateOf("6"), mutableStateOf("6"), mutableStateOf("6")) }
    var seconds by remember { mutableIntStateOf(90) }
    LaunchedEffect(Unit) { while (seconds > 0) { delay(1000); seconds-- } }
    Column(Modifier.fillMaxSize()) {
        Header("Push Day", onBack)
        LazyColumn(Modifier.weight(1f).padding(12.dp)) {
            item {
                Text("Elapsed 00:24:10", color = FitMuted, fontSize = 9.sp)
                Spacer(Modifier.height(7.dp))
                Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(10.dp)) { Text("Bench Press", fontWeight = FontWeight.Bold); Text("Last time: 65kg × 6", fontSize = 9.sp) } }
                Spacer(Modifier.height(9.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) { Text("SET", Modifier.weight(1f), fontSize = 9.sp); Text("PREV", Modifier.weight(1.2f), fontSize = 9.sp); Text("KG", Modifier.weight(1.2f), fontSize = 9.sp); Text("REPS", Modifier.weight(1.2f), fontSize = 9.sp); Spacer(Modifier.width(36.dp)) }
            }
            items(sets.size) { index ->
                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("${index + 1}", Modifier.weight(1f), fontSize = 10.sp)
                    Text(if (index == 0) "65x6" else "60x6", Modifier.weight(1.2f), fontSize = 9.sp, color = FitMuted)
                    OutlinedTextField("60", {}, singleLine = true, modifier = Modifier.weight(1.2f).height(48.dp))
                    OutlinedTextField(sets[index].value, { sets[index].value = it }, singleLine = true, modifier = Modifier.weight(1.2f).height(48.dp))
                    Icon(Icons.Default.Check, null, tint = FitGreen, modifier = Modifier.size(24.dp))
                }
            }
            item {
                Text("+ Add set", color = FitGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(14.dp))
                Card(colors = CardDefaults.cardColors(containerColor = FitSurface), shape = RoundedCornerShape(9.dp), modifier = Modifier.fillMaxWidth()) { Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Timer, null, tint = FitGreen); Spacer(Modifier.width(7.dp)); Text("Rest timer: ${seconds / 60}:${(seconds % 60).toString().padStart(2, '0')}", fontSize = 11.sp) } }
                Spacer(Modifier.height(120.dp))
            }
        }
        Button(onClick = onFinish, modifier = Modifier.fillMaxWidth().padding(12.dp).height(45.dp), colors = ButtonDefaults.buttonColors(containerColor = FitGreen)) { Text(labels.finishWorkout) }
    }
}

@Composable
fun SummaryScreen(labels: Labels, onDone: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Header(labels.workoutSummary)
        LazyColumn(Modifier.weight(1f).padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(11.dp), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(12.dp)) { Text("Push Day complete!", fontWeight = FontWeight.Bold); Text("Duration: 42 min", fontSize = 9.sp); Text("Volume: 1,850 kg • 16 sets", fontSize = 9.sp); Text("New PR: Bench Press 65kg", fontSize = 9.sp) } } }
            item { Text("Exercises", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            listOf("Bench Press" to "3 sets • 65kg x 6", "Incline DB Press" to "3 sets • 24kg x 10", "Cable Fly" to "3 sets • 18kg x 12").forEach { (a,b) -> item { Card(colors = CardDefaults.cardColors(containerColor = FitSurface), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(11.dp)) { Text(a, fontSize = 11.sp, fontWeight = FontWeight.SemiBold); Text(b, fontSize = 9.sp, color = FitMuted) } } } }
        }
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth().padding(14.dp).height(45.dp), colors = ButtonDefaults.buttonColors(containerColor = FitGreen)) { Text(labels.done) }
    }
}

@Composable
fun HistoryScreen(labels: Labels, workouts: List<WorkoutEntity>, onWorkout: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Header(labels.history)
        LazyColumn(Modifier.fillMaxSize().padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Text("August 2026", fontWeight = FontWeight.Bold, fontSize = 15.sp); CalendarMock() }
            item { Text("Recent sessions", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
            items(workouts) { WorkoutCard(it) { onWorkout(it.id) } }
        }
    }
}

@Composable
fun CalendarMock() {
    val days = listOf(3,5,8,10,12,17,19,24)
    Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { listOf("S","M","T","W","T","F","S").forEach { Text(it, fontSize = 8.sp, color = FitMuted) } }
        Spacer(Modifier.height(5.dp))
        (1..31).chunked(7).forEach { week -> Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { week.forEach { day -> Box(Modifier.size(28.dp).clip(RoundedCornerShape(7.dp)).background(if (day in days) FitGreen else androidx.compose.ui.graphics.Color.Transparent), contentAlignment = Alignment.Center) { Text("$day", fontSize = 8.sp, color = if (day in days) androidx.compose.ui.graphics.Color.White else FitText) } } } }
    }
}

@Composable
fun WorkoutDetailScreen(labels: Labels, id: String?, workouts: List<WorkoutEntity>, onBack: () -> Unit) {
    val workout = workouts.firstOrNull { it.id == id } ?: workouts.firstOrNull()
    Column(Modifier.fillMaxSize()) {
        Header(workout?.name ?: "Workout Detail", onBack)
        Column(Modifier.padding(14.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(12.dp)) { Text("Total volume: 1,850 kg", fontWeight = FontWeight.Bold); Text("16 sets across 3 exercises", fontSize = 9.sp) } }
            Spacer(Modifier.height(10.dp))
            listOf("Bench Press" to "65kg x6, 60kg x6, 60kg x6", "Incline DB Press" to "24kg x10 (3 sets)", "Cable Fly" to "18kg x12 (3 sets)").forEach { (a,b) -> Card(colors = CardDefaults.cardColors(containerColor = FitSurface), modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp)) { Column(Modifier.padding(11.dp)) { Text(a, fontWeight = FontWeight.SemiBold, fontSize = 11.sp); Text(b, fontSize = 9.sp, color = FitMuted) } } }
            Spacer(Modifier.weight(1f))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(45.dp)) { Text("EDIT WORKOUT") }
        }
    }
}

@Composable
fun SettingsScreen(labels: Labels, prefs: AppPreferences, onNotifications: () -> Unit, onLanguage: () -> Unit) {
    val scope = rememberCoroutineScope()
    val notifications by prefs.notificationsEnabled.collectAsStateWithLifecycle(initialValue = true)

    Column(Modifier.fillMaxSize()) {
        Header(labels.settings)
        Column(Modifier.padding(14.dp)) {
            Text("ACCOUNT", color = FitMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            SettingRow("Profile", "Alex")
            SettingRow("Email", "alex@email.com")
            Spacer(Modifier.height(13.dp))
            Text("PREFERENCES", color = FitMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            ToggleRow("Push notifications", notifications) { isEnabled ->
                scope.launch {
                    prefs.setNotifications(isEnabled)
                }
            }
            ToggleRow("Rest timer sound", true) {}
            SettingRow("Language", "English ›", onLanguage)
            SettingRow("Units", "Metric (kg) ›")
            Spacer(Modifier.height(13.dp))
            Text("DATA", color = FitMuted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            SettingRow("Sync now", "›")
            SettingRow("Log out", "›")
            Spacer(Modifier.height(15.dp))
            SettingRow(labels.notifications, "Manage", onNotifications)
        }
    }
}

@Composable
fun SettingRow(title: String, trailing: String, onClick: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth().border(1.dp, FitBorder, RoundedCornerShape(9.dp)).clickable { onClick() }.padding(horizontal = 12.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) { Text(title, fontSize = 10.sp, modifier = Modifier.weight(1f)); Text(trailing, fontSize = 10.sp, color = FitMuted) }
    Spacer(Modifier.height(5.dp))
}

@Composable
fun ToggleRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().border(1.dp, FitBorder, RoundedCornerShape(9.dp)).padding(horizontal = 12.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) { Text(title, fontSize = 10.sp, modifier = Modifier.weight(1f)); Switch(checked, onCheckedChange) }
    Spacer(Modifier.height(5.dp))
}

@Composable
fun SimplePage(title: String, onBack: () -> Unit, content: @Composable () -> Unit) { Column(Modifier.fillMaxSize()) { Header(title, onBack); content() } }

@Composable
fun NotificationsScreen(prefs: AppPreferences) {
    Column(Modifier.padding(14.dp)) {
        Text("Choose what you get notified about", color = FitMuted, fontSize = 10.sp)
        Spacer(Modifier.height(10.dp))
        ToggleRow("All push notifications", true) {}
        ToggleRow("Workout reminders", true) {}
        ToggleRow("Weekly recap", true) {}
        ToggleRow("New personal records", true) {}
        ToggleRow("Streak risk alerts", false) {}
        Spacer(Modifier.height(8.dp))
        Card(colors = CardDefaults.cardColors(containerColor = FitLightGreen), shape = RoundedCornerShape(11.dp), modifier = Modifier.fillMaxWidth()) { Column(Modifier.padding(11.dp)) { Text("Reminder days", fontWeight = FontWeight.Bold, fontSize = 11.sp); Text("M   T   W   T   F   S   S", fontSize = 10.sp) } }
    }
}

@Composable
fun LanguageScreen(prefs: AppPreferences, language: String) {
    val scope = rememberCoroutineScope()
    Column(Modifier.padding(14.dp)) {
        Text("Choose your language", color = FitMuted, fontSize = 10.sp)
        Spacer(Modifier.height(10.dp))
        LanguageRow("English", "en", language, prefs, scope)
        Spacer(Modifier.height(7.dp))
        LanguageRow("isiZulu", "zu", language, prefs, scope)
    }
}

@Composable
fun LanguageRow(name: String, code: String, selected: String, prefs: AppPreferences, scope: kotlinx.coroutines.CoroutineScope) {
    Row(Modifier.fillMaxWidth().border(1.dp, if (selected == code) FitGreen else FitBorder, RoundedCornerShape(9.dp)).background(if (selected == code) FitLightGreen else androidx.compose.ui.graphics.Color.White).clickable { scope.launch { prefs.setLanguage(code) } }.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { Text(name, fontSize = 11.sp, modifier = Modifier.weight(1f)); if (selected == code) Icon(Icons.Default.Check, null, tint = FitGreen) }
}

@Composable
fun ProfileScreen(labels: Labels) {
    Column(Modifier.fillMaxSize()) {
        Header(labels.profile)
        Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.size(62.dp).clip(CircleShape).background(FitLightGreen), contentAlignment = Alignment.Center) { Text("A", color = FitDarkGreen, fontWeight = FontWeight.Bold, fontSize = 22.sp) }
            Spacer(Modifier.height(7.dp)); Text("Alex", fontWeight = FontWeight.Bold); Text("alex@email.com", color = FitMuted, fontSize = 10.sp)
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { Stat("48", "Workouts"); Stat("4", "Week streak"); Stat("12,400", "kg lifted") }
            Spacer(Modifier.height(15.dp))
            listOf("Edit profile", "Achievements & badges", "Export my data", "Privacy & security", "Help & support").forEach { SettingRow(it, ">") }
        }
    }
}

@Composable
fun Stat(value: String, label: String) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(value, color = FitDarkGreen, fontWeight = FontWeight.Bold); Text(label, color = FitMuted, fontSize = 8.sp) } }

private val ColorWhite = androidx.compose.ui.graphics.Color.White
