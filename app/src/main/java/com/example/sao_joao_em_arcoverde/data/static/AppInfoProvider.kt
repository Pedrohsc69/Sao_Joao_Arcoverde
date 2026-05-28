package com.example.sao_joao_em_arcoverde.data.static

import com.example.sao_joao_em_arcoverde.R
import com.example.sao_joao_em_arcoverde.data.model.FacultyInfo
import com.example.sao_joao_em_arcoverde.data.model.TeamMember
import com.example.sao_joao_em_arcoverde.data.model.HistorySection

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

    val historySections = listOf(
        HistorySection(
            title = "História de Arcoverde",
            body = """
            Arcoverde tem sua formação ligada ao antigo povoado de Olho d’Água, posteriormente conhecido como Olho d’Água dos Bredos. A região se desenvolveu a partir de caminhos de passagem, atividades religiosas, comércio local e ligação com Pesqueira.

            Com a chegada da estrada de ferro, o então distrito ganhou novo impulso econômico e urbano. Em 1928, Rio Branco foi elevado à categoria de cidade. Em 1943, o município passou a se chamar Arcoverde, em homenagem a Dom Joaquim Arcoverde de Albuquerque Cavalcanti, o primeiro cardeal do Brasil e da América Latina.

            Hoje, Arcoverde é reconhecida como uma das principais cidades do Sertão pernambucano, com forte presença no comércio, nos serviços, na cultura popular e nas festividades regionais.
        """.trimIndent()
        ),
        HistorySection(
            title = "A Cultura Popular no Município",
            body = """
            A identidade cultural de Arcoverde é marcada por manifestações populares, música, dança, religiosidade e tradição nordestina. A cidade é associada ao samba de coco, aos grupos culturais locais e a artistas que projetaram o nome de Arcoverde para além da região.

            Entre os elementos culturais mais lembrados estão os grupos de coco, os polos de festa, a música regional, as quadrilhas, os artistas locais e os espaços de convivência que transformam a cidade em referência cultural no Sertão.
        """.trimIndent()
        ),
        HistorySection(
            title = "História do São João de Arcoverde",
            body = """
            O São João de Arcoverde nasceu da força das tradições juninas, das apresentações populares, das quadrilhas, dos arraiais, da música regional e da participação da população nas ruas e praças da cidade.

            Com o passar dos anos, a festa cresceu e passou a reunir atrações locais, regionais e nacionais, fortalecendo a economia, o turismo e a valorização da cultura nordestina. A programação junina se tornou um dos momentos mais importantes do calendário cultural de Arcoverde.

            Além dos grandes shows, o São João da cidade preserva elementos da cultura popular, como o coco, o forró, as manifestações religiosas, as apresentações culturais e a presença dos polos festivos.
        """.trimIndent()
        ),
        HistorySection(
            title = "Importância para o Sertão Pernambucano",
            body = """
            O São João de Arcoverde movimenta moradores, visitantes, artistas, comerciantes, serviços e equipamentos públicos. A festa fortalece a imagem da cidade como porta de entrada do Sertão e como referência cultural no interior de Pernambuco.

            Mais do que um evento musical, o São João representa memória, identidade, encontro comunitário e valorização da cultura nordestina.
        """.trimIndent()
        )
    )
}