package com.example.audioplayer.presentation.ui.components.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.components.AudioInfo
import com.example.audioplayer.presentation.ui.components.AudioSlider
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile

@Composable
fun BottomBarPlayer(
    progress: Float,
    audioFile: AudioFile,
    isAudioPlaying: Boolean,
    onPlayerAction: (MediaPlayerControllerAction) -> Unit,
    onAudioInfoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colors.primary)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    onAudioInfoClick(audioFile.displayName)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            CoverImage(
                uri = audioFile.coverUri, modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            AudioInfo(
                audioFile = audioFile, modifier = Modifier
                    .weight(1f)
            )

            BottomMediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = { onPlayerAction(MediaPlayerControllerAction.OnStart(audioFile)) },
                onNext = { onPlayerAction(MediaPlayerControllerAction.OnNext) }
            )
        }
        AudioSlider(
            progress = progress,
            onProgressChange = { onPlayerAction(MediaPlayerControllerAction.OnProgressChange(it)) }
        )
    }
}

@Preview
@Composable
fun PreviewBottomBarPlayer() {
    BottomBarPlayer(
        progress = 100f,
        audioFile = getFakeAudioFile(),
        isAudioPlaying = true,
        onPlayerAction = {},
        onAudioInfoClick = {}
    )
}