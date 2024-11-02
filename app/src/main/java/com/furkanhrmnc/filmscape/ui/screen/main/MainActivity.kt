package com.furkanhrmnc.filmscape.ui.screen.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.furkanhrmnc.filmscape.navigation.FilmScapeNavGraph
import com.furkanhrmnc.filmscape.ui.theme.FilmScapeTheme

/**
 * Ana aktivite.
 *
 * Materyal tasarım fonksiyonu içerisinde gezinme fonksiyonumuzu yazdık.
 *
 * [enableEdgeToEdge] ile status bar'ı değiştirdik.
 */
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmScapeTheme {
                FilmScapeNavGraph()
            }
        }
    }
}

