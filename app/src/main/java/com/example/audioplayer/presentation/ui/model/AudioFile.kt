package com.example.audioplayer.presentation.ui.model

data class AudioFile(
    val id: Int,
    val title: String,
    val artist: String,
    val location: String,
    val displayName: String,
    val duration: Float,
    var status: AudioStatus = AudioStatus.STOPPED
) {
    override fun equals(other: Any?): Boolean =
        other is AudioFile && title == other.title && artist == other.artist

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 32 * result + status.hashCode()
        return result
    }
}

