package com.example.audioplayer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.domain.usecase.GetAudioListUseCase
import com.example.audioplayer.domain.usecase.PlayAudioFileUseCase
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
    init {
        viewModelScope.launch { getAudioListUseCase.execute().collect{ list ->
                _audioList.value = list.toPresentation()
            }
        }
    }

    private var currentAudio: AudioFile? = null

    private val playAudioFileUseCase = PlayAudioFileUseCase { onAudioCompletion() }

    fun onAudioClick(audioFile: AudioFile) {
        debugLog("audioFile: ${audioFile.status}")
        if (audioFile == currentAudio) {
            playAudioFileUseCase.playAudioFromSD(audioFile.toDomain(), true)
        } else {
            changeStatusCurrentAudio()
            currentAudio = audioFile
            playAudioFileUseCase.playAudioFromSD(audioFile.toDomain(), false)
        }
    }

    private fun changeStatusCurrentAudio() {
        viewModelScope.launch {
            _audioList.collect { list ->
                currentAudio?.let {
                    debugLog("change status")
                    list[getIndexCurrentAudio()].status = AudioStatus.STOPPED
                    val index = getIndexCurrentAudio()
                    val status = list[index].status.toString()
                    debugLog(status)
                }
            }
        }
    }

    private fun getIndexCurrentAudio(): Int = _audioList.value.indexOf(currentAudio)

    private fun onAudioCompletion() {
        debugLog("onAudioCompletion viewModel")
        changeStatusCurrentAudio()
    }
}