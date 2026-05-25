package com.example.sao_joao_em_arcoverde.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Sponsor(
    val id: String,
    val name: String,
    val category: String,
    val description: String? = null,
    val logoUrl: String? = null,
    val websiteUrl: String? = null
)