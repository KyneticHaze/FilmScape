package com.furkanharmanc.movieland.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.furkanharmanc.movieland.ui.theme.MovieLandTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieLandTheme(
                dynamicColor = false
            ) {
                MovieApp()
            }
        }
    }
}