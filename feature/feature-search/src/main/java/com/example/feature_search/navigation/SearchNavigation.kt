package com.example.feature_search.navigation

const val searchNavigationRoute = "navigation_search"

sealed class SearchScreens(val route: String) {
    object SearchScreenRoute : SearchScreens("screen_search")
}