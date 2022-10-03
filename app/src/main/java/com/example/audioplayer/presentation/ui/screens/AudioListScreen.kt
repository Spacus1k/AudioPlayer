package com.example.audioplayer.presentation.ui.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.example.audioplayer.presentation.ui.components.SearchField
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.EmptyAudioListScreen
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
    onStart: (AudioFile) -> Unit,
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
                    onStart = { onStart(audio) },
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = animatedHeight) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primaryVariant)
        ) {
            SearchField(onValueChange = {}, modifier = modifier)
            if (audioList.isEmpty()) {
                EmptyAudioListScreen(modifier = modifier)
            } else {
                LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                    items(audioList) { audio ->
                        AudioFileItem(
                            audioFile = audio,
                            onAudioClick = { onAudioClick(audio) },
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioListScreen() {
    AudioListScreen(
        audioList = getFakeAudioList(), onAudioClick = {}, modifier = Modifier,
        currentAudioFile = getFakeAudioFile(),
        onProgressChange = {},
        onStart = {},
        onNext = {},
        isAudioPlaying = false,
        progress = 0f
    )
}