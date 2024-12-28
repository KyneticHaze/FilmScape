package com.furkanhrmnc.filmscape.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.repository.StoreRepository
import com.furkanhrmnc.filmscape.util.Constants.SUBSCRIBED_MS
import com.furkanhrmnc.filmscape.util.ThemeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsViewModel(
    private val storeRepository: StoreRepository,
    private val languagePreferences: LanguagePreferences,
) : ViewModel() {

    val themeDialogVisibly = MutableStateFlow(false)
    val languageDialogVisibly = MutableStateFlow(false)

    val themeType: StateFlow<SettingsUiState> =
        storeRepository.themeTypeFLow.map { SettingsUiState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            initialValue = SettingsUiState(ThemeType.SYSTEM)
        )

    val isDynamic: StateFlow<Boolean> =
        storeRepository.isDynamicThemeFlow.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            initialValue = false
        )

    val selectedLanguage: StateFlow<String> = languagePreferences.languageStream
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Locale.getDefault().language
        )

    fun setThemeDialogVisibility(value: Boolean) {
        themeDialogVisibly.value = value
    }

    fun updateIsDynamicTheme() {
        storeRepository.setDynamicTheme(!isDynamic.value, viewModelScope)
    }

    fun updateThemeType(themeType: ThemeType) {
        storeRepository.setThemeType(themeType, viewModelScope)
    }

    fun changeLanguage(language: String) {
        viewModelScope.launch {
            languagePreferences.setLanguage(language)
        }
    }

    fun setLanguageDialogVisibility(value: Boolean) {
        languageDialogVisibly.value = value
    }
}