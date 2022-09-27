package com.example.audioplayer.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.components.AudioFileItem
import com.example.audioplayer.presentation.ui.components.SearchField
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.EmptyAudioListScreen

@Composable
fun AudioListScreen(
    audioList: List<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    modifier: Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            Log.e("TAG", "${audioList.forEach { println(it.title) }}")
            SearchField(onValueChange = {}, modifier = modifier)
            if (audioList.isEmpty()) {
                EmptyAudioListScreen(modifier = modifier)
            } else {
                AudioList(audioList, onAudioClick, modifier = modifier)
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioListScreen() {
    AudioListScreen(audioList = getAudioList(), onAudioClick = {}, modifier = Modifier)
}

@Composable
fun AudioList(audioList: List<AudioFile>, onAudioClick: (AudioFile) -> Unit, modifier: Modifier) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(audioList) { audio ->
            AudioFileItem(audio, onAudioClick, modifier = modifier)
        }
    }
}

fun getAudioList(): List<AudioFile> {
    return mutableListOf(
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D", 0f),
    )
}