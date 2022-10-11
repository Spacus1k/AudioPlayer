package com.example.audioplayer.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = CyanBlue,
    primaryVariant = CyanAlabaster,
    onPrimary = Color.White,
    secondary = CyanAlabaster,
    onSecondary = Color.Black,
    error = Blue,
    surface = CyanAlabaster
)

private val LightColorPalette = lightColors(
    primary = Blue,
    primaryVariant = CyanAlabaster,
    secondary = DarkSlateGray,
    secondaryVariant = Color.Black,
    error = Blue,
    surface = Color.White,
    background = Color.White,
)

@Composable
fun AudioPlayerTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors =
        //if (darkTheme) {
        //DarkColorPalette
    //} else {
        LightColorPalette
    //}

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}