package com.kopidannyemil.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Espresso = Color(0xFF1C1B1A)
val Caramel = Color(0xFFC8814A)
val CaramelDark = Color(0xFF8A4B1F)
val Cream = Color(0xFFFFF4E8)
val CreamCard = Color(0xFFFFF1E3)
val Peach = Color(0xFFF7D3AE)
val AppBackground = Color(0xFFFFFBF7)
val Mint = Color(0xFFB8F0CF)
val MintDark = Color(0xFF0E7A43)

private val KopiColorScheme = lightColorScheme(
    primary = Espresso,
    onPrimary = Color.White,
    secondary = Caramel,
    onSecondary = Color.White,
    primaryContainer = Peach,
    onPrimaryContainer = CaramelDark,
    background = AppBackground,
    onBackground = Espresso,
    surface = Color.White,
    onSurface = Espresso,
    surfaceVariant = Cream,
    onSurfaceVariant = Color(0xFF6B6058),
    outline = Color(0xFFCFC4B8),
    error = Color(0xFFB3261E)
)

@Composable
fun KopiDanNyemilTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KopiColorScheme,
        typography = Typography(),
        content = content
    )
}
