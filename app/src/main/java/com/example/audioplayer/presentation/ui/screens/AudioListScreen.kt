package com.example.audioplayer.presentation.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.components.AudioFileItem
import com.example.audioplayer.presentation.ui.components.BottomBarPlayer
import com.example.audioplayer.presentation.ui.components.TopBarWithSearch
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioList

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AudioListScreen(
    audioList: List<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    currentAudioFile: AudioFile?,
    isAudioPlaying: Boolean,
    progress: Float,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onRestart: () -> Unit,
    onStart: (AudioFile) -> Unit,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearClick: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val animatedHeight by animateDpAsState(
        targetValue = if (currentAudioFile == null) 0.dp
        else BottomSheetScaffoldDefaults.SheetPeekHeight
    )
    BottomSheetScaffold(
        sheetContent = {
            currentAudioFile?.let { audio ->
                BottomBarPlayer(
                    progress = progress,
                    onProgressChange = onProgressChange,
                    audioFile = audio,
                    isAudioPlaying = isAudioPlaying,
                    onNext = { onNext() },
                    onPrevious = { onPrevious() },
                    onRestart = { onRestart() },
                    onStart = { onStart(audio) },
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = animatedHeight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary.copy(alpha = 0.3f))
        ) {
            TopBarWithSearch(
                searchText = searchText,
                modifier = modifier,
                onSearchTextChanged = onSearchTextChanged,
                onClearClick = onClearClick,
            )
            AudioList(
                audioList = audioList,
                animatedHeight = animatedHeight,
                onAudioClick = onAudioClick,
                modifier = modifier,
                searchText
            )
        }
    }
}

@Composable
fun AudioList(
    audioList: List<AudioFile>,
    animatedHeight: Dp,
    onAudioClick: (AudioFile) -> Unit,
    modifier: Modifier,
    searchText: String
) {
    if (audioList.isEmpty()) {
        EmptyAudioListScreen(modifier = modifier)
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .background(MaterialTheme.colors.surface)
                .padding(top = 4.dp, bottom = animatedHeight)
        ) {
            val filteredList = audioList.filter { audio ->
                audio.displayName.contains(searchText, true) || audio.artist.contains(
                    searchText, true
                )
            }
            items(filteredList) { audio ->
                AudioFileItem(
                    audioFile = audio,
                    onAudioClick = { onAudioClick(audio) },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioListScreen() {
    AudioListScreen(
        audioList = getFakeAudioList(),
        onAudioClick = {},
        modifier = Modifier,
        currentAudioFile = getFakeAudioFile(),
        onProgressChange = {},
        onStart = {},
        onNext = {},
        onPrevious = {},
        isAudioPlaying = false,
        progress = 0f,
        searchText = "",
        onSearchTextChanged = {},
        onClearClick = {},
        onRestart = {}
    )
}