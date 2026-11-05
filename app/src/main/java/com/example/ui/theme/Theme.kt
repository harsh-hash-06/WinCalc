package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = WinCalcBlue,
    onPrimary = Color.White,
    background = WinCalcDarkBg,
    onBackground = WinCalcDarkTextPrimary,
    surface = WinCalcDarkSurface,
    onSurface = WinCalcDarkTextPrimary,
    surfaceVariant = WinCalcDarkOpBtn,
    onSurfaceVariant = WinCalcDarkTextSecondary,
    outline = WinCalcDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = WinCalcBlue,
    onPrimary = Color.White,
    background = WinCalcBg,
    onBackground = WinCalcTextPrimary,
    surface = WinCalcSurface,
    onSurface = WinCalcTextPrimary,
    surfaceVariant = WinCalcOpBtn,
    onSurfaceVariant = WinCalcTextSecondary,
    outline = WinCalcBorder
)

@Composable
fun WinCalcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    WinCalcTheme(darkTheme = darkTheme, content = content)
}
