package com.example.audioplayer.presentation.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.components.AudioFileItem
import com.example.audioplayer.presentation.ui.components.BottomBarPlayer
import com.example.audioplayer.presentation.ui.components.BottomPlayerAction
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
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    onPlayerAction: (BottomPlayerAction) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val animatedHeight by animateDpAsState(
        targetValue = if (currentAudioFile == null) 0.dp
        else BottomSheetScaffoldDefaults.SheetPeekHeight
    )
    BottomSheetScaffold(
        topBar = {
            TopBarWithSearch(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged,
                onClearClick = onClearClick,
            )
        },
        sheetContent = {
            currentAudioFile?.let { audio ->
                BottomBarPlayer(
                    progress = progress,
                    audioFile = audio,
                    isAudioPlaying = isAudioPlaying,
                    onPlayerAction = onPlayerAction
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = animatedHeight
    ) {

        AudioList(
            audioList = audioList,
            onAudioClick = onAudioClick,
            searchText = searchText,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary.copy(alpha = 0.3f))
                //.background(MaterialTheme.colors.surface)
                .padding(top = 4.dp, bottom = animatedHeight)
        )
    }
}

@Composable
fun AudioList(
    audioList: List<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    searchText: String,
    modifier: Modifier = Modifier
) {
    if (audioList.isEmpty()) {
        EmptyAudioListScreen(modifier = Modifier)
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
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
        currentAudioFile = getFakeAudioFile(),
        isAudioPlaying = false,
        progress = 0f,
        searchText = "",
        onSearchTextChanged = {},
        onClearClick = {},
        onPlayerAction = {}
    )
}