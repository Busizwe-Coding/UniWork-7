package com.fitlog.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = FitLogGreen,
    onPrimary = Color.White,
    primaryContainer = FitLogGreenLight,
    onPrimaryContainer = FitLogGreenDark,
    secondary = FitLogYellow,
    onSecondary = Color(0xFF3A3200),
    tertiary = FitLogRed,
    onTertiary = Color.White,
    error = FitLogRed,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = SurfaceGrey,
    outline = BorderGrey,
)

private val DarkColors = darkColorScheme(
    primary = FitLogGreen,
    onPrimary = Color.Black,
    primaryContainer = FitLogGreenDark,
    onPrimaryContainer = FitLogGreenLight,
    secondary = FitLogYellow,
    tertiary = FitLogRed,
    error = FitLogRed,
)

@Composable
fun FitLogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = FitLogTypography,
        content = content,
    )
}
