package com.furkanhrmnc.filmscape.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.furkanhrmnc.filmscape.presentation.screen.video.WatchVideoScreen
import com.furkanhrmnc.filmscape.presentation.screen.detail.DetailScreen
import com.furkanhrmnc.filmscape.presentation.screen.detail.DetailUIEvents
import com.furkanhrmnc.filmscape.presentation.screen.detail.DetailViewModel
import com.furkanhrmnc.filmscape.presentation.screen.main.MainScreen
import com.furkanhrmnc.filmscape.presentation.screen.main.MainUIEvents
import com.furkanhrmnc.filmscape.presentation.screen.main.MainUIState

@Composable
fun FilmScapeNavigator(
    mainUIState: MainUIState,
    onEvent: (MainUIEvents) -> Unit
) {

    val navController = rememberNavController()

    val viewModel = hiltViewModel<DetailViewModel>()
    val detailUiState by viewModel.detailUiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            MainScreen(
                mainUIState = mainUIState,
                navController = navController,
                onEvent = onEvent
            )
        }
        composable(
            route = "${Routes.Detail.route}?id={id}",
            arguments = listOf(
                navArgument("id") { NavType.IntType },
            )
        ) {

            detailUiState.movie?.let { media ->
                DetailScreen(
                    navController = navController,
                    movie = media,
                    detailUiState = detailUiState,
                    onEvent = viewModel::onEvent
                )
            }
        }

        composable(
            route = "${Routes.Video.route}?videoKey={videoKey}",
            arguments = listOf(
                navArgument("videoKey") { NavType.StringType }
            )
        ) {navBackStackEntry ->
            val videoKey = navBackStackEntry.arguments?.getString("videoKey")

            videoKey?.let {
                WatchVideoScreen(
                    videoKey = videoKey,
                    lifeCycleOwner = lifecycleOwner
                )
            }
        }
    }
}