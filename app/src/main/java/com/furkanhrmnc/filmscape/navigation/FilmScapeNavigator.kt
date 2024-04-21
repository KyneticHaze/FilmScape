package com.furkanhrmnc.filmscape.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState

@Composable
fun FilmScapeNavigator(
    mainUIState: MainUIState
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            MainScreen(
                mainUIState = mainUIState,
                navController = navController
            )
        }
    }
}