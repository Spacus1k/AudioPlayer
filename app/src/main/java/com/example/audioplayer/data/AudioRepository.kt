package com.example.audioplayer.data

import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.usecase.GetAudioListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AudioRepository(private val getAudioListUseCase: GetAudioListUseCase) {

    suspend fun getAudioData(): List<AudioFileDomain> = withContext(Dispatchers.IO){
        getAudioListUseCase.execute()
    }
}