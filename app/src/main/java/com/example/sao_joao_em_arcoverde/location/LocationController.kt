package com.example.sao_joao_em_arcoverde.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

data class UserLocation(
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float? = null
)

class LocationController(
    private val context: Context
) {
    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun hasLocationPermission(): Boolean {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationGranted || coarseLocationGranted
    }

    fun hasFineLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): UserLocationResult {
        if (!hasLocationPermission()) {
            return UserLocationResult.PermissionDenied
        }

        val request = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMaxUpdateAgeMillis(0)
            .build()

        val currentLocation = runCatching {
            fusedLocationClient.getCurrentLocation(
                request,
                null
            ).await()
        }.getOrNull()

        val location = currentLocation ?: runCatching {
            fusedLocationClient.lastLocation.await()
        }.getOrNull()

        if (location == null) {
            return UserLocationResult.Unavailable
        }

        val accuracy = if (location.hasAccuracy()) {
            location.accuracy
        } else {
            null
        }

        val userLocation = UserLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            accuracyMeters = accuracy
        )

        if (!hasFineLocationPermission()) {
            return UserLocationResult.LowAccuracy(
                location = userLocation,
                message = "O app está usando localização aproximada. Ative a localização precisa nas permissões para melhorar a precisão."
            )
        }

        if (accuracy != null && accuracy > 100f) {
            return UserLocationResult.LowAccuracy(
                location = userLocation,
                message = "Localização encontrada, mas com baixa precisão: aproximadamente ${accuracy.toInt()} metros."
            )
        }

        return UserLocationResult.Success(userLocation)
    }
}

sealed class UserLocationResult {
    data class Success(
        val location: UserLocation
    ) : UserLocationResult()

    data class LowAccuracy(
        val location: UserLocation,
        val message: String
    ) : UserLocationResult()

    data object PermissionDenied : UserLocationResult()

    data object Unavailable : UserLocationResult()
}