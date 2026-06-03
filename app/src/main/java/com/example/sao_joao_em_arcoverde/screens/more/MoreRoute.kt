package com.example.sao_joao_em_arcoverde.screens.more

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContact
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import com.example.sao_joao_em_arcoverde.data.preferences.NotificationPreferencesRepository
import com.example.sao_joao_em_arcoverde.data.preferences.ThemeMode
import com.example.sao_joao_em_arcoverde.data.preferences.ThemePreferencesRepository
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository
import com.example.sao_joao_em_arcoverde.data.static.AppInfoProvider
import com.example.sao_joao_em_arcoverde.notifications.FestivalNotificationScheduler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private data class MoreUiState(
    val isLoading: Boolean = true,
    val emergencyContacts: List<EmergencyContact> = emptyList(),
    val sponsors: List<Sponsor> = emptyList(),
    val errorMessage: String? = null,
    val notificationsEnabled: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)

@Composable
fun MoreRoute(
    repository: FestivalRepository,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onArtistsClick: () -> Unit,
    onAboutAppClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onSponsorsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val notificationPreferencesRepository = remember {
        NotificationPreferencesRepository(context.applicationContext)
    }

    val themePreferencesRepository = remember {
        ThemePreferencesRepository(context.applicationContext)
    }

    val notificationScheduler = remember {
        FestivalNotificationScheduler(context.applicationContext)
    }

    val uiState = remember {
        mutableStateOf(MoreUiState())
    }

    LaunchedEffect(Unit) {
        runCatching {
            val contacts = repository.getAllEmergencyContacts()
            val sponsors = repository.getAllSponsors()
            val notificationsEnabled =
                notificationPreferencesRepository.notificationsEnabledFlow.first()
            val themeMode =
                themePreferencesRepository.themeModeFlow.first()

            MoreUiState(
                isLoading = false,
                emergencyContacts = contacts,
                sponsors = sponsors,
                notificationsEnabled = notificationsEnabled,
                themeMode = themeMode
            )
        }.onSuccess { state ->
            uiState.value = state
        }.onFailure { throwable ->
            uiState.value = MoreUiState(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar mais opções."
            )
        }
    }

    MoreScreen(
        developers = AppInfoProvider.developers,
        emergencyContacts = uiState.value.emergencyContacts,
        sponsors = uiState.value.sponsors,
        notificationsEnabled = uiState.value.notificationsEnabled,
        themeMode = uiState.value.themeMode,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onNotificationsEnabledChange = { enabled ->
            uiState.value = uiState.value.copy(
                notificationsEnabled = enabled
            )

            coroutineScope.launch {
                notificationPreferencesRepository.setNotificationsEnabled(enabled)

                val schedule = repository.getAllSchedule()

                if (enabled) {
                    notificationScheduler.scheduleMainStageReminders(schedule)
                } else {
                    notificationScheduler.cancelMainStageReminders(schedule)
                }
            }
        },
        onThemeModeChange = { themeMode ->
            uiState.value = uiState.value.copy(
                themeMode = themeMode
            )

            coroutineScope.launch {
                themePreferencesRepository.updateThemeMode(themeMode)
            }
        },
        onSendTestNotification = {
            notificationScheduler.sendTestNotification()
        },
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onArtistsClick = onArtistsClick,
        onAboutAppClick = onAboutAppClick,
        onHistoryClick = onHistoryClick,
        onSponsorsClick = onSponsorsClick,
        onSearchClick = onSearchClick,
        onMenuClick = onMenuClick
    )
}