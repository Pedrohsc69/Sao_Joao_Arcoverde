package com.example.sao_joao_em_arcoverde.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.appPreferencesDataStore by preferencesDataStore(
    name = "app_preferences"
)

class AppPreferencesRepository(
    private val context: Context
) {
    private val hasSeenWelcomeKey = booleanPreferencesKey(
        name = "has_seen_welcome"
    )

    val hasSeenWelcomeFlow: Flow<Boolean> =
        context.appPreferencesDataStore.data.map { preferences ->
            preferences[hasSeenWelcomeKey] ?: false
        }

    suspend fun setHasSeenWelcome(hasSeen: Boolean) {
        context.appPreferencesDataStore.edit { preferences ->
            preferences[hasSeenWelcomeKey] = hasSeen
        }
    }
}