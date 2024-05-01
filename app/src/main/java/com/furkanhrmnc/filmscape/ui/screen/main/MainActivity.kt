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
import com.furkanhrmnc.filmscape.navigation.FilmScapeNavGraph
import com.furkanhrmnc.filmscape.ui.theme.FilmScapeTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Ana aktivite.
 *
 * Materyal tasarım fonksiyonu içerisinde gezinme fonksiyonumuzu yazdık.
 *
 * [enableEdgeToEdge] ile status bar'ı değiştirdik.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        setContent {
            FilmScapeTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainUiState by mainViewModel.mainUiState.collectAsState()
                FilmScapeNavGraph(
                    mainUiState = mainUiState
                )
            }
        }
    }
}

