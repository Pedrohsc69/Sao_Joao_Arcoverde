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
import com.example.sao_joao_em_arcoverde.screens.more.MoreRoute
import com.example.sao_joao_em_arcoverde.screens.schedule.ScheduleRoute
import com.example.sao_joao_em_arcoverde.screens.artists.ArtistsRoute
import com.example.sao_joao_em_arcoverde.screens.about.AboutAppRoute
import com.example.sao_joao_em_arcoverde.screens.history.HistoryRoute
import com.example.sao_joao_em_arcoverde.screens.welcome.WelcomeScreen
import com.example.sao_joao_em_arcoverde.screens.sponsors.SponsorsRoute
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sao_joao_em_arcoverde.data.preferences.AppPreferencesRepository
import com.example.sao_joao_em_arcoverde.ui.theme.BackgroundDark
import com.example.sao_joao_em_arcoverde.ui.theme.GoldPrimary
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private enum class AppRoute {
    Welcome,
    Home,
    Schedule,
    Map,
    More,
    Artists,
    AboutApp,
    History,
    Sponsors
}

@Composable
fun AppNavigation() {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val appPreferencesRepository = remember {
        AppPreferencesRepository(context.applicationContext)
    }

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
        mutableStateOf<AppRoute?>(null)
    }

    LaunchedEffect(Unit) {
        val hasSeenWelcome = appPreferencesRepository.hasSeenWelcomeFlow.first()

        currentRoute.value = if (hasSeenWelcome) {
            AppRoute.Home
        } else {
            AppRoute.Welcome
        }
    }

    when (currentRoute.value) {
        null -> {
            NavigationLoadingScreen()
        }

        AppRoute.Welcome -> {
            WelcomeScreen(
                onStartClick = {
                    coroutineScope.launch {
                        appPreferencesRepository.setHasSeenWelcome(true)
                        currentRoute.value = AppRoute.Home
                    }
                }
            )
        }

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
                onArtistsClick = {
                    currentRoute.value = AppRoute.Artists
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

        AppRoute.Artists -> {
            ArtistsRoute(
                repository = festivalRepository,
                onBackClick = {
                    currentRoute.value = AppRoute.More
                },
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                }
            )
        }

        AppRoute.AboutApp -> {
            AboutAppRoute(
                onBackClick = {
                    currentRoute.value = AppRoute.More
                },
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                }
            )
        }

        AppRoute.History -> {
            HistoryRoute(
                onBackClick = {
                    currentRoute.value = AppRoute.More
                },
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                }
            )
        }

        AppRoute.Sponsors -> {
            SponsorsRoute(
                repository = festivalRepository,
                onBackClick = {
                    currentRoute.value = AppRoute.More
                },
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onMoreClick = {
                    currentRoute.value = AppRoute.More
                }
            )
        }

        AppRoute.More -> {
            MoreRoute(
                repository = festivalRepository,
                onHomeClick = {
                    currentRoute.value = AppRoute.Home
                },
                onScheduleClick = {
                    currentRoute.value = AppRoute.Schedule
                },
                onMapClick = {
                    currentRoute.value = AppRoute.Map
                },
                onArtistsClick = {
                    currentRoute.value = AppRoute.Artists
                },
                onAboutAppClick = {
                    currentRoute.value = AppRoute.AboutApp
                },
                onHistoryClick = {
                    currentRoute.value = AppRoute.History
                },
                onSponsorsClick = {
                    currentRoute.value = AppRoute.Sponsors
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

@Composable
private fun NavigationLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = GoldPrimary
        )
    }
}