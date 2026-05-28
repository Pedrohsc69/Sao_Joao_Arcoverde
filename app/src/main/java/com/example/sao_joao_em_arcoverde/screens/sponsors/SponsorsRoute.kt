package com.example.sao_joao_em_arcoverde.screens.sponsors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository

private data class SponsorsUiState(
    val isLoading: Boolean = true,
    val sponsors: List<Sponsor> = emptyList(),
    val errorMessage: String? = null
)

@Composable
fun SponsorsRoute(
    repository: FestivalRepository,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    val uiState = remember {
        mutableStateOf(SponsorsUiState())
    }

    LaunchedEffect(Unit) {
        runCatching {
            repository.getAllSponsors()
        }.onSuccess { sponsors ->
            uiState.value = SponsorsUiState(
                isLoading = false,
                sponsors = sponsors
            )
        }.onFailure { throwable ->
            uiState.value = SponsorsUiState(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar apoiadores."
            )
        }
    }

    SponsorsScreen(
        sponsors = uiState.value.sponsors,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onMoreClick = onMoreClick
    )
}