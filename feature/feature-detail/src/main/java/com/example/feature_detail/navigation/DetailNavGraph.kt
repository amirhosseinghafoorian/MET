package com.example.feature_detail.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.feature_detail.R
import com.example.feature_detail.detail.DetailRoute
import com.example.feature_detail.navigation.DetailScreens.DetailScreenRoute
import com.example.feature_detail.navigation.DetailScreens.ShowImageScreenRoute
import com.example.feature_detail.show_image.ShowImageRoute
import com.example.ui.base.BaseAppState
import com.example.ui.base.rememberBaseAppState
import com.example.ui.component.AppTopBar
import com.example.ui.component.SystemUiControllerManager

fun NavGraphBuilder.detailNavGraph(
    windowSizeClass: WindowSizeClass,
    onNavigateUp: () -> Unit
) {
    composable(
        route = detailNavigationRoute,
        arguments = listOf(
            navArgument(keyId) {
                nullable = false
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getInt(keyId) ?: -1
        DetailHost(
            id = id,
            onNavigateUp = onNavigateUp,
            windowSizeClass = windowSizeClass
        )
    }
}

@Composable
fun DetailHost(
    id: Int,
    onNavigateUp: () -> Unit,
    windowSizeClass: WindowSizeClass,
    baseState: BaseAppState = rememberBaseAppState(
        windowSizeClass = windowSizeClass,
        screenTitles = mapOf(
            DetailScreenRoute.route to R.string.title_detail
        ),
        navigationIconVisibleRoutes = setOf(
            DetailScreenRoute.route,
            ShowImageScreenRoute.route
        )
    )
) {
    val scaffoldState = rememberScaffoldState()
    val backStackEntry by baseState.navController.currentBackStackEntryAsState()

    SystemUiControllerManager(
        statusBarColor = baseState.statusBarColor,
        navigationBarColor = baseState.navigationBarColor
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppTopBar(
                title = baseState.titleTextId,
                canNavigateBack = baseState.isNavigationIconVisible,
                actionIcon = baseState.actionIcon,
                onNavigateUp = {
                    if (backStackEntry?.destination?.route == DetailScreenRoute.route) {
                        onNavigateUp()
                    } else {
                        baseState.navController.navigateUp()
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = baseState.navController,
            startDestination = DetailScreenRoute.route,
        ) {
            composable(
                route = DetailScreenRoute.route,
                arguments = listOf(
                    navArgument(keyId) {
                        nullable = false
                        type = NavType.IntType
                    }
                )
            ) { navBackStackEntry ->
                navBackStackEntry.arguments?.putInt(keyId, id)

                DetailRoute()
            }
            composable(
                route = ShowImageScreenRoute.route,
            ) {
                ShowImageRoute()
            }
        }
    }
}