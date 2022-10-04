package com.example.audioplayer.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AudioViewModel,
) {
    AudioListScreen(
        audioList = viewModel.audioList,
        onAudioClick = { viewModel.playAudio(it) },
        modifier = modifier,
        currentAudioFile = viewModel.currentPlayingAudio.value?.toPresentation(),
        onProgressChange = { viewModel.seekTo(it) },
        isAudioPlaying = viewModel.isAudioPlaying,
        onNext = { viewModel.skipToNext() },
        onStart = { viewModel.playAudio(it) },
        progress = viewModel.currentAudioProgress.value
    )
}