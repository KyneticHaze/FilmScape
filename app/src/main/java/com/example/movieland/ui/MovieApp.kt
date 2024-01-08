package com.example.movieland.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movieland.ui.navigation.MovieNavigation

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize(1f)
    ) {
        MovieNavigation(navController = navController)
    }
}