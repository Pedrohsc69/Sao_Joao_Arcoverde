package com.example.sao_joao_em_arcoverde.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.data.model.MapPointType
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository
import android.Manifest
import com.example.sao_joao_em_arcoverde.location.UserLocationResult
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.sao_joao_em_arcoverde.location.LocationController
import com.example.sao_joao_em_arcoverde.location.UserLocation
import kotlinx.coroutines.launch

private data class MapUiState(
    val isLoading: Boolean = true,
    val mapPoints: List<MapPoint> = emptyList(),
    val selectedType: MapPointType? = null,
    val selectedPoint: MapPoint? = null,
    val userLocation: UserLocation? = null,
    val shouldCenterOnUser: Boolean = false,
    val locationMessage: String? = null,
    val errorMessage: String? = null
)

@Composable
fun MapRoute(
    repository: FestivalRepository,
    onHomeClick: () -> Unit,
    onScheduleClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val uiState = remember {
        mutableStateOf(MapUiState())
    }

    val locationController = remember {
        LocationController(context.applicationContext)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            coroutineScope.launch {
                when (val result = locationController.getCurrentLocation()) {
                    is UserLocationResult.Success -> {
                        uiState.value = uiState.value.copy(
                            userLocation = result.location,
                            shouldCenterOnUser = true,
                            locationMessage = result.location.accuracyMeters?.let {
                                "Localização precisa: aproximadamente ${it.toInt()} metros."
                            }
                        )
                    }

                    is UserLocationResult.LowAccuracy -> {
                        uiState.value = uiState.value.copy(
                            userLocation = result.location,
                            shouldCenterOnUser = true,
                            locationMessage = result.message
                        )
                    }

                    UserLocationResult.PermissionDenied -> {
                        uiState.value = uiState.value.copy(
                            locationMessage = "Permissão de localização negada."
                        )
                    }

                    UserLocationResult.Unavailable -> {
                        uiState.value = uiState.value.copy(
                            locationMessage = "Não foi possível obter sua localização agora. Verifique se o GPS está ativado."
                        )
                    }
                }
            }
        } else {
            uiState.value = uiState.value.copy(
                locationMessage = "Permissão de localização negada."
            )
        }
    }

    LaunchedEffect(Unit) {
        runCatching {
            repository.getAllMapPoints()
        }.onSuccess { points ->
            uiState.value = uiState.value.copy(
                isLoading = false,
                mapPoints = points
            )
        }.onFailure { throwable ->
            uiState.value = uiState.value.copy(
                isLoading = false,
                errorMessage = throwable.message ?: "Erro ao carregar pontos do mapa."
            )
        }
    }

    val visiblePoints = uiState.value.selectedType?.let { selectedType ->
        uiState.value.mapPoints.filter { it.type == selectedType }
    } ?: uiState.value.mapPoints

    MapScreen(
        mapPoints = visiblePoints,
        selectedType = uiState.value.selectedType,
        selectedPoint = uiState.value.selectedPoint,
        userLocation = uiState.value.userLocation,
        shouldCenterOnUser = uiState.value.shouldCenterOnUser,
        locationMessage = uiState.value.locationMessage,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onTypeClick = { type ->
            uiState.value = uiState.value.copy(
                selectedType = if (uiState.value.selectedType == type) null else type,
                selectedPoint = null
            )
        },
        onPointClick = { point ->
            uiState.value = uiState.value.copy(
                selectedPoint = point
            )
        },
        onDismissSelectedPoint = {
            uiState.value = uiState.value.copy(
                selectedPoint = null
            )
        },
        onLocateMeClick = {
            val fineGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val coarseGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (fineGranted || coarseGranted) {
                coroutineScope.launch {
                    when (val result = locationController.getCurrentLocation()) {
                        is UserLocationResult.Success -> {
                            uiState.value = uiState.value.copy(
                                userLocation = result.location,
                                shouldCenterOnUser = true,
                                locationMessage = result.location.accuracyMeters?.let {
                                    "Localização precisa: aproximadamente ${it.toInt()} metros."
                                }
                            )
                        }

                        is UserLocationResult.LowAccuracy -> {
                            uiState.value = uiState.value.copy(
                                userLocation = result.location,
                                shouldCenterOnUser = true,
                                locationMessage = result.message
                            )
                        }

                        UserLocationResult.PermissionDenied -> {
                            uiState.value = uiState.value.copy(
                                locationMessage = "Permissão de localização negada."
                            )
                        }

                        UserLocationResult.Unavailable -> {
                            uiState.value = uiState.value.copy(
                                locationMessage = "Não foi possível obter sua localização agora. Verifique se o GPS está ativado."
                            )
                        }
                    }
                }
            } else {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        },
        onUserLocationCentered = {
            uiState.value = uiState.value.copy(
                shouldCenterOnUser = false
            )
        },
        onHomeClick = onHomeClick,
        onScheduleClick = onScheduleClick,
        onMoreClick = onMoreClick,
        onSearchClick = onSearchClick,
        onMenuClick = onMenuClick
    )
}