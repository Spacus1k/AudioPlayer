package com.example.audioplayer.presentation.utils

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
fun EmptyAudioListScreen(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.empty_audio_list))
    }
}

@Preview
@Composable
fun PreviewEmptyAudioListScreen() {
    EmptyAudioListScreen(modifier = Modifier)
}