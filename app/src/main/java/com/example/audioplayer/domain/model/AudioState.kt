package com.example.audioplayer.domain.model

import kotlinx.coroutines.flow.MutableStateFlow

sealed class AudioState {

    class Success(audioFlow: MutableStateFlow<List<AudioFileDomain>>) : AudioState()

    class Error(message: String) : AudioState()
}