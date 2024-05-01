package com.furkanhrmnc.filmscape.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.components.ErrorScreen
import com.furkanhrmnc.filmscape.ui.screen.actors.ActorsScreen
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainUiState
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesScreen
import com.furkanhrmnc.filmscape.ui.screen.search.SearchScreen
import com.furkanhrmnc.filmscape.ui.screen.tv.TvScreen
import com.furkanhrmnc.filmscape.util.Category

/**
 * Ui gezinmelerini kontrol eden [NavHost] bileşeni bu composable'dadır.
 *
 * Bu composable MainActivity içerisinde bulunuyor.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmScapeNavGraph(
    mainUiState: MainUiState
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME.route
    ) {

        composable(route = Routes.HOME.route) {
            MainScreen(
                mainUiState = mainUiState,
                navController = navController
            )
        }

        composable(
            route = "${Routes.MOVIES.route}?categoryString={categoryString}"
        ) {navBackStackEntry ->
            val categoryString =
                navBackStackEntry.arguments?.getString("categoryString")

            val category = categoryString?.let { Category.valueOf(it) }

            if (category != null) {
                MoviesScreen(
                    category = category,
                    navController = navController
                )
            } else {
                ErrorScreen(errorMessage = "Screen Not Found")
            }
        }

        composable(
            route = "${Routes.DETAILS.route}?id={id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            // Detail
            id?.let { movieId ->
                DetailsScreen(
                    movieId = movieId
                )
            }
        }

        composable(route = Routes.ACTORS.route) {
            ActorsScreen()
        }

        composable(route = Routes.TV.route) {
            TvScreen()
        }

        composable(Routes.SEARCH.route) {
            SearchScreen()
        }
    }
}