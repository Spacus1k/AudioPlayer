package com.example.audioplayer.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.components.MediaPlayerControllerAction
import com.example.audioplayer.presentation.ui.components.SearchBarAction
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun HomeScreen(
    filteredAudioList: SnapshotStateList<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    currentAudioFile: AudioFile?,
    isAudioPlaying: Boolean,
    progress: Float,
    searchText: String,
    allAudioListIsNotEmpty: Boolean,
    onAudioInfoClick: (String) -> Unit,
    onSearchBarAction: (SearchBarAction) -> Unit,
    onPlayerAction: (MediaPlayerControllerAction) -> Unit,
) {
    AudioListScreen(
        filteredAudioList = filteredAudioList,
        onAudioClick = onAudioClick,
        currentAudioFile =currentAudioFile,
        isAudioPlaying = isAudioPlaying,
        progress = progress,
        searchText = searchText,
        allAudioListIsNotEmpty = allAudioListIsNotEmpty,
        onAudioInfoClick = onAudioInfoClick,
        onSearchBarAction = onSearchBarAction,
        onPlayerAction = onPlayerAction
    )
}