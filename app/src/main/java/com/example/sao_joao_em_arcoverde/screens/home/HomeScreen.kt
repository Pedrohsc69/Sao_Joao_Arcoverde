package com.example.sao_joao_em_arcoverde.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sao_joao_em_arcoverde.ui.theme.BackgroundDark
import com.example.sao_joao_em_arcoverde.ui.theme.TextPrimary

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home será implementada na próxima etapa",
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}