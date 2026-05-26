package com.example.sao_joao_em_arcoverde.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "schedule",
    indices = [
        Index(value = ["date"]),
        Index(value = ["artistId"]),
        Index(value = ["stageName"])
    ]
)
data class ScheduleEntity(
    @PrimaryKey
    val id: String,
    val artistId: String,
    val artistName: String,
    val stageName: String,
    val date: String,
    val time: String,
    val genre: String,
    val isHeadliner: Boolean,
    val isBookmarked: Boolean
)