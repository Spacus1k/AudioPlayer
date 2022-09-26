package com.example.audioplayer.domain

import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.presentation.utils.debugLog

class AudioPlayer(private val onAudioCompletion: () -> Unit) : MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener {

    private var mediaPlayer: MediaPlayer? = null

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