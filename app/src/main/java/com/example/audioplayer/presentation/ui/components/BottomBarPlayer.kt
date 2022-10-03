package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AudioInfo(audioFile = audioFile, modifier = modifier.weight(1f))

            MediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = onStart,
                onNext = onNext,
                modifier = Modifier
            )
        }
        Slider(
            valueRange = 0f..100f,
            value = progress,
            onValueChange = { onProgressChange(it) }
        )
    }
}

@Composable
fun AudioInfo(audioFile: AudioFile, modifier: Modifier) {

    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Column {
            Text(
                text = audioFile.displayName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )
            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = audioFile.artist,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Clip,
                maxLines = 1
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
        audioFile = AudioFile(
            id = 0,
            title = "Рыть",
            artist = "Face",
            "D",
            displayName = "test",
            0f
        ),
        isAudioPlaying = true,
        onStart = {},
        onNext = {}
    )
}