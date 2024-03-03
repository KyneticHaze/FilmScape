package com.furkanhrmnc.filmscape.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState

@Composable
fun FilmScapeNav(
    mainUIState: MainUIState
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            MainScreen(
                navController = navController,
                mainUIState = mainUIState,
                onEvent =
            )
        }
        composable(route = Routes.Detail.route) {
            DetailScreen(navController)
        }
    }
}