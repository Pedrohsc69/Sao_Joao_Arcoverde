package com.example.sao_joao_em_arcoverde.screens.about

import androidx.compose.runtime.Composable
import com.example.sao_joao_em_arcoverde.data.static.AppInfoProvider

@Composable
fun AboutAppRoute(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    AboutAppScreen(
        advisor = AppInfoProvider.advisor,
        faculty = AppInfoProvider.faculty,
        onBackClick = onBackClick,
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMapClick = onMapClick,
        onMoreClick = onMoreClick
    )
}