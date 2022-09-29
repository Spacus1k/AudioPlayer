package com.example.audioplayer.domain.usecases

import com.example.audioplayer.data.AudioRepositoryImpl
import com.example.audioplayer.domain.repository.AudioRepository
import com.example.audioplayer.domain.model.AudioFileDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAudioListUseCase @Inject constructor(private val repository: AudioRepository) {

    suspend fun getAudioList(): List<AudioFileDomain> = withContext(Dispatchers.IO) {
        repository.getAudioData()
    }
}