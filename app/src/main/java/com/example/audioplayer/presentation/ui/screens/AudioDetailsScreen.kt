package com.example.audioplayer.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.components.AudioSliderWithTime
import com.example.audioplayer.presentation.ui.components.DetailsAudioInfo
import com.example.audioplayer.presentation.ui.components.controller.CoverImage
import com.example.audioplayer.presentation.ui.components.controller.MediaPlayerController
import com.example.audioplayer.presentation.ui.components.controller.MediaPlayerControllerAction
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile

@Composable
fun AudioDetailsScreen(
    playingAudioFile: AudioFile,
    progress: Float,
    progressInSec: Int,
    isAudioPlaying: Boolean,
    onPlayerAction: (MediaPlayerControllerAction) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CoverImage(uri = playingAudioFile.coverUri)
            }

            Spacer(modifier = Modifier.size(5.dp))
            DetailsAudioInfo(audioFile = playingAudioFile)

            AudioSliderWithTime(
                progress = progress,
                duration = playingAudioFile.duration,
                onProgressChange = { onPlayerAction(MediaPlayerControllerAction.OnProgressChange(it)) })

            MediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = { onPlayerAction(MediaPlayerControllerAction.OnStart(playingAudioFile)) },
                onNext = { onPlayerAction(MediaPlayerControllerAction.OnNext) },
                onRestart = { onPlayerAction(MediaPlayerControllerAction.OnRestart) },
                onPrevious = { onPlayerAction(MediaPlayerControllerAction.OnPrevious) },
                modifier = Modifier.size(15.dp),
                progressInSec = progressInSec
            )
        }
    }
}

@Composable
@Preview
fun AudioDetailsPreview() {
    AudioDetailsScreen(
        isAudioPlaying = false,
        playingAudioFile = getFakeAudioFile(),
        progress = 1f,
        onPlayerAction = {},
        progressInSec = 1,
    )
}