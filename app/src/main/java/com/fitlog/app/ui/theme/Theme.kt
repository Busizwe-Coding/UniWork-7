package com.fitlog.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val FitGreen = Color(0xFF20D200)
val FitDarkGreen = Color(0xFF3BB100)
val FitLightGreen = Color(0xFF92FF6E)
val FitRed = Color(0xFFED3636)
val FitYellow = Color(0xFFFFEE00)
val FitSurface = Color(0xFFF8F8F8)
val FitBorder = Color(0xFFE5E5E5)
val FitText = Color(0xFF202020)
val FitMuted = Color(0xFF858585)

private val Colors = lightColorScheme(
    primary = FitGreen,
    onPrimary = Color.White,
    primaryContainer = FitLightGreen,
    onPrimaryContainer = FitText,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = FitSurface,
    outline = FitBorder,
    onBackground = FitText,
    onSurface = FitText
)

@Composable
fun FitLogTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colors, typography = Typography(), content = content)
}
