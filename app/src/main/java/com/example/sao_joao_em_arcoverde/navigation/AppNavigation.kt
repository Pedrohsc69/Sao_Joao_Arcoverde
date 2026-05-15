package com.example.sao_joao_em_arcoverde.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.screens.welcome.WelcomeScreen

@Composable
fun AppNavigation() {
    val hasStarted = remember {
        mutableStateOf(false)
    }

    if (hasStarted.value) {
        // Na próxima etapa, substituiremos isso pela HomeScreen real.
        HomeScreen()
    } else {
        WelcomeScreen(
            onStartClick = {
                hasStarted.value = true
            }
        )
    }
}