package com.furkanhrmnc.filmscape.ui.screen.settings

import com.furkanhrmnc.filmscape.R
import com.furkanhrmnc.filmscape.util.ThemeType

data class SettingsUiState(
    val selectedRadio: ThemeType,
    val radioItems: List<RadioItem> = listOf(
        RadioItem(ThemeType.SYSTEM, R.string.system),
        RadioItem(ThemeType.LIGHT, R.string.light),
        RadioItem(ThemeType.DARK, R.string.dark),
    ),
)