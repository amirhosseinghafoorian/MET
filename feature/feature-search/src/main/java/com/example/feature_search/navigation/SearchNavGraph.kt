package com.example.feature_search.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.ui.base.BaseAppState
import com.example.ui.base.rememberBaseAppState
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
        windowSizeClass = windowSizeClass
    )
) {
    SystemUiControllerManager(
        statusBarColor = baseState.statusBarColor,
        navigationBarColor = baseState.navigationBarColor
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "search")
    }
}