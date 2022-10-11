package com.example.audioplayer.domain.usecases

import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.repository.AudioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAudioListUseCase @Inject constructor(private val repository: AudioRepository) {

    suspend fun getAudioList(): List<AudioFileDomain> = withContext(Dispatchers.IO) {
        repository.getAudioData()
    }
}