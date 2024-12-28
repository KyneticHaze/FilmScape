package com.furkanhrmnc.filmscape.ui.screen.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class LanguagePreferences(private val context: Context) {
    companion object {
        private const val DATASTORE_NAME = "language_prefs"
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
    }

    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

    val languageStream: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: Locale.getDefault().language
        }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
}