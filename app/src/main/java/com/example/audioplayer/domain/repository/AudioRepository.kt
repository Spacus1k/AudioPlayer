package com.example.audioplayer.domain.repository

import com.example.audioplayer.domain.model.AudioFileDomain

interface AudioRepository {

    suspend fun getAudioData(): List<AudioFileDomain>
}