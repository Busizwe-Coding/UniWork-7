package com.fitlog.app.ui.theme

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

/** Android's built-in "sans-serif-condensed" font - used for every piece of text in FitLog. */
val FitFontFamily = FontFamily(Typeface.create("sans-serif-condensed", Typeface.NORMAL))

/**
 * Wrap the app in this (inside FitLogTheme). It rewrites every Material text style to use
 * [FitFontFamily] (buttons, text fields, dialogs, login page...) and sets the default text style
 * so plain Text() calls use it too. Nothing else about the theme changes.
 */
@Composable
fun FitFontTheme(content: @Composable () -> Unit) {
    val base = MaterialTheme.typography
    val condensed = Typography(
        displayLarge = base.displayLarge.copy(fontFamily = FitFontFamily),
        displayMedium = base.displayMedium.copy(fontFamily = FitFontFamily),
        displaySmall = base.displaySmall.copy(fontFamily = FitFontFamily),
        headlineLarge = base.headlineLarge.copy(fontFamily = FitFontFamily),
        headlineMedium = base.headlineMedium.copy(fontFamily = FitFontFamily),
        headlineSmall = base.headlineSmall.copy(fontFamily = FitFontFamily),
        titleLarge = base.titleLarge.copy(fontFamily = FitFontFamily),
        titleMedium = base.titleMedium.copy(fontFamily = FitFontFamily),
        titleSmall = base.titleSmall.copy(fontFamily = FitFontFamily),
        bodyLarge = base.bodyLarge.copy(fontFamily = FitFontFamily),
        bodyMedium = base.bodyMedium.copy(fontFamily = FitFontFamily),
        bodySmall = base.bodySmall.copy(fontFamily = FitFontFamily),
        labelLarge = base.labelLarge.copy(fontFamily = FitFontFamily),
        labelMedium = base.labelMedium.copy(fontFamily = FitFontFamily),
        labelSmall = base.labelSmall.copy(fontFamily = FitFontFamily),
    )
    MaterialTheme(colorScheme = MaterialTheme.colorScheme, shapes = MaterialTheme.shapes, typography = condensed) {
        ProvideTextStyle(TextStyle(fontFamily = FitFontFamily), content)
    }
}
