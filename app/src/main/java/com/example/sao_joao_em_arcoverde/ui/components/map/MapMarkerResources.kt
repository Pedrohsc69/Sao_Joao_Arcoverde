package com.example.sao_joao_em_arcoverde.ui.components.map

import androidx.annotation.DrawableRes
import com.example.sao_joao_em_arcoverde.R
import com.example.sao_joao_em_arcoverde.data.model.MapPointType

@DrawableRes
fun MapPointType.markerDrawableResId(): Int {
    return when (this) {
        MapPointType.STAGE -> R.drawable.map_marker_stage
        MapPointType.FOOD -> R.drawable.map_marker_food
        MapPointType.HEALTH -> R.drawable.map_marker_health
        MapPointType.INFO -> R.drawable.map_marker_info
        MapPointType.SECURITY -> R.drawable.map_marker_security
        MapPointType.HOTEL -> R.drawable.map_marker_hotel
        MapPointType.TOURISM -> R.drawable.map_marker_tourism
        MapPointType.TRANSPORT -> R.drawable.map_marker_transport
        MapPointType.OTHER -> R.drawable.map_marker_other
    }
}