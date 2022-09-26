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

@Composable
fun AudioListScreen(
    audioList: List<AudioFile>,
    onAudioClick: (AudioFile) -> Unit,
    modifier: Modifier
) {
    Scaffold(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Log.e("TAG", "${audioList.forEach { println(it.title) }}")
            SearchField(onValueChange = {}, modifier = modifier)
            AudioList(audioList, onAudioClick, modifier = Modifier)
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
        AudioFile(id = 0, title = "Рыть", artist = "Face", "D"),
        AudioFile(id = 0, title = "Подруга подруг", artist = "Face", "D"),
        AudioFile(id = 0, title = "Юморист", artist = "Face", "D"),
        AudioFile(id = 0, title = "Калашников", artist = "Face", "D"),
        AudioFile(id = 0, title = "Монетка", artist = "ЛСП", "D"),
        AudioFile(id = 0, title = "Рейман", artist = "Face", "D"),
        AudioFile(id = 0, title = "Бургер", artist = "Face", "D"),
        AudioFile(id = 0, title = "Гоша Рубчинский", artist = "Face", "D")
    )
}