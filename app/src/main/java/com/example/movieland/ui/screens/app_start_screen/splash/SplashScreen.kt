package com.example.movieland.ui.screens.app_start_screen.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieland.R
import com.example.movieland.ui.navigation.Routes
import com.example.movieland.ui.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController : NavController
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(composition = composition)

    LaunchedEffect(key1 = progress) {
        delay(300L)
        navController.navigate(Screens.Intro.screen)
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}