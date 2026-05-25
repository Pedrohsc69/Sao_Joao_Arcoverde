package com.example.sao_joao_em_arcoverde.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EmergencyContact(
    val id: String,
    val name: String,
    val phone: String,
    val description: String,
    val type: EmergencyContactType
)

@Serializable
enum class EmergencyContactType {
    MEDICAL,
    POLICE,
    FIRE_DEPARTMENT,
    EVENT_SUPPORT,
    OTHER
}