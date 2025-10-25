package com.example.sneakers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    onBackground = TextColor,
    onPrimary = Color.White
)

private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = Color(0xFF121212),
    onBackground = Color.White,
    onPrimary = Color.White
)

@Composable
fun SneakersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
