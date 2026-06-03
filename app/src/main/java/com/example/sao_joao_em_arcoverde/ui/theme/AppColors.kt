package com.example.sao_joao_em_arcoverde.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val border: Color,
    val primary: Color,
    val secondary: Color,
    val red: Color,
    val green: Color,
    val blue: Color,
    val pink: Color
)

val DarkAppColors = AppColors(
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceDarkVariant,
    textPrimary = TextPrimary,
    textSecondary = TextSecondary,
    border = BorderGold,
    primary = GoldPrimary,
    secondary = AmberSecondary,
    red = RedAccent,
    green = GreenAccent,
    blue = BlueAccent,
    pink = PinkAccent
)

val LightAppColors = AppColors(
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = SurfaceLightVariant,
    textPrimary = TextPrimaryLight,
    textSecondary = TextSecondaryLight,
    border = BorderGoldLight,
    primary = AmberSecondary,
    secondary = GoldPrimary,
    red = RedAccent,
    green = GreenAccent,
    blue = BlueAccent,
    pink = PinkAccent
)

val LocalAppColors = staticCompositionLocalOf {
    DarkAppColors
}