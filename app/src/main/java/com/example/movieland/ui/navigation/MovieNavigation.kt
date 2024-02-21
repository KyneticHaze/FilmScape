package com.example.movieland.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieland.core.Routes
import com.example.movieland.ui.screens.home_screen.detail_screen.DetailEvents
import com.example.movieland.ui.screens.home_screen.detail_screen.DetailScreen
import com.example.movieland.ui.screens.home_screen.detail_screen.DetailViewModel
import com.example.movieland.ui.screens.home_screen.main_screen.MainScreen
import com.example.movieland.ui.screens.home_screen.main_screen.MainUIState
import com.example.movieland.ui.screens.home_screen.main_screen.MainUiEvent
import com.example.movieland.ui.theme.customFont
import com.example.movieland.ui.util.SomethingWentWrong

@Composable
fun MovieNavigation(
    uiState: MainUIState,
    onEvent: (MainUiEvent) -> Unit
) {

    val navController = rememberNavController()

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val detailUiState =
        detailViewModel.detailUiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(500))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { 500 },
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeOut(tween(500))
        }
    ) {
        composable(Routes.Home.route) {
            MainScreen(
                mainUIState = uiState
            )
        }
        composable(
            route = "${Routes.Detail.route}?id={id}",
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType },
            )
        ) { navBackStackEntry ->

            val viewModel = hiltViewModel<DetailViewModel>()

            val id = navBackStackEntry.arguments?.getInt("id") ?: 0

            LaunchedEffect(
                key1 = true,
                block = {
                    viewModel.onEvent(
                        DetailEvents.SetDataOnLoad(id = id)
                    )
                }
            )

            if (detailUiState.media != null) {
                DetailScreen(
                    navController = navController,
                    media = detailUiState.media,
                    detailUiState = detailUiState,
                    onEvent = viewModel::onEvent
                )
            } else {
                SomethingWentWrong()
            }
        }
    }
}