package com.example.sao_joao_em_arcoverde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.sao_joao_em_arcoverde.data.preferences.ThemeMode
import com.example.sao_joao_em_arcoverde.data.preferences.ThemePreferencesRepository
import com.example.sao_joao_em_arcoverde.navigation.AppNavigation
import com.example.sao_joao_em_arcoverde.ui.theme.Sao_Joao_Em_ArcoverdeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val themePreferencesRepository = remember {
                ThemePreferencesRepository(applicationContext)
            }

            val themeMode by themePreferencesRepository.themeModeFlow.collectAsState(
                initial = ThemeMode.SYSTEM
            )

            val systemInDarkTheme = isSystemInDarkTheme()

            val useDarkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> systemInDarkTheme
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            Sao_Joao_Em_ArcoverdeTheme(
                darkTheme = useDarkTheme
            ) {
                AppNavigation()
            }
        }
    }
}