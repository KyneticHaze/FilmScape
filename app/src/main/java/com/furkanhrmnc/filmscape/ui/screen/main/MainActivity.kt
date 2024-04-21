package com.furkanhrmnc.filmscape.ui.screen.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.furkanhrmnc.filmscape.navigation.FilmScapeNavigator
import com.furkanhrmnc.filmscape.ui.theme.FilmScapeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)

        setContent {
            FilmScapeTheme(
                dynamicColor = false
            ) {

                val viewModel = hiltViewModel<MainViewModel>()
                val mainUIState by viewModel.mainUiState.collectAsState()

                FilmScapeNavigator(
                    mainUIState = mainUIState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

