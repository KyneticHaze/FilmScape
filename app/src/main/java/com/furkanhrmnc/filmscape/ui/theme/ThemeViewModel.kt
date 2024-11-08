package com.furkanhrmnc.filmscape.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanhrmnc.filmscape.domain.repository.StoreRepository
import com.furkanhrmnc.filmscape.util.Constants.SUBSCRIBED_MS
import com.furkanhrmnc.filmscape.util.ThemeType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ThemeViewModel(storeRepository: StoreRepository) : ViewModel() {

    val isDynamic: StateFlow<Boolean> =
        storeRepository.isDynamicThemeFlow.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            initialValue = true
        )

    val isDarkTheme: StateFlow<ThemeType> =
        storeRepository.themeTypeFLow.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            initialValue = ThemeType.SYSTEM
        )
}