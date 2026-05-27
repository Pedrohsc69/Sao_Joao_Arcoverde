package com.example.sao_joao_em_arcoverde.screens.artists

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.Artist
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository

private data class ArtistsUiState(
    val isLoading: Boolean = true,
    val artists: List<Artist> = emptyList(),
    val searchQuery: String = "",
    val selectedArtist: Artist? = null,
    val errorMessage: String? = null
)

@Composable
fun ArtistsRoute(
    repository: FestivalRepository,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    val uiState = remember {
        mutableStateOf(ArtistsUiState())
    }

    LaunchedEffect(Unit) {
        runCatching {
            repository.getAllArtists()
        }.onSuccess { artists ->
            uiState.value = ArtistsUiState(
                isLoading = false,
                artists = artists
            )
        }.onFailure { throwable ->
            uiState.value = ArtistsUiState(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar artistas."
            )
        }
    }

    val filteredArtists = uiState.value.artists.filter { artist ->
        val query = uiState.value.searchQuery.trim()

        query.isBlank() ||
                artist.name.contains(query, ignoreCase = true) ||
                artist.genre.contains(query, ignoreCase = true)
    }

    ArtistsScreen(
        artists = filteredArtists,
        totalArtists = uiState.value.artists.size,
        searchQuery = uiState.value.searchQuery,
        selectedArtist = uiState.value.selectedArtist,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onSearchQueryChange = { query ->
            uiState.value = uiState.value.copy(searchQuery = query)
        },
        onArtistClick = { artist ->
            uiState.value = uiState.value.copy(selectedArtist = artist)
        },
        onDismissArtist = {
            uiState.value = uiState.value.copy(selectedArtist = null)
        },
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onMoreClick = onMoreClick
    )
}