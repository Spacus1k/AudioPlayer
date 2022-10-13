package com.example.audioplayer.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.components.BottomPlayerAction
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun HomeScreen(
    viewModel: AudioViewModel,
) {
    AudioListScreen(
        audioList = viewModel.audioList,
        onAudioClick = { viewModel.playAudio(it) },
        currentAudioFile = viewModel.currentPlayingAudio.value?.toPresentation(),
        isAudioPlaying = viewModel.isAudioPlaying,
        progress = viewModel.currentAudioProgress.value,
        onSearchTextChanged = { viewModel.searchQuery.value = it },
        searchText = viewModel.searchQuery.value,
        onClearClick = { viewModel.searchQuery.value = "" },
        onPlayerAction = { action ->
            when (action) {
                is BottomPlayerAction.OnStart -> viewModel.playAudio(action.audioFile)
                is BottomPlayerAction.OnProgressChange -> viewModel.seekTo(action.value)
                BottomPlayerAction.OnNext -> viewModel.skipToNext()
                BottomPlayerAction.OnPrevious -> viewModel.skipToPrevious()
                BottomPlayerAction.OnRestart -> viewModel.restart()
            }
        }
    )
}