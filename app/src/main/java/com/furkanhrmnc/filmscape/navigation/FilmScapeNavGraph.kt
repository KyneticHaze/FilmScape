package com.furkanhrmnc.filmscape.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.furkanhrmnc.filmscape.navigation.components.Screen
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsScreen
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.favorite.FavoriteScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.popular.PopularScreen
import com.furkanhrmnc.filmscape.ui.screen.search.SearchScreen
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarScreen
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun FilmScapeNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = "${Screen.DETAILS.route}?id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val movieId = it.arguments?.getInt("id") ?: 1
            val viewModel: DetailsViewModel = koinInject<DetailsViewModel> { parametersOf(movieId) }

            DetailsScreen(
                viewModel = viewModel,
                onNavigate = { event ->
                    navController.navigate(event.route)
                }
            )
        }
        composable(
            route = "${Screen.SIMILAR.route}?id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val movieId = it.arguments?.getInt("id") ?: 1
            SimilarScreen(
                movieId = movieId,
                navController = navController
            )
        }

        composable(Screen.SEARCH.route) {
            SearchScreen(navController = navController)
        }

        composable(route = Screen.POPULAR.route) {
            PopularScreen(navController = navController)
        }

        composable(route = Screen.FAVORITE.route) {
            FavoriteScreen()
        }
    }
}