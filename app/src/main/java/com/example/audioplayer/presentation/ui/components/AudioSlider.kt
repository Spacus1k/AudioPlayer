package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun AudioSlider(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float> = 0f..100f
) {
    var localSliderValue by remember {
        mutableStateOf(progress)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isInteracting = isPressed || isDragged

    val sliderValue by derivedStateOf {
        if (isInteracting) {
            localSliderValue
        } else {
            progress
        }
    }

    Slider(
        valueRange = range,
        value = sliderValue,
        onValueChange = {
            localSliderValue = it
        },
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.primaryVariant,
            activeTrackColor = MaterialTheme.colors.primaryVariant,
            inactiveTrackColor = Color.Black.copy(alpha = 0.1f)
        ),
        interactionSource = interactionSource,
        onValueChangeFinished = {
            onProgressChange(sliderValue)
        }
    )
}