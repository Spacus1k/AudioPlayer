package com.example.audioplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.domain.usecase.GetAudioListUseCase
import com.example.audioplayer.domain.usecase.AudioPlayerManager
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.ui.model.AudioStatus
import com.example.audioplayer.presentation.utils.debugLog
import com.example.audioplayer.presentation.utils.toDomain
import com.example.audioplayer.presentation.utils.toPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AudioViewModel(getAudioListUseCase: GetAudioListUseCase) : ViewModel() {

    private val _audioList = MutableStateFlow<List<AudioFile>>(emptyList())
    val audioList: StateFlow<List<AudioFile>> = _audioList

    private val _currentAudio = MutableStateFlow<AudioFile?>(null)
    val currentAudio: StateFlow<AudioFile?> = _currentAudio


    private val audioManager = AudioPlayerManager { onAudioCompletion() }

    val currentAudioProgress = mutableSetOf(0f)

    init {
        viewModelScope.launch {
            audioManager.getCurrentAudio().collect { audio ->
                _currentAudio.value = audio?.toPresentation()
            }
        }

        viewModelScope.launch {
            getAudioListUseCase.executeAsFlow().collect { list ->
                _audioList.value = list.toPresentation()
            }
        }
    }

    fun seekTo(value: Float) {
        //serviceConnection.transportControl.seekTo(
          //  (currentDuration * value / 100f).toLong()
        //)
    }

    fun onAudioClick(audioFile: AudioFile) {
        debugLog("audioFile: ${audioFile.status}")
        viewModelScope.launch {
            if (audioFile == currentAudio.value) {
                audioManager.playAudioFromSD(audioFile.toDomain(), true)
            } else {
                changeStatusCurrentAudio()
                audioManager.playAudioFromSD(audioFile.toDomain(), false)
                currentAudio.collect { audio ->
                    _currentAudio.value = audio
                }
            }
        }
    }

    private fun changeStatusCurrentAudio() {
        viewModelScope.launch {
            _audioList.collect { list ->
                currentAudio.value?.let {
                    debugLog("change status")
                    list[getIndexCurrentAudio()].status = AudioStatus.STOPPED
                    val index = getIndexCurrentAudio()
                    val status = list[index].status.toString()
                    debugLog(status)
                }
            }
        }
    }

    private fun getIndexCurrentAudio(): Int = _audioList.value.indexOf(currentAudio.value)

    private fun onAudioCompletion() {
        debugLog("onAudioCompletion viewModel")
        changeStatusCurrentAudio()
    }
}