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

class SettingsViewModel(
    private val storeRepository: StoreRepository,
) : ViewModel() {

    val dialogVisibly = MutableStateFlow(false)

    val themeType: StateFlow<ThemeTypeState> =
        storeRepository.themeTypeFLow.map { ThemeTypeState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            initialValue = ThemeTypeState(ThemeType.SYSTEM)
        )

    val isDynamic: StateFlow<Boolean> =
        storeRepository.isDynamicThemeFlow.map { it }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(SUBSCRIBED_MS),
            initialValue = false
        )

    fun setDialogVisibility(value: Boolean) {
        dialogVisibly.value = value
    }

    fun updateIsDynamicTheme() {
        storeRepository.setDynamicTheme(!isDynamic.value, viewModelScope)
    }

    fun updateThemeType(themeType: ThemeType) {
        storeRepository.setThemeType(themeType, viewModelScope)
    }
}

data class ThemeTypeState(
    val selectedRadio: ThemeType,
    val radioItems: List<RadioItem> = listOf(
        RadioItem(ThemeType.SYSTEM, "System"),
        RadioItem(ThemeType.LIGHT, "Light"),
        RadioItem(ThemeType.DARK, "Dark"),
    ),
)

data class RadioItem(val value: ThemeType, val title: String)