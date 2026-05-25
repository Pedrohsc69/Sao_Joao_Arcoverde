package com.example.sao_joao_em_arcoverde.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: String,
    val artistId: String,
    val artistName: String,
    val stageName: String,
    val date: String,
    val time: String,
    val genre: String,
    val isHeadliner: Boolean = false,
    val isBookmarked: Boolean = false
)