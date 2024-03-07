package com.furkanhrmnc.filmscape.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.furkanhrmnc.filmscape.ui.navigation.FilmScapeNav
import com.furkanhrmnc.filmscape.ui.screen.main.MainViewModel
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
                val mainUIState by viewModel.mainUiState.collectAsState()

                FilmScapeNav(
                    mainUIState = mainUIState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

