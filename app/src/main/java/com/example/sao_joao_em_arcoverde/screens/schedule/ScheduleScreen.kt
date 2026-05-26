package com.example.sao_joao_em_arcoverde.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Whatshot
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavBar
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavDestination
import com.example.sao_joao_em_arcoverde.ui.theme.BackgroundDark
import com.example.sao_joao_em_arcoverde.ui.theme.BorderGold
import com.example.sao_joao_em_arcoverde.ui.theme.GoldPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.GreenAccent
import com.example.sao_joao_em_arcoverde.ui.theme.RedAccent
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDark
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDarkVariant
import com.example.sao_joao_em_arcoverde.ui.theme.TextPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.TextSecondary

@Composable
fun ScheduleScreen(
    festivalDays: List<FestivalDay>,
    selectedDate: String?,
    scheduleItems: List<Schedule>,
    isLoading: Boolean,
    errorMessage: String?,
    onDayClick: (String) -> Unit,
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundDark,
        bottomBar = {
            BottomNavBar(
                selectedDestination = BottomNavDestination.Schedule,
                onHomeClick = onHomeClick,
                onScheduleClick = {},
                onMapClick = onMapClick,
                onMoreClick = onMoreClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                )
                .padding(horizontal = 16.dp)
        ) {
            ScheduleHeader(
                onMenuClick = onMenuClick,
                onSearchClick = onSearchClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            DaySelector(
                days = festivalDays,
                selectedDate = selectedDate,
                onDayClick = onDayClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            ScheduleContent(
                scheduleItems = scheduleItems,
                isLoading = isLoading,
                errorMessage = errorMessage,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ScheduleContent(
    scheduleItems: List<Schedule>,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        when {
            isLoading -> {
                Text(
                    text = "Carregando programação...",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            errorMessage != null -> {
                Text(
                    text = "Não foi possível carregar a programação.",
                    color = RedAccent,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            scheduleItems.isEmpty() -> {
                Text(
                    text = "Nenhuma atração encontrada para este dia.",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                val scheduleByStage = scheduleItems.groupBy { it.stageName }

                scheduleByStage.forEach { (stageName, items) ->
                    StageSection(
                        title = stageName,
                        items = items
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }

                HighlightCard(
                    scheduleItems = scheduleItems
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun ScheduleHeader(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconButton(
            icon = Icons.Rounded.Menu,
            contentDescription = "Abrir menu",
            onClick = onMenuClick
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SÃO JOÃO",
                color = GoldPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "ARCOVERDE",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        CircleIconButton(
            icon = Icons.Rounded.Search,
            contentDescription = "Pesquisar",
            onClick = onSearchClick
        )
    }
}

@Composable
private fun DaySelector(
    days: List<FestivalDay>,
    selectedDate: String?,
    onDayClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        days.forEach { day ->
            DayChip(
                day = day,
                selected = day.date == selectedDate,
                onClick = {
                    onDayClick(day.date)
                }
            )
        }
    }
}

@Composable
private fun DayChip(
    day: FestivalDay,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (selected) GoldPrimary else SurfaceDark
    val contentColor = if (selected) BackgroundDark else TextPrimary
    val borderColor = if (selected) GoldPrimary else BorderGold

    Column(
        modifier = modifier
            .width(58.dp)
            .height(62.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = day.month,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black
        )

        Text(
            text = day.dayNumber,
            color = contentColor,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun StageSection(
    title: String,
    items: List<Schedule>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(28.dp)
                    .background(GoldPrimary, RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                color = GoldPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items.forEach { item ->
                ScheduleCard(item = item)
            }
        }
    }
}

@Composable
private fun ScheduleCard(
    item: Schedule,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDark
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.time,
                color = GoldPrimary,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.width(58.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.artistName,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenreBadge(
                        text = item.genre,
                        backgroundColor = GreenAccent.copy(alpha = 0.18f),
                        contentColor = GreenAccent
                    )

                    if (item.isHeadliner) {
                        Spacer(modifier = Modifier.width(6.dp))

                        GenreBadge(
                            text = "Headliner",
                            backgroundColor = RedAccent.copy(alpha = 0.18f),
                            contentColor = RedAccent
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                imageVector = Icons.Rounded.BookmarkBorder,
                contentDescription = "Salvar atração",
                tint = RedAccent,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun GenreBadge(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HighlightCard(
    scheduleItems: List<Schedule>,
    modifier: Modifier = Modifier
) {
    val headliner = scheduleItems.lastOrNull { it.isHeadliner }
        ?: scheduleItems.lastOrNull()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDarkVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Whatshot,
                contentDescription = null,
                tint = GoldPrimary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "DESTAQUE",
                    color = RedAccent,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (headliner != null) {
                        "Não perca: ${headliner.artistName} às ${headliner.time}"
                    } else {
                        "Confira a programação completa do dia."
                    },
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun CircleIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(SurfaceDark)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = TextPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}