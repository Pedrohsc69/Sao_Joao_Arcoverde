package com.example.sao_joao_em_arcoverde.data.mapper

import com.example.sao_joao_em_arcoverde.data.local.entity.ArtistEntity
import com.example.sao_joao_em_arcoverde.data.model.Artist

fun ArtistEntity.toModel(): Artist {
    return Artist(
        id = id,
        name = name,
        genre = genre,
        description = description,
        birthDate = birthDate,
        imageUrl = imageUrl,
        isFeatured = isFeatured
    )
}

fun Artist.toEntity(): ArtistEntity {
    return ArtistEntity(
        id = id,
        name = name,
        genre = genre,
        description = description,
        birthDate = birthDate,
        imageUrl = imageUrl,
        isFeatured = isFeatured
    )
}