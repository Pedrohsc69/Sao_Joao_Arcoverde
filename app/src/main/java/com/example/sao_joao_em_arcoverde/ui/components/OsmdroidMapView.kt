package com.example.sao_joao_em_arcoverde.ui.components.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.location.UserLocation
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.DirectedLocationOverlay

@Composable
fun OsmdroidMapView(
    mapPoints: List<MapPoint>,
    userLocation: UserLocation?,
    shouldCenterOnUser: Boolean,
    onUserLocationCentered: () -> Unit,
    onPointClick: (MapPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val mapView = remember {
        createMapView(context)
    }

    val userLocationOverlay = remember {
        DirectedLocationOverlay(context).apply {
            setShowAccuracy(true)
        }
    }

    DisposableEffect(Unit) {
        mapView.onResume()

        onDispose {
            mapView.onPause()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView
        },
        update = { map ->
            val pointsWithCoordinates = mapPoints.filter {
                it.latitude != null && it.longitude != null
            }

            map.overlays.clear()

            pointsWithCoordinates.forEach { point ->
                val marker = Marker(map).apply {
                    position = GeoPoint(point.latitude!!, point.longitude!!)
                    title = point.name
                    snippet = point.description
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    setOnMarkerClickListener { _, _ ->
                        onPointClick(point)
                        true
                    }
                }

                map.overlays.add(marker)
            }

            userLocation?.let { location ->
                userLocationOverlay.location = GeoPoint(
                    location.latitude,
                    location.longitude
                )
                userLocationOverlay.setAccuracy(25)
                map.overlays.add(userLocationOverlay)

                if (shouldCenterOnUser) {
                    map.controller.animateTo(
                        GeoPoint(location.latitude, location.longitude)
                    )
                    map.controller.setZoom(18.0)
                    onUserLocationCentered()
                }
            }

            if (userLocation == null || !shouldCenterOnUser) {
                if (pointsWithCoordinates.isNotEmpty()) {
                    if (pointsWithCoordinates.size == 1) {
                        val onlyPoint = pointsWithCoordinates.first()

                        map.controller.setZoom(17.0)
                        map.controller.setCenter(
                            GeoPoint(
                                onlyPoint.latitude!!,
                                onlyPoint.longitude!!
                            )
                        )
                    } else {
                        val latitudes = pointsWithCoordinates.mapNotNull { it.latitude }
                        val longitudes = pointsWithCoordinates.mapNotNull { it.longitude }

                        val boundingBox = BoundingBox(
                            latitudes.maxOrNull() ?: -8.4199,
                            longitudes.maxOrNull() ?: -37.0532,
                            latitudes.minOrNull() ?: -8.4199,
                            longitudes.minOrNull() ?: -37.0532
                        )

                        map.post {
                            map.zoomToBoundingBox(
                                boundingBox.increaseByScale(1.4f),
                                true
                            )
                        }
                    }
                } else {
                    map.controller.setZoom(15.0)
                    map.controller.setCenter(
                        GeoPoint(-8.4199, -37.0532)
                    )
                }
            }

            map.invalidate()
        }
    )
}

private fun createMapView(context: Context): MapView {
    Configuration.getInstance().userAgentValue = context.packageName

    return MapView(context).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)
        minZoomLevel = 13.0
        maxZoomLevel = 20.0
        controller.setZoom(16.0)
        controller.setCenter(GeoPoint(-8.4199, -37.0532))
    }
}