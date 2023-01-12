package com.example.audioplayer.presentation.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.components.*
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AudioListScreen(
    filteredAudioList: List<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    currentAudioFile: AudioFile?,
    isAudioPlaying: Boolean,
    progress: Float,
    searchText: String,
    allAudioListIsNotEmpty: Boolean,
    onSearchBarAction: (SearchBarAction) -> Unit,
    onPlayerAction: (MediaPlayerControllerAction) -> Unit,
    onAudioInfoClick: (String) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val animatedHeight by animateDpAsState(
        targetValue = if (currentAudioFile == null) 0.dp
        else BottomSheetScaffoldDefaults.SheetPeekHeight
    )
    val focusManager = LocalFocusManager.current

    BottomSheetScaffold(
        topBar = {
            TopBarWithSearch(
                searchText = searchText,
                onSearchBarAction = onSearchBarAction,
                focusManager = focusManager
            )
        },
        sheetContent = {
            currentAudioFile?.let { audio ->
                BottomBarPlayer(
                    progress = progress,
                    audioFile = audio,
                    isAudioPlaying = isAudioPlaying,
                    onPlayerAction = onPlayerAction,
                    onAudioInfoClick = onAudioInfoClick
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = animatedHeight
    ) {

        AudioList(
            filteredAudioList = filteredAudioList,
            onAudioClick = {
                onAudioClick(it)
                focusManager.clearFocus()
            },
            allAudioListIsNotEmpty = allAudioListIsNotEmpty,
            currentPlayingAudioId = currentAudioFile?.id,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary.copy(alpha = 0.3f))
                .padding(bottom = animatedHeight)
        )
    }
}

@Composable
fun AudioList(
    filteredAudioList: List<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    allAudioListIsNotEmpty: Boolean,
    currentPlayingAudioId: Long?,
    modifier: Modifier = Modifier
) {
    when {
        filteredAudioList.isEmpty() -> EmptyAudioListScreen(EmptyAudioListAttribute.BY_SEARCH)
        !allAudioListIsNotEmpty -> EmptyAudioListScreen(EmptyAudioListAttribute.ALL)
        else -> {
            LazyColumn(modifier = modifier) {
                items(filteredAudioList) { audio ->
                    AudioFileItem(
                        audioFile = audio,
                        onAudioClick = { onAudioClick(audio) },
                        isPlayingCurrentAudio = audio.id == currentPlayingAudioId
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioListScreen() {
    AudioListScreen(
        filteredAudioList = getFakeAudioList(),
        onAudioClick = {},
        currentAudioFile = getFakeAudioFile(),
        isAudioPlaying = false,
        progress = 0f,
        searchText = "",
        allAudioListIsNotEmpty = true,
        onPlayerAction = {},
        onSearchBarAction = {},
        onAudioInfoClick = {}
    )
}