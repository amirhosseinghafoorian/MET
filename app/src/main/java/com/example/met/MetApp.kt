package com.example.met

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.example.designsystem.theme.MetTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MetApp(
    windowSizeClass: WindowSizeClass,
) {
    MetTheme(windowSizeClass) {
        val systemUiController = rememberSystemUiController()
        val backgroundColor = MaterialTheme.colors.background
        val surfaceColor = MaterialTheme.colors.surface

        SideEffect {
            systemUiController.setStatusBarColor(surfaceColor)
            systemUiController.setNavigationBarColor(backgroundColor)
        }

        AppNavGraph()
    }
}