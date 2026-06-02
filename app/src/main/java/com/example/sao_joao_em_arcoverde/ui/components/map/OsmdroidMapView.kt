package com.example.sao_joao_em_arcoverde.ui.components.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
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
import androidx.core.content.ContextCompat
import android.view.MotionEvent

@Composable
fun OsmdroidMapView(
    mapPoints: List<MapPoint>,
    userLocation: UserLocation?,
    shouldCenterOnUser: Boolean,
    onUserLocationCentered: () -> Unit,
    onPointClick: (MapPoint) -> Unit,
    onMapTouchChanged: (Boolean) -> Unit,
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

    val hasFittedMapPoints = remember {
        mutableStateOf(false)
    }

    val mapPointsKey = mapPoints.joinToString(separator = "|") { point ->
        "${point.id}:${point.latitude}:${point.longitude}"
    }

    LaunchedEffect(mapPointsKey) {
        hasFittedMapPoints.value = false
    }

    DisposableEffect(Unit) {
        mapView.onResume()

        onDispose {
            mapView.onPause()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                minZoomLevel = 12.0
                maxZoomLevel = 20.0

                setOnTouchListener { view, event ->
                    when (event.actionMasked) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE -> {
                            onMapTouchChanged(true)
                            view.parent?.requestDisallowInterceptTouchEvent(true)
                        }

                        MotionEvent.ACTION_UP,
                        MotionEvent.ACTION_CANCEL -> {
                            onMapTouchChanged(false)
                            view.parent?.requestDisallowInterceptTouchEvent(false)
                        }
                    }

                    false
                }
            }
        },
        update = { map ->
            val pointsWithCoordinates = mapPoints.filter { point ->
                point.latitude != null && point.longitude != null
            }

            map.overlays.clear()

            pointsWithCoordinates.forEach { point ->
                val marker = Marker(map).apply {
                    position = GeoPoint(point.latitude!!, point.longitude!!)
                    title = point.name
                    snippet = point.description
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    setIcon(
                        ContextCompat.getDrawable(
                            context,
                            point.type.markerDrawableResId()
                        )
                    )

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
            }

            if (shouldCenterOnUser && userLocation != null) {
                val userGeoPoint = GeoPoint(
                    userLocation.latitude,
                    userLocation.longitude
                )

                map.controller.setZoom(18.0)
                map.controller.animateTo(userGeoPoint)

                onUserLocationCentered()
            } else if (!hasFittedMapPoints.value) {
                fitMapToPoints(
                    map = map,
                    pointsWithCoordinates = pointsWithCoordinates
                )

                hasFittedMapPoints.value = true
            }

            map.invalidate()
        }
    )
}

private fun fitMapToPoints(
    map: MapView,
    pointsWithCoordinates: List<MapPoint>
) {
    when {
        pointsWithCoordinates.isEmpty() -> {
            map.controller.setZoom(15.0)
            map.controller.setCenter(
                GeoPoint(-8.4199, -37.0532)
            )
        }

        pointsWithCoordinates.size == 1 -> {
            val onlyPoint = pointsWithCoordinates.first()

            map.controller.setZoom(17.0)
            map.controller.setCenter(
                GeoPoint(
                    onlyPoint.latitude!!,
                    onlyPoint.longitude!!
                )
            )
        }

        else -> {
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
    }
}

private fun createMapView(context: Context): MapView {
    Configuration.getInstance().userAgentValue = context.packageName

    return MapView(context).apply {
        setTileSource(TileSourceFactory.MAPNIK)
        setMultiTouchControls(true)
        minZoomLevel = 13.0
        maxZoomLevel = 20.0
        controller.setZoom(16.0)
        controller.setCenter(
            GeoPoint(-8.4199, -37.0532)
        )
    }
}