package com.example.audioplayer.presentation.ui.components.controller

import androidx.compose.runtime.Composable

@Composable
fun BottomMediaPlayerController(
    isAudioPlaying: Boolean,
    onStart: () -> Unit
) {
    PlayerButtonItem(
        isAudioPlaying = isAudioPlaying,
        onClick = { onStart() },
    )
}