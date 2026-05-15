package com.example.sao_joao_em_arcoverde.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.screens.home.HomeScreen
import com.example.sao_joao_em_arcoverde.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val hasStarted = remember {
        mutableStateOf(false)
    }

    if (hasStarted.value) {
        HomeScreen(
            onScheduleClick = {
                // Próxima etapa: navegar para Programação
            },
            onMapClick = {
                // Etapa futura: navegar para Mapa
            },
            onMoreClick = {
                // Etapa futura: navegar para Mais Opções
            },
            onSearchClick = {
                // Etapa futura: implementar pesquisa
            },
            onMenuClick = {
                // Etapa futura: menu lateral, se o grupo decidir manter
            }
        )
    } else {
        WelcomeScreen(
            onStartClick = {
                hasStarted.value = true
            }
        )
    }
}