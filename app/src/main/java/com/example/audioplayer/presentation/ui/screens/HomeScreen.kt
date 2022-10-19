package com.example.audioplayer.presentation.ui.screens

import androidx.compose.runtime.Composable
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.components.BottomPlayerAction
import com.example.audioplayer.presentation.ui.components.SearchBarAction
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun HomeScreen(
    viewModel: AudioViewModel,
) {
    AudioListScreen(
        filteredAudioList = viewModel.filteredList,
        onAudioClick = { viewModel.playAudio(it) },
        currentAudioFile = viewModel.currentPlayingAudio.value?.toPresentation(),
        isAudioPlaying = viewModel.isAudioPlaying,
        progress = viewModel.currentAudioProgress.value,
        searchText = viewModel.searchQuery.value,
        allAudioListIsNotEmpty = viewModel.allAudioListIsNotEmpty.value,
        onSearchBarAction = { action ->
            when (action) {
                is SearchBarAction.OnSearchTextChanged -> {
                    viewModel.setSearchQuery(action.query)
                }
                SearchBarAction.OnClearClick -> viewModel.setSearchQuery("")
            }
        },
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