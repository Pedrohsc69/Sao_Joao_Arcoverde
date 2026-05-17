package com.example.sao_joao_em_arcoverde.screens.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocalHospital
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

private data class MapPoint(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val offsetX: Int,
    val offsetY: Int
)

@Composable
fun MapScreen(
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
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
                selectedDestination = BottomNavDestination.Map,
                onHomeClick = onHomeClick,
                onScheduleClick = onScheduleClick,
                onMapClick = {},
                onMoreClick = onMoreClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(innerPadding)
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                )
                .padding(horizontal = 16.dp)
        ) {
            MapHeader(
                onMenuClick = onMenuClick,
                onSearchClick = onSearchClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            EventMapCard()

            Spacer(modifier = Modifier.height(14.dp))

            EventLegendCard()

            Spacer(modifier = Modifier.height(14.dp))

            LocateMeButton()

            Spacer(modifier = Modifier.height(18.dp))

            MapQuickAccessSection()
        }
    }
}

@Composable
private fun MapHeader(
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
                text = "MAPA",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
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
private fun EventMapCard(
    modifier: Modifier = Modifier
) {
    val points = listOf(
        MapPoint(
            title = "Palco Principal",
            icon = Icons.Rounded.TheaterComedy,
            color = RedAccent,
            offsetX = 142,
            offsetY = 54
        ),
        MapPoint(
            title = "Informações",
            icon = Icons.Rounded.Info,
            color = BlueAccent,
            offsetX = 54,
            offsetY = 116
        ),
        MapPoint(
            title = "Alimentação",
            icon = Icons.Rounded.Restaurant,
            color = GoldPrimary,
            offsetX = 236,
            offsetY = 112
        ),
        MapPoint(
            title = "Saúde",
            icon = Icons.Rounded.LocalHospital,
            color = GreenAccent,
            offsetX = 226,
            offsetY = 36
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .border(
                width = 1.dp,
                color = BorderGold,
                shape = RoundedCornerShape(22.dp)
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDark
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101C26))
        ) {
            MapGrid()

            points.forEach { point ->
                MapMarker(
                    point = point,
                    modifier = Modifier.offset(
                        x = point.offsetX.dp,
                        y = point.offsetY.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun MapGrid(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val gridColor = Color.White.copy(alpha = 0.05f)
        val step = 32.dp.toPx()

        var x = 0f
        while (x < size.width) {
            drawLine(
                color = gridColor,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1f
            )
            x += step
        }

        var y = 0f
        while (y < size.height) {
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )
            y += step
        }
    }
}

@Composable
private fun MapMarker(
    point: MapPoint,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(88.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(point.color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = point.icon,
                contentDescription = point.title,
                tint = BackgroundDark,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(BackgroundDark.copy(alpha = 0.78f))
                .padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
            Text(
                text = point.title,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun EventLegendCard(
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
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Legenda do Evento",
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Fechar legenda",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LegendItem(
                    color = RedAccent,
                    label = "Palco Principal"
                )

                LegendItem(
                    color = GoldPrimary,
                    label = "Alimentação"
                )

                LegendItem(
                    color = BlueAccent,
                    label = "Informações"
                )

                LegendItem(
                    color = GreenAccent,
                    label = "Posto Médico"
                )
            }
        }
    }
}

@Composable
private fun LegendItem(
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = label,
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LocateMeButton(
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            // Etapa futura: solicitar permissão de localização e centralizar no mapa real.
        },
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GoldPrimary,
            contentColor = BackgroundDark
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.MyLocation,
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "LOCALIZAR-ME AGORA",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun MapQuickAccessSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Pontos do Evento",
            color = TextPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickAccessCard(
                title = "Palco Principal",
                icon = Icons.Rounded.TheaterComedy,
                color = RedAccent,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Saúde",
                icon = Icons.Rounded.LocalHospital,
                color = GreenAccent,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickAccessCard(
                title = "Informações",
                icon = Icons.Rounded.Info,
                color = BlueAccent,
                modifier = Modifier.weight(1f)
            )

            QuickAccessCard(
                title = "Alimentação",
                icon = Icons.Rounded.Restaurant,
                color = GoldPrimary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuickAccessCard(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(76.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDarkVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black
            )
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

