package com.example.sao_joao_em_arcoverde.data.model

import androidx.annotation.DrawableRes

data class TeamMember(
    val name: String,
    val role: String,
    @DrawableRes val photoResId: Int
)

data class FacultyInfo(
    val shortName: String,
    val fullName: String,
    val course: String,
    val subject: String
)