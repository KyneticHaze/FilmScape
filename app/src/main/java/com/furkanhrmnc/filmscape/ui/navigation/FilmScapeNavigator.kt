package com.furkanhrmnc.filmscape.ui.navigation

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
import com.furkanhrmnc.filmscape.ui.screen.video.WatchVideoScreen
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailScreen
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailUIEvents
import com.furkanhrmnc.filmscape.ui.screen.detail.DetailViewModel
import com.furkanhrmnc.filmscape.ui.screen.main.MainScreen
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIEvents
import com.furkanhrmnc.filmscape.ui.screen.main.MainUIState

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
                navController = navController,
                mainUIState = mainUIState,
                onEvent = onEvent
            )
        }
        composable(
            route = "${Routes.Detail.route}?id={id}&type={type}&category={category}",
            arguments = listOf(
                navArgument("id") { NavType.IntType },
                navArgument("type") { NavType.StringType },
                navArgument("category") { NavType.StringType }
            )
        ) { navBackStackEntry ->

            val id = navBackStackEntry.arguments?.getInt("id") ?: 0
            val type = navBackStackEntry.arguments?.getString("type") ?: ""
            val category = navBackStackEntry.arguments?.getString("category") ?: ""

            
            LaunchedEffect(key1 = true) {
                viewModel.onEvent(
                    DetailUIEvents.SetDataAndLoad(
                        id = id,
                        type = type,
                        category = category
                    )
                )
            }
            
            detailUiState.media?.let { media ->
                DetailScreen(
                    navController = navController,
                    media = media,
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