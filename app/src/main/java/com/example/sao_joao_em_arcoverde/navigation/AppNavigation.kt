package com.example.sao_joao_em_arcoverde.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.screens.home.HomeScreen
import com.example.sao_joao_em_arcoverde.screens.map.MapScreen
import com.example.sao_joao_em_arcoverde.screens.more.MoreScreen
import com.example.sao_joao_em_arcoverde.screens.schedule.ScheduleScreen
import com.example.sao_joao_em_arcoverde.screens.welcome.WelcomeScreen

private enum class AppRoute {
    Welcome,
    Home,
    Schedule,
    Map,
    More
}

@Composable
fun AppNavigation() {
    val currentRoute = remember {
        mutableStateOf(AppRoute.Welcome)
    }

    when (currentRoute.value) {
        AppRoute.Welcome -> {
            WelcomeScreen(
                onStartClick = {
                    currentRoute.value = AppRoute.Home
                }
            )
        }

        AppRoute.Home -> {
            HomeScreen(
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                },
                onSearchClick = {
                    // Etapa futura: implementar pesquisa
                },
                onMenuClick = {
                    // Etapa futura: menu lateral, se necessário
                }
            )
        }

        AppRoute.Schedule -> {
            ScheduleScreen(
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                },
                onSearchClick = {
                    // Etapa futura: implementar pesquisa
                },
                onMenuClick = {
                    // Etapa futura: menu lateral, se necessário
                }
            )
        }

        AppRoute.Map -> {
            MapScreen(
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                },
                onSearchClick = {
                    // Etapa futura: implementar pesquisa
                },
                onMenuClick = {
                    // Etapa futura: menu lateral, se necessário
                }
            )
        }

        AppRoute.More -> {
            MoreScreen(
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onSearchClick = {
                    // Etapa futura: implementar pesquisa
                },
                onMenuClick = {
                    // Etapa futura: menu lateral, se necessário
                }
            )
        }
    }
}