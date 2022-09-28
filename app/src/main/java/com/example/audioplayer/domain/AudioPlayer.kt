package com.example.audioplayer.domain

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.presentation.utils.debugLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import java.lang.reflect.Array.get

class AudioPlayer(private val onAudioCompletion: () -> Unit) : MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {

    private var mediaPlayer: MediaPlayer? = null
    private var _currentAudio = MutableStateFlow<AudioFileDomain?>(null)
    val currentAudio: StateFlow<AudioFileDomain?> get() = _currentAudio

    override fun onPrepared(mp: MediaPlayer?) {
        debugLog("onPrepared")
        mediaPlayer?.apply {
            start()
            setOnCompletionListener(this@AudioPlayer)
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        debugLog("onCompletion")
        onAudioCompletion()
    }

    fun startPlaying(audioFile: AudioFileDomain) {
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer().apply {
            debugLog("start SD")
            setDataSource(audioFile.location)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setOnPreparedListener(this@AudioPlayer)
            prepare()

        }
    }

    suspend fun setCurrentAudio(audioFile: AudioFileDomain?){
        _currentAudio.emit(audioFile)
    }

    fun pausePlaying() {
        debugLog("pausePlaying")
        mediaPlayer?.pause()
    }

    fun continuePlaying() {
        debugLog("continuePlaying")
        mediaPlayer?.apply {
            start()
            setOnCompletionListener(this@AudioPlayer)
        }
    }

    fun stopPlaying() {
        debugLog("stopPlaying")
        mediaPlayer?.stop()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.release()
    }
}