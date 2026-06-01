package com.example.sao_joao_em_arcoverde.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class HomeUiState(
    val isLoading: Boolean = true,
    val todaySchedule: List<Schedule> = emptyList(),
    val errorMessage: String? = null
)

@Composable
fun HomeRoute(
    repository: FestivalRepository,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onArtistsClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val uiState = remember {
        mutableStateOf(HomeUiState())
    }

    LaunchedEffect(Unit) {
        runCatching {
            val festivalDays = repository.getFestivalDays()
            val homeDate = resolveHomeScheduleDate(festivalDays)

            repository.getTodayStagePreview(
                date = homeDate,
                limit = 3
            )
        }.onSuccess { schedule ->
            uiState.value = HomeUiState(
                isLoading = false,
                todaySchedule = schedule
            )
        }.onFailure { throwable ->
            uiState.value = HomeUiState(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar dados da Home."
            )
        }
    }

    HomeScreen(
        todaySchedule = uiState.value.todaySchedule,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onArtistsClick = onArtistsClick,
        onMoreClick = onMoreClick,
        onSearchClick = onSearchClick,
        onMenuClick = onMenuClick
    )
}

private fun resolveHomeScheduleDate(
    festivalDays: List<FestivalDay>
): String {
    val sortedDays = festivalDays.sortedBy { it.date }

    if (sortedDays.isEmpty()) {
        return DEFAULT_HOME_DATE
    }

    val today = currentDateIso()

    return sortedDays
        .firstOrNull { festivalDay ->
            festivalDay.date >= today
        }
        ?.date
        ?: sortedDays.last().date
}

private fun currentDateIso(): String {
    val formatter = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.US
    )

    return formatter.format(Date())
}

private const val DEFAULT_HOME_DATE = "2026-06-13"