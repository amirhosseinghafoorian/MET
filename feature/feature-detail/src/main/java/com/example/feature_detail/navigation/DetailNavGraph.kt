package com.example.feature_detail.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.common.AppConstants.keyId
import com.example.feature_detail.R
import com.example.feature_detail.detail.DetailRoute
import com.example.feature_detail.navigation.DetailScreens.DetailScreenRoute
import com.example.feature_detail.navigation.DetailScreens.ShowImageScreenRoute
import com.example.feature_detail.show_image.ShowImageRoute
import com.example.ui.base.BaseAppState
import com.example.ui.base.rememberBaseAppState
import com.example.ui.component.AppTopBar

fun NavGraphBuilder.detailNavGraph(
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
            onNavigateUp = onNavigateUp
        )
    }
}

@Composable
fun DetailHost(
    id: Int,
    onNavigateUp: () -> Unit,
    baseState: BaseAppState = rememberBaseAppState(
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
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
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

                DetailRoute { url ->
                    baseState.navController.navigate(
                        ShowImageScreenRoute.generateRoute(url)
                    )
                }
            }
            composable(
                route = ShowImageScreenRoute.route,
                arguments = listOf(
                    navArgument(keyImage) {
                        nullable = false
                        type = NavType.StringType
                    }
                )
            ) { navBackStackEntry ->
                val imageUrl = navBackStackEntry.arguments?.getString(keyImage)

                ShowImageRoute(imageUrl)
            }
        }
    }
}