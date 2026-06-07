package com.example.sao_joao_em_arcoverde.screens.welcome

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.ui.theme.LocalAppColors

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = appColors.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(appColors.background)
                .padding(innerPadding)
        ) {
            PremiumWelcomeBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = WindowInsets.statusBars.asPaddingValues()
                            .calculateTopPadding()
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(top = 18.dp, bottom = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DoubleFlagBanner()

                Spacer(modifier = Modifier.height(22.dp))

                PremiumHeroCard()

                Spacer(modifier = Modifier.height(22.dp))

                WelcomeTitleBlock()

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Seu guia completo para curtir a maior festa do sertão pernambucano. Programação, mapa, artistas e muito mais — tudo na palma da sua mão.",
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                )

                Spacer(modifier = Modifier.height(22.dp))

                WelcomeFeatureGrid()

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = onStartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = appColors.primary,
                        contentColor = appColors.background
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "INICIAR EXPERIÊNCIA",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Arcoverde • Pernambuco",
                    color = appColors.textSecondary.copy(alpha = 0.82f),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun PremiumWelcomeBackground() {
    val appColors = LocalAppColors.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.TopStart)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            appColors.primary.copy(alpha = 0.20f),
                            Color.Transparent
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.BottomEnd)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            appColors.red.copy(alpha = 0.16f),
                            Color.Transparent
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.Center)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            appColors.blue.copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    )
                )
        )

        DecorativeStars()
    }
}

@Composable
private fun DecorativeStars() {
    val appColors = LocalAppColors.current

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val stars = listOf(
            Pair(size.width * 0.14f, size.height * 0.20f),
            Pair(size.width * 0.82f, size.height * 0.17f),
            Pair(size.width * 0.88f, size.height * 0.40f),
            Pair(size.width * 0.12f, size.height * 0.55f),
            Pair(size.width * 0.76f, size.height * 0.72f)
        )

        stars.forEachIndexed { index, position ->
            drawCircle(
                color = when (index % 3) {
                    0 -> appColors.primary.copy(alpha = 0.45f)
                    1 -> appColors.red.copy(alpha = 0.30f)
                    else -> appColors.green.copy(alpha = 0.30f)
                },
                radius = 3.6f,
                center = androidx.compose.ui.geometry.Offset(
                    position.first,
                    position.second
                )
            )
        }
    }
}

@Composable
private fun DoubleFlagBanner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlagBanner(
            amount = 11,
            flagWidth = 15,
            flagHeight = 24
        )

        Spacer(modifier = Modifier.height(6.dp))

        FlagBanner(
            amount = 10,
            flagWidth = 13,
            flagHeight = 20,
            modifier = Modifier.padding(horizontal = 18.dp)
        )
    }
}

@Composable
private fun FlagBanner(
    amount: Int,
    flagWidth: Int,
    flagHeight: Int,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        Color(0xFFF23030),
        Color(0xFFF2B705),
        Color(0xFF3BA66A),
        Color(0xFF0688BF),
        Color(0xFFD94A8C),
        Color(0xFFD98A07)
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(amount) { index ->
            JuneFlag(
                color = colors[index % colors.size],
                width = flagWidth,
                height = flagHeight
            )

            if (index != amount - 1) {
                Spacer(modifier = Modifier.width(7.dp))
            }
        }
    }
}

@Composable
private fun JuneFlag(
    color: Color,
    width: Int,
    height: Int
) {
    val flagShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height)
        lineTo(size.width / 2f, size.height * 0.68f)
        lineTo(0f, size.height)
        close()
    }

    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .clip(flagShape)
            .background(color)
    )
}

@Composable
private fun PremiumHeroCard(
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = appColors.border
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            appColors.surfaceVariant.copy(alpha = 0.85f),
                            appColors.surface
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FireBadge()

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "SÃO JOÃO EM ARCOVERDE",
                    color = appColors.primary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Programação • Mapa • Artistas • Cultura",
                    color = appColors.textSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    PremiumMiniInfo(
                        title = "Shows",
                        icon = Icons.Rounded.Mic,
                        color = appColors.blue,
                        modifier = Modifier.weight(1f)
                    )

                    PremiumMiniInfo(
                        title = "Mapa",
                        icon = Icons.Rounded.Map,
                        color = appColors.green,
                        modifier = Modifier.weight(1f)
                    )

                    PremiumMiniInfo(
                        title = "Festa",
                        icon = Icons.Rounded.Celebration,
                        color = appColors.red,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun FireBadge() {
    val appColors = LocalAppColors.current

    val infiniteTransition = rememberInfiniteTransition()
    val pulse = infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(118.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(112.dp)
                .scale(pulse.value)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            appColors.primary.copy(alpha = 0.24f),
                            appColors.red.copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    )
                )
        )

        Box(
            modifier = Modifier
                .size(94.dp)
                .clip(CircleShape)
                .background(appColors.background.copy(alpha = 0.28f))
                .border(
                    width = 2.dp,
                    color = appColors.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.LocalFireDepartment,
                contentDescription = null,
                tint = appColors.primary,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
private fun PremiumMiniInfo(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Box(
        modifier = modifier
            .height(74.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color.copy(alpha = 0.12f))
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.45f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun WelcomeTitleBlock() {
    val appColors = LocalAppColors.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bem-vindo ao",
            color = appColors.textPrimary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Maior São João\ndo Sertão",
            color = appColors.primary,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            lineHeight = MaterialTheme.typography.displaySmall.lineHeight
        )
    }
}

@Composable
private fun WelcomeFeatureGrid(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureCard(
                title = "Programação",
                subtitle = "Dias, horários e atrações",
                icon = Icons.Rounded.MusicNote,
                color = LocalAppColors.current.blue,
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Mapa",
                subtitle = "Pontos úteis da festa",
                icon = Icons.Rounded.Map,
                color = LocalAppColors.current.green,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureCard(
                title = "Artistas",
                subtitle = "Fotos e detalhes",
                icon = Icons.Rounded.TheaterComedy,
                color = LocalAppColors.current.red,
                modifier = Modifier.weight(1f)
            )

            FeatureCard(
                title = "Cultura",
                subtitle = "Tradição e história local",
                icon = Icons.Rounded.AutoAwesome,
                color = LocalAppColors.current.primary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier.height(118.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = appColors.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = appColors.border
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                color = appColors.textPrimary,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                color = appColors.textSecondary,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )
        }
    }
}