package com.furkanhrmnc.filmscape.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesScreen

@Composable
fun FilmScapeNavigator() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = Routes.Movies.route) {
            MoviesScreen()
        }
        composable(
            route = "${Routes.Detail.route}?id={id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            // Detail
            id?.let { movieId ->
                DetailScreen(
                    movieId = movieId
                )
            }
        }
    }
}