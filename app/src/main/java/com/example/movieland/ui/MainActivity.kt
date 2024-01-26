package com.example.movieland.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieland.core.Routes
import com.example.movieland.ui.screens.home_screen.detail_screen.DetailScreen
import com.example.movieland.ui.screens.home_screen.main_screen.MainScreen
import com.example.movieland.ui.screens.home_screen.main_screen.MainUIState
import com.example.movieland.ui.screens.home_screen.main_screen.MainUiEvent
import com.example.movieland.ui.screens.home_screen.main_screen.MainViewModel
import com.example.movieland.ui.screens.splash.SplashScreen
import com.example.movieland.ui.theme.MovieLandTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieLandTheme(
                dynamicColor = false
            ) {
                val viewModel = hiltViewModel<MainViewModel>()
                val uiState = viewModel.mainUiState.collectAsState().value

                MovieNavigation(
                    uiState = uiState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

@Composable
fun MovieNavigation(
    uiState : MainUIState,
    onEvent: (MainUiEvent) -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route,
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
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }
        composable(Routes.Main.route) {
            MainScreen(
                navController = navController,
                mainUIState = uiState,
                onEvent = onEvent
            )
        }
        composable(
            route = "${Routes.Detail.route}/{movie_id}",
            arguments = listOf(
                navArgument(
                    name = "movie_id"
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            DetailScreen(navController)
        }
    }
}