package com.example.feature_search.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature_search.R
import com.example.feature_search.navigation.SearchScreens.SearchScreenRoute
import com.example.feature_search.search.SearchRoute
import com.example.ui.base.BaseAppState
import com.example.ui.base.rememberBaseAppState
import com.example.ui.component.AppTopBar
import com.example.ui.component.SystemUiControllerManager

fun NavGraphBuilder.searchNavGraph(
    windowSizeClass: WindowSizeClass,
    onSearchDetail: (Int) -> Unit
) {
    composable(route = searchNavigationRoute) {
        SearchHost(
            onSearchDetail = onSearchDetail,
            windowSizeClass = windowSizeClass
        )
    }
}

@Composable
fun SearchHost(
    onSearchDetail: (Int) -> Unit,
    windowSizeClass: WindowSizeClass,
    baseState: BaseAppState = rememberBaseAppState(
        windowSizeClass = windowSizeClass,
        screenTitles = mapOf(
            SearchScreenRoute.route to R.string.title_met
        )
    )
) {
    val scaffoldState = rememberScaffoldState()

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
                actionIcon = baseState.actionIcon
            )
        }
    ) {
        NavHost(
            navController = baseState.navController,
            startDestination = SearchScreenRoute.route,
        ) {
            composable(
                route = SearchScreenRoute.route,
            ) {
                SearchRoute {
                    onSearchDetail(5)
                }
            }
        }
    }
}