package com.furkanhrmnc.filmscape.domain.repository

import com.furkanhrmnc.filmscape.util.ThemeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    val isDynamicThemeFlow: Flow<Boolean>
    val themeTypeFLow: Flow<ThemeType>

    fun setDynamicTheme(value: Boolean, scope: CoroutineScope)
    fun setThemeType(value: ThemeType, scope: CoroutineScope)
}