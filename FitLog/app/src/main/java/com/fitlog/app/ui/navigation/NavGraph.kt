package com.fitlog.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fitlog.app.FitLogApp
import com.fitlog.app.ui.history.HistoryScreen
import com.fitlog.app.ui.home.HomeScreen
import com.fitlog.app.ui.login.LoginScreen
import com.fitlog.app.ui.profile.ProfileScreen
import com.fitlog.app.ui.register.RegisterScreen
import com.fitlog.app.ui.settings.LanguageScreen
import com.fitlog.app.ui.settings.SettingsScreen
import com.fitlog.app.ui.theme.FitLogGreen
import com.fitlog.app.ui.theme.FitLogGreenDark
import com.fitlog.app.ui.workout.ActiveWorkoutScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val ACTIVE_WORKOUT = "active_workout"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
    const val LANGUAGE = "language"
    const val PROFILE = "profile"
}

private data class BottomDestination(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val bottomDestinations = listOf(
    BottomDestination(Routes.HOME, "Home", Icons.Filled.Home),
    BottomDestination(Routes.HISTORY, "History", Icons.Filled.History),
    BottomDestination(Routes.SETTINGS, "Settings", Icons.Filled.Settings),
    BottomDestination(Routes.PROFILE, "Profile", Icons.Filled.Person),
)

@Composable
fun FitLogNavGraph(startLoggedIn: Boolean) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomDestinations.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = FitLogGreenDark) {
                    bottomDestinations.forEach { dest ->
                        val selected = currentRoute == dest.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(dest.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(dest.icon, contentDescription = dest.label) },
                            label = { Text(dest.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = androidx.compose.ui.graphics.Color.White,
                                selectedTextColor = androidx.compose.ui.graphics.Color.White,
                                indicatorColor = FitLogGreen,
                                unselectedIconColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f),
                                unselectedTextColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f),
                            ),
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = if (startLoggedIn) Routes.HOME else Routes.LOGIN,
            modifier = Modifier.padding(padding),
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = { navController.navigate(Routes.HOME) { popUpTo(0) } },
                    onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                )
            }
            composable(Routes.REGISTER) {
                RegisterScreen(
                    onRegisterSuccess = { navController.navigate(Routes.HOME) { popUpTo(0) } },
                    onNavigateToLogin = { navController.popBackStack() },
                )
            }
            composable(Routes.HOME) {
                HomeScreen(onStartWorkout = { navController.navigate(Routes.ACTIVE_WORKOUT) })
            }
            composable(Routes.ACTIVE_WORKOUT) {
                val app = LocalContext.current.applicationContext as FitLogApp
                val userId = app.container.let { "local-user" } // resolved from TokenStore in the full build
                ActiveWorkoutScreen(userId = userId, onFinished = { navController.popBackStack() })
            }
            composable(Routes.HISTORY) { HistoryScreen() }
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    onLoggedOut = { navController.navigate(Routes.LOGIN) { popUpTo(0) } },
                    onOpenLanguage = { navController.navigate(Routes.LANGUAGE) },
                )
            }
            composable(Routes.LANGUAGE) { LanguageScreen(onBack = { navController.popBackStack() }) }
            composable(Routes.PROFILE) { ProfileScreen() }
        }
    }
}
