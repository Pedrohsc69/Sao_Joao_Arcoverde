package com.example.sao_joao_em_arcoverde.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.data.model.MapPointType
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository

private data class MapUiState(
    val isLoading: Boolean = true,
    val mapPoints: List<MapPoint> = emptyList(),
    val selectedType: MapPointType? = null,
    val selectedPoint: MapPoint? = null,
    val errorMessage: String? = null
)

@Composable
fun MapRoute(
    repository: FestivalRepository,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val uiState = remember {
        mutableStateOf(MapUiState())
    }

    LaunchedEffect(Unit) {
        runCatching {
            repository.getAllMapPoints()
        }.onSuccess { points ->
            uiState.value = MapUiState(
                isLoading = false,
                mapPoints = points
            )
        }.onFailure { throwable ->
            uiState.value = MapUiState(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar pontos do mapa."
            )
        }
    }

    val visiblePoints = uiState.value.selectedType?.let { selectedType ->
        uiState.value.mapPoints.filter { it.type == selectedType }
    } ?: uiState.value.mapPoints

    MapScreen(
        mapPoints = visiblePoints,
        selectedType = uiState.value.selectedType,
        selectedPoint = uiState.value.selectedPoint,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onTypeClick = { type ->
            uiState.value = uiState.value.copy(
                selectedType = if (uiState.value.selectedType == type) null else type,
                selectedPoint = null
            )
        },
        onPointClick = { point ->
            uiState.value = uiState.value.copy(
                selectedPoint = point
            )
        },
        onDismissSelectedPoint = {
            uiState.value = uiState.value.copy(
                selectedPoint = null
            )
        },
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMoreClick = onMoreClick,
        onSearchClick = onSearchClick,
        onMenuClick = onMenuClick
    )
}