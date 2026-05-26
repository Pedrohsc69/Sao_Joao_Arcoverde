package com.example.sao_joao_em_arcoverde.data.mapper

import com.example.sao_joao_em_arcoverde.data.local.entity.MapPointEntity
import com.example.sao_joao_em_arcoverde.data.model.MapPoint
import com.example.sao_joao_em_arcoverde.data.model.MapPointType

fun MapPointEntity.toModel(): MapPoint {
    return MapPoint(
        id = id,
        name = name,
        type = type.toMapPointType(),
        description = description,
        latitude = latitude,
        longitude = longitude
    )
}

fun MapPoint.toEntity(): MapPointEntity {
    return MapPointEntity(
        id = id,
        name = name,
        type = type.name,
        description = description,
        latitude = latitude,
        longitude = longitude
    )
}

private fun String.toMapPointType(): MapPointType {
    return runCatching {
        MapPointType.valueOf(this)
    }.getOrDefault(MapPointType.OTHER)
}