package com.example.sao_joao_em_arcoverde.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.notificationPreferencesDataStore by preferencesDataStore(
    name = "notification_preferences"
)

class NotificationPreferencesRepository(
    private val context: Context
) {
    private val notificationsEnabledKey = booleanPreferencesKey(
        name = "notifications_enabled"
    )

    val notificationsEnabledFlow: Flow<Boolean> =
        context.notificationPreferencesDataStore.data.map { preferences ->
            preferences[notificationsEnabledKey] ?: false
        }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.notificationPreferencesDataStore.edit { preferences ->
            preferences[notificationsEnabledKey] = enabled
        }
    }
}