package com.furkanhrmnc.filmscape.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.furkanhrmnc.filmscape.ui.screen.auth.account.AccountScreen
import com.furkanhrmnc.filmscape.ui.screen.auth.login.LoginScreen
import com.furkanhrmnc.filmscape.ui.screen.auth.register.RegisterScreen
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsScreen
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsUiEvents
import com.furkanhrmnc.filmscape.ui.screen.details.DetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.details.WatchVideoScreen
import com.furkanhrmnc.filmscape.ui.screen.favorite.FavoriteScreen
import com.furkanhrmnc.filmscape.ui.screen.favorite.FavoriteViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.home.HomeScreen
import com.furkanhrmnc.filmscape.ui.screen.main.home.HomeViewModel
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesScreen
import com.furkanhrmnc.filmscape.ui.screen.movies.MoviesViewModel
import com.furkanhrmnc.filmscape.ui.screen.person.PersonScreen
import com.furkanhrmnc.filmscape.ui.screen.person.PersonViewModel
import com.furkanhrmnc.filmscape.ui.screen.person.details.PersonDetailScreen
import com.furkanhrmnc.filmscape.ui.screen.person.details.PersonDetailsViewModel
import com.furkanhrmnc.filmscape.ui.screen.search.SearchScreen
import com.furkanhrmnc.filmscape.ui.screen.settings.SettingsScreen
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarMediasViewModel
import com.furkanhrmnc.filmscape.ui.screen.similar.SimilarScreen
import com.furkanhrmnc.filmscape.ui.screen.trending.TrendingScreen
import com.furkanhrmnc.filmscape.ui.screen.tv.TvScreen
import com.furkanhrmnc.filmscape.ui.screen.tv.TvViewModel
import com.furkanhrmnc.filmscape.util.MediaType
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun FilmScapeNavGraph() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    val startDestination =
        remember { if (auth.currentUser != null) Destinations.MAIN.route else Destinations.AUTH.route }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation(
            route = Destinations.AUTH.route,
            startDestination = Destinations.LOGIN.route
        ) {

            composable(route = Destinations.LOGIN.route) { LoginScreen(navController = navController) }

            composable(route = Destinations.REGISTER.route) { RegisterScreen(navController = navController) }

            composable(route = Destinations.ACCOUNT.route) {
                AccountScreen(
                    context = context,
                    exit = {
                        auth.signOut()
                        navController.navigate(Destinations.LOGIN.route) {
                            popUpTo(Destinations.ACCOUNT.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        navigation(
            route = Destinations.MAIN.route,
            startDestination = Destinations.HOME.route
        ) {

            composable(Destinations.HOME.route) {
                val viewModel = koinViewModel<HomeViewModel>()
                HomeScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = Destinations.TRENDING.route) { TrendingScreen(navController = navController) }

            composable(Destinations.MOVIES.route) {
                val viewModel = koinViewModel<MoviesViewModel>()
                MoviesScreen(viewModel = viewModel, navController = navController)
            }

            composable(Destinations.TV.route) {
                val viewModel = koinViewModel<TvViewModel>()
                TvScreen(viewModel = viewModel, navController = navController)
            }

            composable(route = Destinations.SETTINGS.route) { SettingsScreen() }
        }

        composable(
            route = "${Destinations.DETAILS.route}?id={id}&type={type}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("type") { type = NavType.StringType },
            )
        ) {
            val viewModel = koinViewModel<DetailsViewModel>()
            val id = it.arguments?.getInt("id") ?: 1
            val typeString = it.arguments?.getString("type")

            val type = typeString?.let { type -> MediaType.valueOf(type) }

            type?.let {
                LaunchedEffect(true) {
                    viewModel.onEvent(
                        DetailsUiEvents.SetDataAndLoad(
                            id = id,
                            type = type,
                        )
                    )
                }
            }

            DetailsScreen(
                viewModel = viewModel,
                onNavigate = { event -> navController.navigate(event.route) }
            )
        }

        composable(
            route = "${Destinations.SIMILAR.route}?id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt("id") ?: 1
            val viewModel: SimilarMediasViewModel = koinInject { parametersOf(id) }
            SimilarScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = "${Destinations.WATCH_VIDEO.route}?videoId={videoId}",
            arguments = listOf(
                navArgument("videoId") { type = NavType.StringType }
            )
        ) {
            val videoId = it.arguments?.getString("videoId") ?: ""
            Log.w("FilmScapeNavGraph", "WatchVideo composable -> $videoId")
            WatchVideoScreen(videoId = videoId)
        }

        composable(route = Destinations.FAVORITE.route) {
            auth.currentUser?.uid?.let { uid ->
                val viewModel = koinInject<FavoriteViewModel> { parametersOf(uid) }
                FavoriteScreen(viewModel = viewModel)
            }
        }

        composable(Destinations.SEARCH.route) { SearchScreen(navController = navController) }

        composable(route = Destinations.PERSON.route) {
            val viewModel = koinViewModel<PersonViewModel>()
            PersonScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = "${Destinations.PERSON_DETAILS.route}?id={id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt("id")
            val viewModel = koinInject<PersonDetailsViewModel> { parametersOf(id) }
            PersonDetailScreen(viewModel = viewModel)
        }
    }
}