package com.example.sao_joao_em_arcoverde.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SaoJoaoDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    secondary = AmberSecondary,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = BackgroundDark,
    onSecondary = BackgroundDark,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun Sao_Joao_Em_ArcoverdeTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = SaoJoaoDarkColorScheme

    val view = LocalView.current

    if (!view.isInEditMode) {
        val window = (LocalContext.current as Activity).window

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}