package com.example.sao_joao_em_arcoverde.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "emergency_contacts",
    indices = [
        Index(value = ["type"])
    ]
)
data class EmergencyContactEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val phone: String,
    val description: String,
    val type: String
)