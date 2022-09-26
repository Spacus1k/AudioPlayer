package com.example.audioplayer.domain.usecase

import com.example.audioplayer.domain.AudioPlayer
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.model.AudioStatusDomain.*

class PlayAudioFileUseCase(onAudioCompletion: () -> Unit) {

    private var audioPlayer: AudioPlayer = AudioPlayer(onAudioCompletion)

    fun playAudioFromSD(audioFile: AudioFileDomain, continuePlaying: Boolean) =
        with(audioPlayer) {
            when (audioFile.status) {
                PLAYING -> if (continuePlaying) continuePlaying() else startPlaying(
                    audioFile
                )
                PAUSED -> pausePlaying()
                STOPPED -> stopPlaying()
            }
        }
}