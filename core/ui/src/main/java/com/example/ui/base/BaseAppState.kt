package com.example.ui.base

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.Icon

@Composable
fun rememberBaseAppState(
    navController: NavHostController = rememberNavController(),
    windowSizeClass: WindowSizeClass,
    screenTitles: Map<String, Int> = mapOf(),
    screenActionIcons: Map<String, Icon> = mapOf(),
    statusBarColors: Map<String, Color> = mapOf(),
    navigationBarColors: Map<String, Color> = mapOf(),
    navigationIconInVisibleRoutes: Set<String> = setOf()
): BaseAppState {
    val backgroundColor = MaterialTheme.colors.background
    return remember(
        navController,
        windowSizeClass,
        screenTitles,
        screenActionIcons,
        statusBarColors,
        navigationBarColors,
        navigationIconInVisibleRoutes
    ) {
        BaseAppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            screenTitles = screenTitles,
            screenActionIcons = screenActionIcons,
            navigationIconInVisibleRoutes = navigationIconInVisibleRoutes,
            backgroundColor = backgroundColor
        )
    }
}

class BaseAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    private val screenTitles: Map<String, Int> = mapOf(),
    private val screenActionIcons: Map<String, Icon> = mapOf(),
    private val statusBarColors: Map<String, Color> = mapOf(),
    private val navigationBarColors: Map<String, Color> = mapOf(),
    private val navigationIconInVisibleRoutes: Set<String> = setOf(),
    private val backgroundColor : Color
) {
    val currentRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route

    val titleTextId: Int?
        @Composable get() = currentRoute?.let { screenTitles[it] }

    val actionIdonId: Icon?
        @Composable get() = currentRoute?.let { screenActionIcons[it] }

    val isNavigationIconVisible: Boolean
        @Composable get() = currentRoute?.let { it !in navigationIconInVisibleRoutes } ?: true

    val statusBarColor : Color
        @Composable get() = currentRoute?.let { statusBarColors[it] } ?: backgroundColor

    val navigationBarColor : Color
        @Composable get() = currentRoute?.let { navigationBarColors[it] } ?: backgroundColor

    // todo define a variable which defines the screen type using windowSizeClass
}