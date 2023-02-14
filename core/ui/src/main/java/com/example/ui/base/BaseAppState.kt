package com.example.ui.base

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    navigationIconVisibleRoutes: Set<String> = setOf()
): BaseAppState {
    return remember(
        navController,
        windowSizeClass,
        screenTitles,
        screenActionIcons,
        navigationIconVisibleRoutes
    ) {
        BaseAppState(
            navController = navController,
            windowSizeClass = windowSizeClass,
            screenTitles = screenTitles,
            screenActionIcons = screenActionIcons,
            navigationIconVisibleRoutes = navigationIconVisibleRoutes,
        )
    }
}

class BaseAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    private val screenTitles: Map<String, Int> = mapOf(),
    private val screenActionIcons: Map<String, Icon> = mapOf(),
    private val navigationIconVisibleRoutes: Set<String> = setOf(),
) {
    private val currentRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route

    val titleTextId: Int?
        @Composable get() = currentRoute?.let { screenTitles[it] }

    val actionIcon: Icon?
        @Composable get() = currentRoute?.let { screenActionIcons[it] }

    val isNavigationIconVisible: Boolean
        @Composable get() = currentRoute?.let { it in navigationIconVisibleRoutes } ?: false

    // todo define a variable which defines the screen type using windowSizeClass
}