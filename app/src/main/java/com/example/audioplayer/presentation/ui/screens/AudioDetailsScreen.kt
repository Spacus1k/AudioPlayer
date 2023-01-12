package com.example.audioplayer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R
import com.example.audioplayer.presentation.ui.components.AudioInfo
import com.example.audioplayer.presentation.ui.components.AudioSlider
import com.example.audioplayer.presentation.ui.components.MediaPlayerController
import com.example.audioplayer.presentation.ui.components.MediaPlayerControllerAction
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile

@Composable
fun AudioDetailsScreen(
    playingAudioFile: AudioFile,
    progress: Float,
    isAudioPlaying: Boolean,
    onPlayerAction: (MediaPlayerControllerAction) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(50.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                contentDescription = "Song cover",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )

            AudioInfo(audioFile = playingAudioFile)

            AudioSlider(progress = progress, onProgressChange = {})

            MediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = { onPlayerAction(MediaPlayerControllerAction.OnStart(playingAudioFile)) },
                onNext = { onPlayerAction(MediaPlayerControllerAction.OnNext) },
                onRestart = { onPlayerAction(MediaPlayerControllerAction.OnRestart) },
                onPrevious = { onPlayerAction(MediaPlayerControllerAction.OnPrevious) },
                modifier = Modifier.size(30.dp)
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
        onPlayerAction = {}
        )
}