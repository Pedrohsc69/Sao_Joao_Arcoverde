package com.example.sao_joao_em_arcoverde.screens.map

import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.sao_joao_em_arcoverde.ui.components.map.markerDrawableResId
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.example.sao_joao_em_arcoverde.location.UserLocation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.Hotel
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocalHospital
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.TheaterComedy
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.data.model.MapPointType
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavBar
import com.example.sao_joao_em_arcoverde.ui.components.BottomNavDestination
import com.example.sao_joao_em_arcoverde.ui.components.map.OsmdroidMapView
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
fun MapScreen(
    mapPoints: List<MapPoint>,
    selectedType: MapPointType?,
    selectedPoint: MapPoint?,
    userLocation: UserLocation?,
    shouldCenterOnUser: Boolean,
    locationMessage: String?,
    isLoading: Boolean,
    errorMessage: String?,
    onTypeClick: (MapPointType?) -> Unit,
    onPointClick: (MapPoint) -> Unit,
    onDismissSelectedPoint: () -> Unit,
    onLocateMeClick: () -> Unit,
    onUserLocationCentered: () -> Unit,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val isMapBeingTouched = remember {
        mutableStateOf(false)
    }
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
            modifier = Modifier
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = !isMapBeingTouched.value
                    )
            ) {
                MapContent(
                    mapPoints = mapPoints,
                    selectedType = selectedType,
                    userLocation = userLocation,
                    shouldCenterOnUser = shouldCenterOnUser,
                    locationMessage = locationMessage,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onTypeClick = onTypeClick,
                    onPointClick = onPointClick,
                    onLocateMeClick = onLocateMeClick,
                    onUserLocationCentered = onUserLocationCentered,
                    onMapTouchChanged = { isTouching ->
                        isMapBeingTouched.value = isTouching
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }

    if (selectedPoint != null) {
        MapPointBottomSheet(
            point = selectedPoint,
            onDismiss = onDismissSelectedPoint
        )
    }
}

@Composable
private fun MapContent(
    mapPoints: List<MapPoint>,
    selectedType: MapPointType?,
    userLocation: UserLocation?,
    shouldCenterOnUser: Boolean,
    locationMessage: String?,
    isLoading: Boolean,
    errorMessage: String?,
    onTypeClick: (MapPointType?) -> Unit,
    onPointClick: (MapPoint) -> Unit,
    onLocateMeClick: () -> Unit,
    onUserLocationCentered: () -> Unit,
    onMapTouchChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> {
            Text(
                text = "Carregando pontos do mapa...",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        errorMessage != null -> {
            Text(
                text = "Não foi possível carregar os pontos do mapa.",
                color = RedAccent,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        mapPoints.isEmpty() -> {
            Text(
                text = "Nenhum ponto encontrado para esta categoria.",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        else -> {
            EventMapCard(
                mapPoints = mapPoints,
                userLocation = userLocation,
                shouldCenterOnUser = shouldCenterOnUser,
                onUserLocationCentered = onUserLocationCentered,
                onPointClick = onPointClick,
                onMapTouchChanged = onMapTouchChanged
            )

            Spacer(modifier = Modifier.height(14.dp))

            EventLegendCard(
                selectedType = selectedType,
                onTypeClick = onTypeClick
            )

            Spacer(modifier = Modifier.height(14.dp))

            LocateMeButton(
                onClick = onLocateMeClick
            )

            locationMessage?.let { message ->
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            MapQuickAccessSection(
                mapPoints = mapPoints,
                onPointClick = onPointClick
            )
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
                fontWeight = FontWeight.Black,
                letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
            )

            Text(
                text = "MAPA",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
private fun EventMapCard(
    mapPoints: List<MapPoint>,
    userLocation: UserLocation?,
    shouldCenterOnUser: Boolean,
    onUserLocationCentered: () -> Unit,
    onPointClick: (MapPoint) -> Unit,
    onMapTouchChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
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
        OsmdroidMapView(
            mapPoints = mapPoints,
            userLocation = userLocation,
            shouldCenterOnUser = shouldCenterOnUser,
            onPointClick = onPointClick,
            onUserLocationCentered = onUserLocationCentered,
            onMapTouchChanged = onMapTouchChanged,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun EventLegendCard(
    selectedType: MapPointType?,
    onTypeClick: (MapPointType) -> Unit,
    modifier: Modifier = Modifier
) {
    val legendTypes = listOf(
        MapPointType.STAGE,
        MapPointType.FOOD,
        MapPointType.HEALTH,
        MapPointType.INFO,
        MapPointType.SECURITY,
        MapPointType.HOTEL,
        MapPointType.TOURISM,
        MapPointType.TRANSPORT
    )

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
                    contentDescription = "Limpar filtro",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                legendTypes.forEach { type ->
                    LegendChip(
                        type = type,
                        selected = selectedType == type,
                        onClick = {
                            onTypeClick(type)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LegendChip(
    type: MapPointType,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val metadata = type.metadata()

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(
                if (selected) metadata.color.copy(alpha = 0.28f)
                else SurfaceDarkVariant
            )
            .border(
                width = 1.dp,
                color = if (selected) metadata.color else BorderGold,
                shape = RoundedCornerShape(50)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = type.markerDrawableResId()),
            contentDescription = metadata.label,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = metadata.label,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LocateMeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
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
    mapPoints: List<MapPoint>,
    onPointClick: (MapPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    val quickPoints = mapPoints.take(6)

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

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            quickPoints.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { point ->
                        QuickAccessCard(
                            point = point,
                            onClick = {
                                onPointClick(point)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickAccessCard(
    point: MapPoint,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(78.dp)
            .clickable(onClick = onClick),
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
            MapPointMarkerImage(
                point = point,
                size = 34
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = point.name,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MapPointMarkerImage(
    point: MapPoint,
    size: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = point.type.markerDrawableResId()),
        contentDescription = point.name,
        contentScale = ContentScale.Fit,
        modifier = modifier.size(size.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapPointBottomSheet(
    point: MapPoint,
    onDismiss: () -> Unit
) {
    val metadata = point.type.metadata()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = SurfaceDark,
        contentColor = TextPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 28.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceDarkVariant)
                        .border(
                            width = 1.dp,
                            color = BorderGold,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MapPointMarkerImage(
                        point = point,
                        size = 42
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = point.name,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black
                    )

                    Text(
                        text = metadata.label,
                        color = metadata.color,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = point.description,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = if (point.latitude != null && point.longitude != null) {
                    "Coordenadas disponíveis para uso no mapa real."
                } else {
                    "Coordenadas ainda não cadastradas. Este ponto será posicionado com precisão na etapa do OSMDroid."
                },
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private data class MapPointTypeMetadata(
    val label: String,
    val color: Color,
    val icon: ImageVector
)

private fun MapPointType.metadata(): MapPointTypeMetadata {
    return when (this) {
        MapPointType.STAGE -> MapPointTypeMetadata(
            label = "Palcos/Polos",
            color = RedAccent,
            icon = Icons.Rounded.TheaterComedy
        )

        MapPointType.FOOD -> MapPointTypeMetadata(
            label = "Alimentação",
            color = GoldPrimary,
            icon = Icons.Rounded.Restaurant
        )

        MapPointType.HEALTH -> MapPointTypeMetadata(
            label = "Saúde",
            color = GreenAccent,
            icon = Icons.Rounded.LocalHospital
        )

        MapPointType.INFO -> MapPointTypeMetadata(
            label = "Informações",
            color = BlueAccent,
            icon = Icons.Rounded.Info
        )

        MapPointType.SECURITY -> MapPointTypeMetadata(
            label = "Segurança",
            color = RedAccent,
            icon = Icons.Rounded.Security
        )

        MapPointType.HOTEL -> MapPointTypeMetadata(
            label = "Hotéis",
            color = BlueAccent,
            icon = Icons.Rounded.Hotel
        )

        MapPointType.TOURISM -> MapPointTypeMetadata(
            label = "Turismo",
            color = GreenAccent,
            icon = Icons.Rounded.TravelExplore
        )

        MapPointType.TRANSPORT -> MapPointTypeMetadata(
            label = "Transporte",
            color = GoldPrimary,
            icon = Icons.Rounded.DirectionsBus
        )

        MapPointType.OTHER -> MapPointTypeMetadata(
            label = "Outros",
            color = TextSecondary,
            icon = Icons.Rounded.Place
        )
    }
}