package com.example.audioplayer.presentation.ui

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.domain.media.Constants
import com.example.audioplayer.domain.media.MediaPlayerService
import com.example.audioplayer.domain.media.MediaPlayerServiceConnection
import com.example.audioplayer.domain.media.currentPosition
import com.example.audioplayer.domain.media.isPlaying
import com.example.audioplayer.domain.usecases.GetAudioListUseCase
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.toDomain
import com.example.audioplayer.presentation.utils.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val getAudioListUseCase: GetAudioListUseCase,
    serviceConnection: MediaPlayerServiceConnection
) : ViewModel() {

    var audioList = mutableStateListOf<AudioFile>()
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlayBackPosition by mutableStateOf(0L)
    private var updatePosition = true

    private val playbackSate = serviceConnection.plackBackState
    val isAudioPlaying: Boolean
        get() = playbackSate.value?.isPlaying == true

    private var subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
        }
    }

    private val serviceConnection = serviceConnection.also {
        updatePlayback()
    }

    val currentDuration: Long
        get() = MediaPlayerService.currentDuration

    var currentAudioProgress = mutableStateOf(0f)

    init {
        viewModelScope.launch {
            audioList += getAndFormatAudioData()
            isConnected.collect {
                if (it) {
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.plackBackState.value?.apply {
                        currentPlayBackPosition = position
                    }
                    serviceConnection.subscribe(rootMediaId, subscriptionCallback)
                }
            }
        }
    }

    private suspend fun getAndFormatAudioData(): List<AudioFile> {
        return getAudioListUseCase.getAudioList().map { audio ->
            val displayName = audio.displayName.substringBefore(".")
            val artist = audio.artist
            audio.copy(displayName = displayName, artist = artist).toPresentation()
        }
    }

    fun  refreshAudioList() {
        audioList = mutableStateListOf()
        viewModelScope.launch {
            audioList += getAndFormatAudioData()
            isConnected.collect {
                if (it) {
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.plackBackState.value?.apply {
                        currentPlayBackPosition = position
                    }
                    serviceConnection.subscribe(rootMediaId, subscriptionCallback)
                }
            }
        }
    }

    fun playAudio(currentAudio: AudioFile) {
        serviceConnection.playAudio(audioList.toDomain())
        if (currentAudio.id == currentPlayingAudio.value?.id) {
            if (isAudioPlaying) {
                serviceConnection.transportControl.pause()
            } else {
                serviceConnection.transportControl.play()
            }
        } else {
            serviceConnection.transportControl.playFromMediaId(
                currentAudio.id.toString(),
                null
            )
        }
    }

    fun stopPlayback() {
        serviceConnection.transportControl.stop()
    }

    fun startForward() {
        serviceConnection.fastForward()
    }

    fun rewind() {
        serviceConnection.rewind()
    }

    fun skipToNext() {
        serviceConnection.skipToNext()
    }

    fun seekTo(value: Float) {
        serviceConnection.transportControl.seekTo(
            (currentDuration * value / 100f).toLong()
        )
    }

    private fun updatePlayback() {
        viewModelScope.launch {
            val position = playbackSate.value?.currentPosition ?: 0
            if (currentPlayBackPosition != position) {
                currentPlayBackPosition = position
            }
            if (currentDuration > 0) {
                currentAudioProgress.value =
                    (currentPlayBackPosition.toFloat() / currentDuration.toFloat() * 100f)
            }

            delay(Constants.PLAYBACK_UPDATE_INTERVAL)
            if (updatePosition) {
                updatePlayback()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unSubscribe(
            Constants.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})
        updatePosition = false
    }
}