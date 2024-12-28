package com.furkanhrmnc.filmscape.ui.screen.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.furkanhrmnc.filmscape.navigation.FilmScapeNavGraph
import com.furkanhrmnc.filmscape.ui.screen.auth.login.LoginViewModel
import com.furkanhrmnc.filmscape.ui.screen.settings.LanguageAwareApp
import com.furkanhrmnc.filmscape.ui.screen.settings.SettingsViewModel
import com.furkanhrmnc.filmscape.ui.theme.FilmScapeTheme
import org.koin.androidx.compose.koinViewModel

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
            val settingsViewModel = koinViewModel<SettingsViewModel>()
            LanguageAwareApp(viewModel = settingsViewModel) {
                FilmScapeTheme {
                    val loginViewModel = koinViewModel<LoginViewModel>()
                    installSplashScreen().apply {
                        setKeepOnScreenCondition { loginViewModel.splashScreen.value }
                    }
                    FilmScapeNavGraph()
                }
            }
        }
    }
}