package com.example.audioplayer.presentation.utils

import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.model.AudioStatusDomain
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.ui.model.AudioStatus

fun AudioFile.toDomain() = AudioFileDomain(
    id = id,
    title = title,
    artist = artist,
    location = location,
    status = status.toDomain()
)

fun AudioFileDomain.toPresentation() = AudioFile(
    id = id,
    title = title,
    artist = artist,
    location = location,
    status = status.toPresentation()
)

fun AudioStatus.toDomain() = when (this) {
    AudioStatus.STOPPED -> AudioStatusDomain.STOPPED
    AudioStatus.PLAYING -> AudioStatusDomain.PLAYING
    AudioStatus.PAUSED -> AudioStatusDomain.PAUSED
}

fun AudioStatusDomain.toPresentation() = when (this) {
    AudioStatusDomain.STOPPED -> AudioStatus.STOPPED
    AudioStatusDomain.PLAYING -> AudioStatus.PLAYING
    AudioStatusDomain.PAUSED -> AudioStatus.PAUSED
}