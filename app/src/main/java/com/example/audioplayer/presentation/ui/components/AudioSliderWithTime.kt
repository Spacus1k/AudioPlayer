package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.domain.utils.formatDuration

@Composable
fun AudioSliderWithTime(
    progress: Float,
    duration: Float,
    onProgressChange: (Float) -> Unit
) {

    Row {
        Column(
            Modifier
                .size(50.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formatDuration(progress * duration / 100), fontSize = 10.sp)
        }

        Column(Modifier.weight(8f)) {
            AudioSlider(progress = progress, onProgressChange = onProgressChange)
        }

        Column(
            Modifier
                .size(50.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formatDuration(duration), fontSize = 10.sp)
        }
    }
}

@Preview
@Composable
fun PreviewAudioSliderWithTime() {
    AudioSliderWithTime(1f, 1f) {}
}