package com.example.sao_joao_em_arcoverde.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "festival_days")
data class FestivalDayEntity(
    @PrimaryKey
    val id: String,
    val label: String,
    val date: String,
    val dayNumber: String,
    val month: String
)