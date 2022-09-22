package com.example.audioplayer.presentation.ui

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

@Composable
fun AudioListScreen(audioList: List<AudioFile>, modifier: Modifier) {

    Scaffold(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            SearchField(onValueChange = {}, modifier = modifier)
            AudioList(modifier = Modifier)
        }
    }
}

@Composable
@Preview
fun PreviewAudioListScreen() {
    AudioListScreen(audioList = getAudioList(), modifier = Modifier)
}

@Composable
fun AudioList(audioList: List<AudioFile> = getAudioList(), modifier: Modifier) {
    LazyColumn( modifier = modifier.padding(vertical = 4.dp)) {
        items(audioList) { audio ->
            AudioFileItem(title = audio.title, artist = audio.artist, modifier = modifier)
        }
    }
}

fun getAudioList(): List<AudioFile> {
    return mutableListOf(
        AudioFile(title = "Рыть", artist = "Face", "D"),
        AudioFile(title = "Подруга подруг", artist = "Face", "D"),
        AudioFile(title = "Юморист", artist = "Face", "D"),
        AudioFile(title = "Калашников", artist = "Face", "D"),
        AudioFile(title = "Монетка", artist = "ЛСП", "D"),
        AudioFile(title = "Рейман", artist = "Face", "D"),
        AudioFile(title = "Бургер", artist = "Face", "D"),
        AudioFile(title = "Гоша Рубчинский", artist = "Face", "D"),
        AudioFile(title = "Рыть", artist = "Face", "D"),
        AudioFile(title = "Рыть", artist = "Face", "D"),
        AudioFile(title = "Рыть", artist = "Face", "D"),
        AudioFile(title = "Рыть", artist = "Face", "D"),
        AudioFile(title = "Рыть", artist = "Face", "D"))
}