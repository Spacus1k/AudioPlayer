package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
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
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(64.dp)
            .padding(4.dp)
    ) {

        Icon(
            imageVector = Icons.Default.SkipPrevious,
            contentDescription = null,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onPress = { onRestart() },
                    onDoubleTap = { onPrevious() },
                )
            }
        )
        Spacer(modifier = modifier.size(8.dp))

        PlayerIconItem(
            icon = if (isAudioPlaying) Icons.Default.Pause
            else Icons.Default.PlayArrow,
            onClick = { onStart() },
        )
        Spacer(modifier = modifier.size(8.dp))

        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = null,
            modifier = Modifier.clickable { onNext() }
        )
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
        onRestart = {}
    )
}