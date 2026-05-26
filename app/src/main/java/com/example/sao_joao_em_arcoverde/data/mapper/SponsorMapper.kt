package com.example.sao_joao_em_arcoverde.data.mapper

import com.example.sao_joao_em_arcoverde.data.local.entity.SponsorEntity
import com.example.sao_joao_em_arcoverde.data.model.Sponsor

fun SponsorEntity.toModel(): Sponsor {
    return Sponsor(
        id = id,
        name = name,
        category = category,
        description = description,
        logoUrl = logoUrl,
        websiteUrl = websiteUrl
    )
}

fun Sponsor.toEntity(): SponsorEntity {
    return SponsorEntity(
        id = id,
        name = name,
        category = category,
        description = description,
        logoUrl = logoUrl,
        websiteUrl = websiteUrl
    )
}