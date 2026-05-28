package com.example.sao_joao_em_arcoverde.screens.history

import androidx.compose.runtime.Composable
import com.example.sao_joao_em_arcoverde.data.static.AppInfoProvider

@Composable
fun HistoryRoute(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    HistoryScreen(
        historySections = AppInfoProvider.historySections,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onMoreClick = onMoreClick
    )
}