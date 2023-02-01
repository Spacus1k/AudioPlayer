package com.example.audioplayer.presentation.ui.components.controller

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomMediaPlayerController(
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit
) {
    Row {
        PlayerButtonItem(
            isAudioPlaying = isAudioPlaying,
            onClick = { onStart() },
            buttonSize = 25,
            transparencyBackground = 0f
        )

        Spacer(modifier = Modifier.size(8.dp))

        NextButtonItem(
            onNext = { onNext() },
            buttonSize = 25,
            transparencyBackground = 0f
        )
    }
}

@Composable
@Preview
fun BottomMediaPlayerControllerPreview() {
    BottomMediaPlayerController(isAudioPlaying = true, {}, {})
}

@Composable
@Preview
fun BottomMediaPlayerControllerPausePreview() {
    BottomMediaPlayerController(isAudioPlaying = false, {}, {})
}