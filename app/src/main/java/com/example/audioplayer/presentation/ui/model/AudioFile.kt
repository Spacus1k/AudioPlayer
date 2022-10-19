package com.example.audioplayer.presentation.ui.model

data class AudioFile(
    val id: Long,
    val title: String,
    val artist: String,
    val location: String,
    val displayName: String,
    val duration: Float,
) {
    override fun equals(other: Any?): Boolean =
        other is AudioFile && title == other.title && artist == other.artist

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 32 * result + displayName.hashCode()
        return result
    }
}