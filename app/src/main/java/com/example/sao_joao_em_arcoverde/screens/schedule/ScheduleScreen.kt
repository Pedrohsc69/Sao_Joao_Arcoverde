package com.example.sao_joao_em_arcoverde.screens.schedule

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.rounded.MusicNote
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavBar
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavDestination
import com.example.sao_joao_em_arcoverde.ui.components.artists.artistImageResId
import com.example.sao_joao_em_arcoverde.ui.theme.LocalAppColors

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
    val appColors = LocalAppColors.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = appColors.background,
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
                .background(appColors.background)
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
    val appColors = LocalAppColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        when {
            isLoading -> {
                Text(
                    text = "Carregando programação...",
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            errorMessage != null -> {
                Text(
                    text = "Não foi possível carregar a programação.",
                    color = appColors.red,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            scheduleItems.isEmpty() -> {
                Text(
                    text = "Nenhuma atração encontrada para este dia.",
                    color = appColors.textSecondary,
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

                Spacer(modifier = Modifier.height(28.dp))
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
    val appColors = LocalAppColors.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SÃO JOÃO EM ARCOVERDE",
                color = appColors.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
            )

            Text(
                text = "PROGRAMAÇÃO",
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }
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
    val appColors = LocalAppColors.current
    val selectedContentColor = Color(0xFF201A10)

    val backgroundColor = if (selected) appColors.primary else appColors.surface
    val contentColor = if (selected) selectedContentColor else appColors.textPrimary
    val borderColor = if (selected) appColors.primary else appColors.border

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
    val appColors = LocalAppColors.current

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
                    .background(appColors.primary, RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                color = appColors.primary,
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
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScheduleArtistImage(
                item = item
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.time,
                        color = appColors.primary,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.width(54.dp)
                    )

                    Text(
                        text = item.artistName,
                        color = appColors.textPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Rounded.BookmarkBorder,
                        contentDescription = "Salvar atração",
                        tint = appColors.red,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenreBadge(
                        text = item.genre,
                        backgroundColor = appColors.green.copy(alpha = 0.18f),
                        contentColor = appColors.green
                    )

                    if (item.isHeadliner) {
                        Spacer(modifier = Modifier.width(8.dp))

                        GenreBadge(
                            text = "Headliner",
                            backgroundColor = appColors.red.copy(alpha = 0.18f),
                            contentColor = appColors.red
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleArtistImage(
    item: Schedule,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current
    val context = LocalContext.current
    val imageResId = context.artistImageResId(item.artistId)

    Box(
        modifier = modifier
            .size(width = 76.dp, height = 86.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(appColors.surfaceVariant)
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (imageResId != null) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = item.artistName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Rounded.MusicNote,
                contentDescription = item.artistName,
                tint = appColors.primary.copy(alpha = 0.65f),
                modifier = Modifier.size(34.dp)
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
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HighlightCard(
    scheduleItems: List<Schedule>,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    val headliner = scheduleItems.lastOrNull { it.isHeadliner }
        ?: scheduleItems.lastOrNull()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surfaceVariant
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
                tint = appColors.primary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "DESTAQUE",
                    color = appColors.red,
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
                    color = appColors.textPrimary,
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
    val appColors = LocalAppColors.current

    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(appColors.surface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = appColors.textPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}