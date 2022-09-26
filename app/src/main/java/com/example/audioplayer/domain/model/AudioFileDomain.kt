package com.example.audioplayer.domain.model

data class AudioFileDomain(
    val id: Int,
    val title: String,
    val artist: String,
    val location: String,
    var status: AudioStatusDomain = AudioStatusDomain.STOPPED
) {
    override fun equals(other: Any?): Boolean =
        other is AudioFileDomain && title == other.title && artist == other.artist

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 32 * result + status.hashCode()
        return result
    }
}