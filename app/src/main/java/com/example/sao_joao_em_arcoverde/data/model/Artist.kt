package com.example.sao_joao_em_arcoverde.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val description: String,
    val birthDate: String? = null,
    val imageUrl: String? = null,
    val isFeatured: Boolean = false
)