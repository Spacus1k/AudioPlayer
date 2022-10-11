package com.example.audioplayer.di

import com.example.audioplayer.data.AudioRepositoryImpl
import com.example.audioplayer.domain.repository.AudioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindAudioRepository(repository: AudioRepositoryImpl): AudioRepository
}