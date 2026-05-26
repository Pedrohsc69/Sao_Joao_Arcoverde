package com.example.sao_joao_em_arcoverde.screens.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository

private data class ScheduleUiState(
    val isLoading: Boolean = true,
    val festivalDays: List<FestivalDay> = emptyList(),
    val selectedDate: String? = null,
    val scheduleItems: List<Schedule> = emptyList(),
    val errorMessage: String? = null
)

@Composable
fun ScheduleRoute(
    repository: FestivalRepository,
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val uiState = remember {
        mutableStateOf(ScheduleUiState())
    }

    fun loadScheduleByDate(date: String) {
        uiState.value = uiState.value.copy(
            isLoading = true,
            selectedDate = date,
            errorMessage = null
        )
    }

    LaunchedEffect(Unit) {
        runCatching {
            val days = repository.getFestivalDays()
            val initialDate = days.firstOrNull()?.date

            val schedule = if (initialDate != null) {
                repository.getScheduleByDate(initialDate)
            } else {
                emptyList()
            }

            ScheduleUiState(
                isLoading = false,
                festivalDays = days,
                selectedDate = initialDate,
                scheduleItems = schedule
            )
        }.onSuccess { state ->
            uiState.value = state
        }.onFailure { throwable ->
            uiState.value = ScheduleUiState(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar programação."
            )
        }
    }

    LaunchedEffect(uiState.value.selectedDate) {
        val selectedDate = uiState.value.selectedDate ?: return@LaunchedEffect

        if (uiState.value.festivalDays.isEmpty()) {
            return@LaunchedEffect
        }

        runCatching {
            repository.getScheduleByDate(selectedDate)
        }.onSuccess { schedule ->
            uiState.value = uiState.value.copy(
                isLoading = false,
                scheduleItems = schedule,
                errorMessage = null
            )
        }.onFailure { throwable ->
            uiState.value = uiState.value.copy(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar programação."
            )
        }
    }

    ScheduleScreen(
        festivalDays = uiState.value.festivalDays,
        selectedDate = uiState.value.selectedDate,
        scheduleItems = uiState.value.scheduleItems,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onDayClick = { date ->
            loadScheduleByDate(date)
        },
        onHomeClick = onHomeClick,
        onMapClick = onMapClick,
        onMoreClick = onMoreClick,
        onSearchClick = onSearchClick,
        onMenuClick = onMenuClick
    )
}