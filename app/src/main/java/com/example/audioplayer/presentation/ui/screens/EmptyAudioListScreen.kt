package com.example.audioplayer.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.audioplayer.R

@Composable
fun EmptyAudioListScreen(attributeOfEmpty: EmptyAudioListAttribute) {

    val info = getEmptyScreenInfo(attributeOfEmpty = attributeOfEmpty)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = info)
    }
}

@Preview
@Composable
fun PreviewEmptyAllAudioListScreen() {
    EmptyAudioListScreen(EmptyAudioListAttribute.ALL)
}

@Composable
private fun getEmptyScreenInfo(attributeOfEmpty: EmptyAudioListAttribute) = stringResource(
    id = when (attributeOfEmpty) {
        EmptyAudioListAttribute.ALL -> R.string.empty_audio_list
        EmptyAudioListAttribute.BY_SEARCH -> R.string.audio_files_not_found
    }
)

enum class EmptyAudioListAttribute {
    ALL,
    BY_SEARCH
}