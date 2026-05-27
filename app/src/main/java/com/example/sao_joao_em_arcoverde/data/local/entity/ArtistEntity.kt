package com.example.sao_joao_em_arcoverde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val genre: String,
    val description: String,
    val birthDate: String?,
    val imageUrl: String?,
    val isFeatured: Boolean
)