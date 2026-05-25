package com.example.sao_joao_em_arcoverde.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FestivalDay(
    val id: String,
    val label: String,
    val date: String,
    val dayNumber: String,
    val month: String
)