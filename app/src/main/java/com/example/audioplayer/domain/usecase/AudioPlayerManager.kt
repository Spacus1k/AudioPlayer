package com.example.audioplayer.domain.usecase

import com.example.audioplayer.domain.AudioPlayer
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.model.AudioStatusDomain.*

class AudioPlayerManager(onAudioCompletion: () -> Unit) {

    private var audioPlayer: AudioPlayer = AudioPlayer(onAudioCompletion)

    suspend fun playAudioFromSD(audioFile: AudioFileDomain, continuePlaying: Boolean) =
        with(audioPlayer) {
            when (audioFile.status) {
                PLAYING -> {
                    if (continuePlaying) {
                        continuePlaying()
                    } else {
                        startPlaying(audioFile)
                        setCurrentAudio(audioFile)
                    }
                }
                PAUSED -> pausePlaying()
                STOPPED -> {
                    stopPlaying()
                    setCurrentAudio(null)
                }
            }
        }

    fun getCurrentAudio() = audioPlayer.currentAudio
}