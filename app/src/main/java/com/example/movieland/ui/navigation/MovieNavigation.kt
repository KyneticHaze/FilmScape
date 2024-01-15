package com.example.movieland.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.movieland.ui.screens.app_start_screen.intro.IntroScreen
import com.example.movieland.ui.screens.app_start_screen.splash.SplashScreen
import com.example.movieland.ui.screens.auth_screen.login.LoginScreen
import com.example.movieland.ui.screens.auth_screen.register.RegisterScreen
import com.example.movieland.ui.screens.home_screen.main_screen.MainScreen
import com.example.movieland.ui.screens.home_screen.detail_screen.DetailScreen
import com.example.movieland.ui.screens.home_screen.main_screen.other_screens.PlayingScreen
import com.example.movieland.ui.screens.home_screen.main_screen.other_screens.PopularScreen
import com.example.movieland.ui.screens.home_screen.main_screen.other_screens.ProfileScreen
import com.example.movieland.ui.screens.home_screen.main_screen.other_screens.UpcomingScreen
import com.example.movieland.ui.screens.home_screen.main_screen.other_screens.RatedScreen
import com.example.movieland.ui.screens.home_screen.main_screen.search_screen.SearchScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MovieNavigation(
    navController: NavHostController,
) {
    val auth = Firebase.auth
    val startDestinationWithAuth =
        if (auth.currentUser != null) Routes.Home.route else Routes.AppStart.route

    NavHost(
        navController = navController,
        startDestination = startDestinationWithAuth,
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
        navigation(
            startDestination = Screens.Splash.screen,
            route = Routes.AppStart.route
        ) {
            composable(Screens.Splash.screen) {
                SplashScreen(navController)
            }
            composable(Screens.Intro.screen) {
                IntroScreen(navController)
            }
        }
        navigation(
            startDestination = Screens.Login.screen,
            route = Routes.Auth.route
        ) {
            composable(Screens.Login.screen) {
                LoginScreen(navController)
            }
            composable(Screens.Register.screen) {
                RegisterScreen(navController)
            }
        }
        navigation(
            startDestination = Screens.Main.screen,
            route = Routes.Home.route
        ) {
            composable(Screens.Main.screen) {
                MainScreen(navController = navController) {
                    navController.navigate("${Screens.Detail.screen}/$it")
                }
            }
            composable(
                route = "${Screens.Detail.screen}/{movie_id}",
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
            composable(Screens.Upcoming.screen) {
                UpcomingScreen()
            }
            composable(Screens.TopRated.screen) {
                RatedScreen()
            }
            composable(Screens.Playing.screen) {
                PlayingScreen()
            }
            composable(Screens.Popular.screen) {
                PopularScreen()
            }
            composable(Screens.Search.screen) {
                SearchScreen(navController)
            }
            composable(Screens.Profile.screen) {
                ProfileScreen(navController)
            }
        }
    }
}