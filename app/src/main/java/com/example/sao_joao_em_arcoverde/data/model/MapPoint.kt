package com.example.sao_joao_em_arcoverde.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MapPoint(
    val id: String,
    val name: String,
    val type: MapPointType,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Serializable
enum class MapPointType {
    STAGE,
    FOOD,
    HEALTH,
    INFO,
    SECURITY,
    PARKING,
    OTHER
}