package com.example.sao_joao_em_arcoverde.screens.more

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.EmergencyContact
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository

private data class MoreUiState(
    val isLoading: Boolean = true,
    val emergencyContacts: List<EmergencyContact> = emptyList(),
    val sponsors: List<Sponsor> = emptyList(),
    val errorMessage: String? = null
)

@Composable
fun MoreRoute(
    repository: FestivalRepository,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onArtistsClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val uiState = remember {
        mutableStateOf(MoreUiState())
    }

    LaunchedEffect(Unit) {
        runCatching {
            val contacts = repository.getAllEmergencyContacts()
            val sponsors = repository.getAllSponsors()

            MoreUiState(
                isLoading = false,
                emergencyContacts = contacts,
                sponsors = sponsors
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
        emergencyContacts = uiState.value.emergencyContacts,
        sponsors = uiState.value.sponsors,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onArtistsClick = onArtistsClick,
        onSearchClick = onSearchClick,
        onMenuClick = onMenuClick
    )
}