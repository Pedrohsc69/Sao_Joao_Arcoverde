package com.example.sao_joao_em_arcoverde.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.R
import com.example.sao_joao_em_arcoverde.data.model.Schedule
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavBar
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavDestination
import com.example.sao_joao_em_arcoverde.ui.components.artists.artistImageResId
import com.example.sao_joao_em_arcoverde.ui.theme.LocalAppColors
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    todaySchedule: List<Schedule>,
    isLoading: Boolean,
    errorMessage: String?,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onArtistsClick: () -> Unit,
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
                selectedDestination = BottomNavDestination.Home,
                onHomeClick = {},
                onScheduleClick = onScheduleClick,
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            HomeHeader(
                onMenuClick = onMenuClick,
                onSearchClick = onSearchClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            InstitutionalCarouselCard()

            Spacer(modifier = Modifier.height(18.dp))

            TodayOnStageSection(
                todaySchedule = todaySchedule,
                isLoading = isLoading,
                errorMessage = errorMessage
            )

            Spacer(modifier = Modifier.height(20.dp))

            ExploreFestivalSection(
                onScheduleClick = onScheduleClick,
                onMapClick = onMapClick,
                onArtistsClick = onArtistsClick
            )

            Spacer(modifier = Modifier.height(18.dp))

            TipOfDayCard()

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun HomeHeader(
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
                text = "INÍCIO",
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun TodayOnStageSection(
    todaySchedule: List<Schedule>,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SectionHeader(
            title = "Hoje no Palco",
            actionText = ""
        )

        Spacer(modifier = Modifier.height(10.dp))

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

            todaySchedule.isEmpty() -> {
                Text(
                    text = "Nenhuma atração encontrada para hoje.",
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            else -> {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    todaySchedule.forEach { schedule ->
                        ArtistTimeCard(
                            schedule = schedule
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtistTimeCard(
    schedule: Schedule,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current
    val context = LocalContext.current
    val imageResId = context.artistImageResId(schedule.artistId)

    Card(
        modifier = modifier
            .width(210.dp)
            .height(150.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = schedule.artistName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    appColors.background.copy(alpha = 0.92f)
                                )
                            )
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appColors.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MusicNote,
                        contentDescription = null,
                        tint = appColors.primary.copy(alpha = 0.5f),
                        modifier = Modifier.size(46.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = schedule.time,
                    color = appColors.primary,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = schedule.artistName,
                    color = appColors.textPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = schedule.genre,
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ExploreFestivalSection(
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onArtistsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Explore a Festa",
            color = appColors.textPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExploreCard(
                title = "Programação",
                icon = Icons.Rounded.Mic,
                backgroundColor = appColors.blue.copy(alpha = 0.18f),
                iconColor = appColors.blue,
                modifier = Modifier.weight(1f),
                onClick = onScheduleClick
            )

            ExploreCard(
                title = "Mapa",
                icon = Icons.Rounded.Map,
                backgroundColor = appColors.green.copy(alpha = 0.18f),
                iconColor = appColors.green,
                modifier = Modifier.weight(1f),
                onClick = onMapClick
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        ExploreCard(
            title = "Artistas",
            icon = Icons.Rounded.TheaterComedy,
            backgroundColor = appColors.red.copy(alpha = 0.14f),
            iconColor = appColors.red,
            modifier = Modifier.fillMaxWidth(),
            onClick = onArtistsClick
        )
    }
}

@Composable
private fun ExploreCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier
            .height(82.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun TipOfDayCard(
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Lightbulb,
                contentDescription = null,
                tint = appColors.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "DICA DO DIA",
                    color = appColors.primary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "Chegue cedo para garantir um bom lugar no Palco Principal!",
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.MusicNote,
                contentDescription = null,
                tint = appColors.primary,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = title,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )
        }

        Text(
            text = actionText,
            color = appColors.primary,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

private data class InstitutionalLogo(
    val name: String,
    val subtitle: String,
    @DrawableRes val logoResId: Int
)

@Composable
private fun InstitutionalCarouselCard(
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    val items = listOf(
        InstitutionalLogo(
            name = "Prefeitura de Arcoverde",
            subtitle = "Realização",
            logoResId = R.drawable.prefeitura
        ),
        InstitutionalLogo(
            name = "Secretaria de Cultura",
            subtitle = "Organização cultural",
            logoResId = R.drawable.sec_cultura
        ),
        InstitutionalLogo(
            name = "Secretaria de Turismo, Esportes e Eventos",
            subtitle = "Apoio institucional",
            logoResId = R.drawable.sec_turismo
        ),
        InstitutionalLogo(
            name = "Secretaria de Desenvolvimento Econômico",
            subtitle = "Apoio econômico",
            logoResId = R.drawable.sec_desenvolvimento
        )
    )

    val currentIndex = remember {
        mutableStateOf(0)
    }

    fun goToPrevious() {
        currentIndex.value = if (currentIndex.value == 0) {
            items.lastIndex
        } else {
            currentIndex.value - 1
        }
    }

    fun goToNext() {
        currentIndex.value = (currentIndex.value + 1) % items.size
    }

    LaunchedEffect(currentIndex.value) {
        delay(10_000)
        goToNext()
    }

    val currentItem = items[currentIndex.value]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(22.dp)
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InstitutionalLogoBox(
                    logoResId = currentItem.logoResId,
                    contentDescription = currentItem.name
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentItem.name,
                    color = appColors.textPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = currentItem.subtitle,
                    color = appColors.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                CarouselIndicators(
                    totalItems = items.size,
                    selectedIndex = currentIndex.value,
                    onIndicatorClick = { index ->
                        currentIndex.value = index
                    }
                )
            }

            CarouselArrowButton(
                icon = Icons.Rounded.ChevronLeft,
                contentDescription = "Logo anterior",
                onClick = {
                    goToPrevious()
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            )

            CarouselArrowButton(
                icon = Icons.Rounded.ChevronRight,
                contentDescription = "Próxima logo",
                onClick = {
                    goToNext()
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            )
        }
    }
}

@Composable
private fun CarouselArrowButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Box(
        modifier = modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(appColors.background.copy(alpha = 0.72f))
            .border(
                width = 1.dp,
                color = appColors.border.copy(alpha = 0.55f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = appColors.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun InstitutionalLogoBox(
    @DrawableRes logoResId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Box(
        modifier = modifier
            .size(width = 82.dp, height = 82.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(appColors.surfaceVariant)
            .border(
                width = 1.dp,
                color = appColors.border,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoResId),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CarouselIndicators(
    totalItems: Int,
    selectedIndex: Int,
    onIndicatorClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalItems) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(
                        width = if (index == selectedIndex) 24.dp else 8.dp,
                        height = 8.dp
                    )
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (index == selectedIndex) {
                            appColors.primary
                        } else {
                            appColors.textSecondary.copy(alpha = 0.35f)
                        }
                    )
                    .clickable {
                        onIndicatorClick(index)
                    }
            )
        }
    }
}

@Composable
private fun CircleIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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