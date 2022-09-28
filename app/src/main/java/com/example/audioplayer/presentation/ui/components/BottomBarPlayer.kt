package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.model.AudioFile

@Composable
fun BottomBarPlayer(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audioFile: AudioFile,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit
) {
    Column {
        Text(text = audioFile.title)
        Text(text = audioFile.artist)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(
                valueRange = 0f..100f,
                steps = 100,
                value = progress,
                onValueChange = { onProgressChange.invoke(it) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomBarPlayer() {
    BottomBarPlayer(
        progress = 100f,
        onProgressChange = {},
        audioFile = AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        isAudioPlaying = true,
        onStart = { /*TODO*/ }) {
    }
}