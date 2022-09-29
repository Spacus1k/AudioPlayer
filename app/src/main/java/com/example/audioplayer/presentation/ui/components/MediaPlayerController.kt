package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MediaPlayerController(
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(56.dp)
            .padding(4.dp)
    ) {

        PlayerIconItem(
            icon = if (isAudioPlaying) Icons.Default.Pause
            else Icons.Default.PlayArrow,
            onClick = { onStart() },
            modifier = modifier
        )
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = null,
            modifier = modifier
                .clickable { onNext() }
        )
    }
}

@Preview
@Composable
fun PreviewMediaPlayerController() {
    MediaPlayerController(
        isAudioPlaying = false,
        onStart = {},
        onNext = {},
        modifier = Modifier
    )
}