package com.example.sao_joao_em_arcoverde.screens.sponsors

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Handshake
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.R
import com.example.sao_joao_em_arcoverde.data.model.Sponsor
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavBar
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavDestination
import com.example.sao_joao_em_arcoverde.ui.theme.BackgroundDark
import com.example.sao_joao_em_arcoverde.ui.theme.BlueAccent
import com.example.sao_joao_em_arcoverde.ui.theme.BorderGold
import com.example.sao_joao_em_arcoverde.ui.theme.GoldPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.GreenAccent
import com.example.sao_joao_em_arcoverde.ui.theme.RedAccent
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDark
import com.example.sao_joao_em_arcoverde.ui.theme.SurfaceDarkVariant
import com.example.sao_joao_em_arcoverde.ui.theme.TextPrimary
import com.example.sao_joao_em_arcoverde.ui.theme.TextSecondary

@Composable
fun SponsorsScreen(
    sponsors: List<Sponsor>,
    isLoading: Boolean,
    errorMessage: String?,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMapClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundDark,
        bottomBar = {
            BottomNavBar(
                selectedDestination = BottomNavDestination.More,
                onHomeClick = onHomeClick,
                onScheduleClick = onScheduleClick,
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
            SponsorsHeader(onBackClick = onBackClick)

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Realização e Apoio",
                    color = TextPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Instituições relacionadas à realização, organização e apoio ao São João de Arcoverde.",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(18.dp))

                SponsorsHeroCard()

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    isLoading -> {
                        Text(
                            text = "Carregando informações...",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    errorMessage != null -> {
                        Text(
                            text = "Não foi possível carregar realização e apoios.",
                            color = RedAccent,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    sponsors.isEmpty() -> {
                        Text(
                            text = "Nenhuma informação cadastrada.",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    else -> {
                        sponsors.forEach { sponsor ->
                            SponsorInfoCard(sponsor = sponsor)

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun SponsorsHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconButton(
            icon = Icons.Rounded.ArrowBack,
            contentDescription = "Voltar",
            onClick = onBackClick
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SÃO JOÃO EM ARCOVERDE",
                color = GoldPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "REALIZAÇÃO",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Box(modifier = Modifier.size(44.dp))
    }
}

@Composable
private fun SponsorsHeroCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDark
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(SurfaceDarkVariant)
                        .border(
                            width = 1.dp,
                            color = GoldPrimary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.prefeitura),
                        contentDescription = "Prefeitura Municipal de Arcoverde",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "São João de Arcoverde",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Evento público realizado com atuação institucional do município.",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Até o momento, não foi localizada uma lista oficial de patrocinadores privados. Por isso, esta seção apresenta realização e apoios institucionais confirmáveis.",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SponsorInfoCard(
    sponsor: Sponsor,
    modifier: Modifier = Modifier
) {
    val metadata = sponsorMetadata(sponsor.category)
    val logoResId = sponsorLogoResId(sponsor.id)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(22.dp)
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDarkVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SponsorLogoBox(
                    logoResId = logoResId,
                    fallbackIcon = metadata.icon,
                    fallbackColor = metadata.color,
                    contentDescription = sponsor.name
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = sponsor.name,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = sponsor.category,
                        color = metadata.color,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            sponsor.description?.let { description ->
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun SponsorLogoBox(
    @DrawableRes logoResId: Int?,
    fallbackIcon: ImageVector,
    fallbackColor: Color,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(58.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceDark)
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        if (logoResId != null) {
            Image(
                painter = painterResource(id = logoResId),
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )
        } else {
            Icon(
                imageVector = fallbackIcon,
                contentDescription = contentDescription,
                tint = fallbackColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@DrawableRes
private fun sponsorLogoResId(sponsorId: String): Int? {
    return when (sponsorId) {
        "prefeitura_arcoverde" -> R.drawable.prefeitura
        "secretaria_cultura" -> R.drawable.sec_cultura
        "secretaria_turismo_eventos" -> R.drawable.sec_turismo
        "secretaria_desenvolvimento_economico" -> R.drawable.sec_desenvolvimento
        else -> null
    }
}

private data class SponsorMetadata(
    val icon: ImageVector,
    val color: Color
)

private fun sponsorMetadata(category: String): SponsorMetadata {
    return when {
        category.contains("Realização", ignoreCase = true) -> SponsorMetadata(
            icon = Icons.Rounded.Handshake,
            color = GoldPrimary
        )

        category.contains("cultural", ignoreCase = true) -> SponsorMetadata(
            icon = Icons.Rounded.Star,
            color = GreenAccent
        )

        category.contains("econômico", ignoreCase = true) -> SponsorMetadata(
            icon = Icons.Rounded.Storefront,
            color = BlueAccent
        )

        else -> SponsorMetadata(
            icon = Icons.Rounded.Handshake,
            color = GoldPrimary
        )
    }
}

@Composable
private fun CircleIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
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