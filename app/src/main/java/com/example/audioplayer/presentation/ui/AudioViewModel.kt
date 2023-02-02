package com.example.audioplayer.presentation.ui

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioplayer.domain.media.*
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

    private val allAudioList = mutableListOf<AudioFile>()
    var filteredList = mutableStateListOf<AudioFile>()
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlayBackPosition by mutableStateOf(0L)
    private var updatePosition = true

    val allAudioListIsNotEmpty = mutableStateOf(allAudioList.isNotEmpty())
    private val playbackSate = serviceConnection.plackBackState
    val isAudioPlaying: Boolean
        get() = playbackSate.value?.isPlaying == true

    var searchQuery = mutableStateOf("")

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
    var currentAudioProgressInSec = mutableStateOf(0)

    init {
        viewModelScope.launch {
            allAudioList += getAndFormatAudioData()
            filteredList += getAndFormatAudioData()
            allAudioListIsNotEmpty.value = allAudioList.isNotEmpty()
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
            val title = audio.title.substringBefore(".")
            val artist = audio.artist
            audio.copy(title = title, displayName = displayName, artist = artist).toPresentation()
        }
    }

    fun playAudio(currentAudio: AudioFile) {
        serviceConnection.playAudio(allAudioList.toDomain())
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

    fun setSearchQuery(query: String) {
        searchQuery.value = query
        filterListBySearch(query)
    }

    private fun filterListBySearch(query: String) {
        filteredList =
            allAudioList.filter { audio ->
                audio.title.contains(query, true) || audio.artist.contains(
                    query, true
                )
            }.toMutableStateList()
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

    fun restart() {
        serviceConnection.restart()
    }

    fun skipToNext() {
        serviceConnection.skipToNext()
    }

    fun skipToPrevious() {
        serviceConnection.skipToPrevious()
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

                currentAudioProgressInSec.value =  (currentAudioProgress.value* currentDuration /100000).toInt()
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