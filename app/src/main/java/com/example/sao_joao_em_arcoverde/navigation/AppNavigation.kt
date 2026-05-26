package com.example.sao_joao_em_arcoverde.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.sao_joao_em_arcoverde.data.local.AppDatabase
import com.example.sao_joao_em_arcoverde.data.repository.FestivalRepository
import com.example.sao_joao_em_arcoverde.data.seed.FestivalSeedLoader
import com.example.sao_joao_em_arcoverde.screens.home.HomeRoute
import com.example.sao_joao_em_arcoverde.screens.map.MapRoute
import com.example.sao_joao_em_arcoverde.screens.more.MoreScreen
import com.example.sao_joao_em_arcoverde.screens.schedule.ScheduleRoute
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
    val context = LocalContext.current

    val database = remember {
        AppDatabase.getInstance(context)
    }

    val seedLoader = remember {
        FestivalSeedLoader(
            context = context,
            database = database
        )
    }

    val festivalRepository = remember {
        FestivalRepository(
            database = database,
            seedLoader = seedLoader
        )
    }

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
            HomeRoute(
                repository = festivalRepository,
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
            ScheduleRoute(
                repository = festivalRepository,
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
            MapRoute(
                repository = festivalRepository,
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