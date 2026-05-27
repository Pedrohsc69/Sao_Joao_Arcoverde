package com.example.sao_joao_em_arcoverde.data.static

import com.example.sao_joao_em_arcoverde.R
import com.example.sao_joao_em_arcoverde.data.model.FacultyInfo
import com.example.sao_joao_em_arcoverde.data.model.TeamMember

object AppInfoProvider {
    val developers = listOf(
        TeamMember(
            name = "Pedro Henrique",
            role = "Líder",
            photoResId = R.drawable.pedro
        ),
        TeamMember(
            name = "Jamille Magalhães",
            role = "QA / Testes",
            photoResId = R.drawable.jamille
        ),
        TeamMember(
            name = "Maria Beatriz",
            role = "Desenvolvedora",
            photoResId = R.drawable.beatriz
        ),
        TeamMember(
            name = "Edcarlos França",
            role = "Designer / UI/UX",
            photoResId = R.drawable.edcarlos
        )
    )

    val advisor = TeamMember(
        name = "Willams de Jesus",
        role = "Professor orientador",
        photoResId = R.drawable.willams
    )

    val faculty = FacultyInfo(
        shortName = "AESA-CESA",
        fullName = "Autarquia de Ensino Superior de Arcoverde",
        course = "ADS - Análise e Desenvolvimento de Sistemas",
        subject = "Programação para Dispositivos Móveis"
    )
}