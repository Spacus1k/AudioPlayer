package com.example.audioplayer.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.audioplayer.presentation.AudioViewModel
import com.example.audioplayer.presentation.utils.extensions.collectAsStateLifecycleAware
import com.example.audioplayer.presentation.ui.model.AudioFile

@Composable
fun HomeScreen(
    audioViewModel: AudioViewModel,
    modifier: Modifier = Modifier
) {
    val audioList: List<AudioFile> by audioViewModel.audioList.collectAsStateLifecycleAware(initial = emptyList())

    AudioListScreen(
        audioList = audioList,
        onAudioClick = { audio ->
            audioViewModel.onAudioClick(audio)
        },
        modifier = modifier
    )
}