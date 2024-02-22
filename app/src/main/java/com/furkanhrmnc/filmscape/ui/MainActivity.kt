package com.furkanhrmnc.filmscape.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.furkanhrmnc.filmscape.ui.navigation.MovieNavigation
import com.furkanhrmnc.filmscape.ui.screens.main.MainViewModel
import com.furkanhrmnc.filmscape.ui.theme.FilmScapeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmScapeTheme(
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

