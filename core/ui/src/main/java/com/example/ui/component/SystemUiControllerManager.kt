package com.example.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemUiControllerManager(
    statusBarColor: Color = MaterialTheme.colors.background,
    navigationBarColor: Color = MaterialTheme.colors.background,
) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = statusBarColor) {
        systemUiController.setStatusBarColor(statusBarColor)
    }

    LaunchedEffect(key1 = navigationBarColor) {
        systemUiController.setNavigationBarColor(navigationBarColor)
    }
}