package com.example.sao_joao_em_arcoverde.data.mapper

import com.example.sao_joao_em_arcoverde.data.local.entity.FestivalDayEntity
import com.example.sao_joao_em_arcoverde.data.model.FestivalDay

fun FestivalDayEntity.toModel(): FestivalDay {
    return FestivalDay(
        id = id,
        label = label,
        date = date,
        dayNumber = dayNumber,
        month = month
    )
}

fun FestivalDay.toEntity(): FestivalDayEntity {
    return FestivalDayEntity(
        id = id,
        label = label,
        date = date,
        dayNumber = dayNumber,
        month = month
    )
}