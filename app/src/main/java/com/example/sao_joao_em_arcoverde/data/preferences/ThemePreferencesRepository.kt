package com.example.sao_joao_em_arcoverde.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themePreferencesDataStore by preferencesDataStore(
    name = "theme_preferences"
)

class ThemePreferencesRepository(
    private val context: Context
) {
    private val themeModeKey = stringPreferencesKey("theme_mode")

    val themeModeFlow: Flow<ThemeMode> = context.themePreferencesDataStore.data
        .map { preferences ->
            ThemeMode.fromStorageValue(
                preferences[themeModeKey]
            )
        }

    suspend fun updateThemeMode(themeMode: ThemeMode) {
        context.themePreferencesDataStore.edit { preferences ->
            preferences[themeModeKey] = themeMode.storageValue
        }
    }
}