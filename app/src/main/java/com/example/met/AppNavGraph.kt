package com.example.met

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature_detail.navigation.detailNavGraph
import com.example.feature_detail.navigation.generateDetailNavigationRoute
import com.example.feature_search.navigation.searchNavGraph
import com.example.feature_search.navigation.searchNavigationRoute

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = searchNavigationRoute
    ) {
        searchNavGraph { id ->
            navController.navigate(
                generateDetailNavigationRoute(id)
            )
        }
        detailNavGraph { navController.navigateUp() }
    }
}