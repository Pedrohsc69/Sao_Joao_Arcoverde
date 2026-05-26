package com.example.sao_joao_em_arcoverde.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sponsors",
    indices = [
        Index(value = ["category"])
    ]
)
data class SponsorEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val category: String,
    val description: String?,
    val logoUrl: String?,
    val websiteUrl: String?
)