package com.example.feature_search.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feature_search.R
import com.example.feature_search.navigation.SearchScreens.SearchScreenRoute
import com.example.feature_search.search.SearchRoute
import com.example.ui.base.BaseAppState
import com.example.ui.base.rememberBaseAppState
import com.example.ui.component.AppTopBar

fun NavGraphBuilder.searchNavGraph(
    onSearchDetail: (Int) -> Unit
) {
    composable(route = searchNavigationRoute) {
        SearchHost(
            onSearchDetail = onSearchDetail
        )
    }
}

@Composable
fun SearchHost(
    onSearchDetail: (Int) -> Unit,
    baseState: BaseAppState = rememberBaseAppState(
        screenTitles = mapOf(
            SearchScreenRoute.route to R.string.title_met
        )
    )
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppTopBar(
                title = baseState.titleTextId,
                canNavigateBack = baseState.isNavigationIconVisible,
                actionIcon = baseState.actionIcon
            )
        }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = baseState.navController,
            startDestination = SearchScreenRoute.route,
        ) {
            composable(
                route = SearchScreenRoute.route,
            ) {
                SearchRoute(scaffoldState) { id ->
                    onSearchDetail(id)
                }
            }
        }
    }
}