package com.example.audioplayer.presentation.utils

import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.presentation.ui.model.AudioFile

fun AudioFile.toDomain() = AudioFileDomain(
    id = id,
    title = title,
    artist = artist,
    duration = duration,
    displayName = displayName,
    location = location,
)

fun AudioFileDomain.toPresentation() = AudioFile(
    id = id,
    title = title,
    artist = artist,
    duration = duration,
    displayName = displayName,
    location = location,
)

fun List<AudioFileDomain>.toPresentation() = map { audio -> audio.toPresentation() }

fun List<AudioFile>.toDomain() = map { audio -> audio.toDomain() }



