package com.example.audioplayer.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = CyanBlue,
    primaryVariant = DarkSlateGray,
    onPrimary = CyanBlue,
    secondary = CyanAlabaster,
    onSecondary = CyanAlabaster,
    error = Blue
)

private val LightColorPalette = lightColors(
    primary = CyanBlue,
    primaryVariant = CyanAlabaster,
    onPrimary = CyanAlabaster,
    secondary = CyanAlabaster,
    onSecondary = CyanAlabaster,
    error = Blue,
    surface = CyanAlabaster


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AudioPlayerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}