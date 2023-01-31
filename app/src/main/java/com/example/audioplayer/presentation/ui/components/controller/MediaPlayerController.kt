package com.example.audioplayer.presentation.ui.components.controller

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.model.AudioFile

@Composable
fun MediaPlayerController(
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onRestart: () -> Unit,
    progressInSec: Int,
    modifier: Modifier = Modifier,
    backButtonSize: Int = 35,
    nextButtonSize: Int = 35,
    playButtonSize: Int = 35
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(64.dp)
            .padding(4.dp)
    ) {

        PlayerButtonItem(
            isAudioPlaying = isAudioPlaying,
            onClick = { onStart() },
            buttonSize = playButtonSize
        )

        Spacer(modifier = modifier.size(8.dp))

        BackIconItem(
            onRestart = { onRestart() },
            onPrevious = { onPrevious() },
            buttonSize = backButtonSize,
            progressInSec = progressInSec
        )

        Spacer(modifier = modifier.size(8.dp))

        NextButtonItem(onNext = onNext, buttonSize = nextButtonSize)
    }
}

sealed class MediaPlayerControllerAction {
    object OnNext : MediaPlayerControllerAction()
    object OnPrevious : MediaPlayerControllerAction()
    object OnRestart : MediaPlayerControllerAction()
    class OnStart(val audioFile: AudioFile) : MediaPlayerControllerAction()
    class OnProgressChange(val value: Float) : MediaPlayerControllerAction()
}

@Preview
@Composable
fun PreviewMediaPlayerController() {
    MediaPlayerController(
        isAudioPlaying = false,
        onStart = {},
        onPrevious = {},
        onNext = {},
        onRestart = {},
        progressInSec = 1
    )
}