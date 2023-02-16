package com.example.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizing(
    val small: Dp = 64.dp,
    val medium: Dp = 128.dp,
    val large: Dp = 256.dp,
)

val LocalSizing = compositionLocalOf {
    Sizing()
}

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current