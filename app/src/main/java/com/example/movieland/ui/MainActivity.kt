package com.example.movieland.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieland.ui.navigation.MovieNavigation
import com.example.movieland.ui.screens.home_screen.main_screen.MainViewModel
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

