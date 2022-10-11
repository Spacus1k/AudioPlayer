package com.example.audioplayer.presentation.utils

import com.example.audioplayer.presentation.ui.model.AudioFile

fun getFakeAudioList(): List<AudioFile> {
    return mutableListOf<AudioFile>().apply {
        repeat(15) {
            add(getFakeAudioFile())
        }
    }
}

fun getFakeAudioFile() = AudioFile(
    id = 0,
    title = "Пачка сигарет",
    artist = "КИНО",
    "D",
    displayName = "Пачка сигарет",
    0f
)
