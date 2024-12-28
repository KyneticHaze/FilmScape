package com.furkanhrmnc.filmscape.ui.screen.settings

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun LanguageAwareApp(viewModel: SettingsViewModel, content: @Composable () -> Unit) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    CompositionLocalProvider(
        value = LocalContext provides LocalContext.current.createConfigurationContext(
            Configuration(LocalContext.current.resources.configuration).apply {
                setLocale(
                    Locale(
                        selectedLanguage
                    )
                )
            }
        ),
        content = content
    )
}