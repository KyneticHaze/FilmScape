package com.furkanhrmnc.filmscape.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.furkanhrmnc.filmscape.navigation.components.Routes
import com.furkanhrmnc.filmscape.ui.screen.actors.ActorsScreen
import com.furkanhrmnc.filmscape.ui.screen.auth.AccountScreen
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesScreen
import com.furkanhrmnc.filmscape.ui.screen.search.SearchScreen
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarScreen
import com.furkanhrmnc.filmscape.util.Category
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmScapeNavGraph() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.MAIN.route
    ) {
        composable(route = Routes.MAIN.route) {
            val mainViewModel = koinViewModel<MainViewModel>()
            val mainUiState by mainViewModel.mainUiState.collectAsState()
            MainScreen(
                mainUiState = mainUiState,
                navController = navController
            )
        }
        composable(
            route = "${Routes.MOVIES.route}?categoryString={categoryString}"
        ) { navBackStackEntry ->
            val categoryString = navBackStackEntry.arguments?.getString("categoryString")
            MoviesScreen(
                category = Category.getCategoryTypeName(categoryString),
                navController = navController
            )
        }
        composable(Routes.SEARCH.route) {
            SearchScreen(navController = navController)
        }
        composable(
            route = "${Routes.DETAILS.route}?id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("id") ?: 1
            DetailsScreen(
                movieId = movieId,
                navController = navController
            )
        }
        composable(route = "${Routes.SIMILAR.route}?id={id}") {
            val movieId = it.arguments?.getInt("id") ?: 1
            SimilarScreen(
                movieId = movieId,
                navController = navController
            )
        }
        composable(route = Routes.ACCOUNT.route) {
            AccountScreen {
                navController.navigate(Routes.MAIN.route) {
                    navController.popBackStack()
                }
            }
        }
        composable(route = Routes.ACTORS.route) {
            ActorsScreen()
        }
    }
}