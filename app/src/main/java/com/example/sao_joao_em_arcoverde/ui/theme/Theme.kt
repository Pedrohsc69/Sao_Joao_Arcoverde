package com.example.sao_joao_em_arcoverde.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SaoJoaoDarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = BackgroundDark,

    secondary = AmberSecondary,
    onSecondary = BackgroundDark,

    tertiary = GreenAccent,
    onTertiary = BackgroundDark,

    background = BackgroundDark,
    onBackground = TextPrimary,

    surface = SurfaceDark,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceDarkVariant,
    onSurfaceVariant = TextSecondary,

    outline = BorderGold,

    error = ErrorDark,
    onError = BackgroundDark
)

private val SaoJoaoLightColorScheme = lightColorScheme(
    primary = AmberSecondary,
    onPrimary = TextPrimaryLight,

    secondary = GoldPrimary,
    onSecondary = TextPrimaryLight,

    tertiary = GreenAccent,
    onTertiary = TextPrimaryLight,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    surface = SurfaceLight,
    onSurface = TextPrimaryLight,

    surfaceVariant = SurfaceLightVariant,
    onSurfaceVariant = TextSecondaryLight,

    outline = BorderGoldLight,

    error = ErrorLight,
    onError = SurfaceLight
)

@Composable
fun Sao_Joao_Em_ArcoverdeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        SaoJoaoDarkColorScheme
    } else {
        SaoJoaoLightColorScheme
    }

    val appColors = if (darkTheme) {
        DarkAppColors
    } else {
        LightAppColors
    }

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
            isAppearanceLightStatusBars = !darkTheme
            isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides appColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}