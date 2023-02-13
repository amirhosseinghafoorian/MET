package com.example.met

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature_search.navigation.searchNavGraph
import com.example.feature_search.navigation.searchNavigationRoute

@Composable
fun AppNavGraph(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = searchNavigationRoute,
    ) {
        searchNavGraph(windowSizeClass) { id -> }
    }
}