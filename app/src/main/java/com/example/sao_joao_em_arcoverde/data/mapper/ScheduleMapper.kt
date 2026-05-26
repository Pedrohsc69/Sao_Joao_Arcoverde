package com.example.sao_joao_em_arcoverde.data.mapper

import com.example.sao_joao_em_arcoverde.data.local.entity.ScheduleEntity
import com.example.sao_joao_em_arcoverde.data.model.Schedule

fun ScheduleEntity.toModel(): Schedule {
    return Schedule(
        id = id,
        artistId = artistId,
        artistName = artistName,
        stageName = stageName,
        date = date,
        time = time,
        genre = genre,
        isHeadliner = isHeadliner,
        isBookmarked = isBookmarked
    )
}

fun Schedule.toEntity(): ScheduleEntity {
    return ScheduleEntity(
        id = id,
        artistId = artistId,
        artistName = artistName,
        stageName = stageName,
        date = date,
        time = time,
        genre = genre,
        isHeadliner = isHeadliner,
        isBookmarked = isBookmarked
    )
}