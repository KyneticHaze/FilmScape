package com.furkanhrmnc.filmscape.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.furkanhrmnc.filmscape.domain.repository.StoreRepository
import com.furkanhrmnc.filmscape.util.Constants.IS_DYNAMIC_THEME
import com.furkanhrmnc.filmscape.util.Constants.THEME_TYPE
import com.furkanhrmnc.filmscape.util.ThemeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class StoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : StoreRepository {

    private companion object {
        val isDynamicTheme = booleanPreferencesKey(IS_DYNAMIC_THEME)
        val themeType = intPreferencesKey(THEME_TYPE)
    }


    override val isDynamicThemeFlow: Flow<Boolean>
        get() = dataStore.data.map { preferences -> preferences[isDynamicTheme] ?: false }
    override val themeTypeFLow: Flow<ThemeType>
        get() = dataStore.data.map { preferences -> ThemeType.entries[preferences[themeType] ?: 0] }

    override fun setDynamicTheme(value: Boolean, scope: CoroutineScope) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[isDynamicTheme] = value
            }
        }
    }

    override fun setThemeType(value: ThemeType, scope: CoroutineScope) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[themeType] = value.ordinal
            }
        }
    }
}