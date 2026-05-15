package com.example.sao_joao_em_arcoverde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sao_joao_em_arcoverde.navigation.AppNavigation
import com.example.sao_joao_em_arcoverde.ui.theme.Sao_Joao_Em_ArcoverdeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Sao_Joao_Em_ArcoverdeTheme {
                AppNavigation()
            }
        }
    }
}