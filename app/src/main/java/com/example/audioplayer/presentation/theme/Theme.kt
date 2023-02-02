package com.example.audioplayer.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val DarkColorPalette = darkColors(
    primary = Blue,
    primaryVariant = CyanAlabaster,
    secondary = DarkSlateGray,
    secondaryVariant = Color.Black,
    error = Blue,
    surface = Color.White,
    background = Color.White,
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


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = colors.primary.toArgb()
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}