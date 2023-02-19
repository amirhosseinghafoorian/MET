package com.example.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColors(
    primary = Orange800,
    onPrimary = White,
    primaryVariant = Orange900,
    secondary = White,
    surface = Blue800,
    background = Blue900,
    onSurface = Blue50,
    onBackground = Blue100
)

private val LightColorPalette = lightColors(
    primary = Orange800,
    onPrimary = White,
    primaryVariant = Orange900,
    secondary = Black,
    surface = White,
    background = Blue50,
    onSurface = Blue800,
    onBackground = Blue900
)

@Composable
fun MetTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalSizing provides Sizing()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}