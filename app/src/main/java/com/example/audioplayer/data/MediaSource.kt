package com.example.audioplayer.data

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import javax.inject.Inject

class MediaSource @Inject constructor(private val repository: AudioRepository) {

    private val onReadyListener: MutableList<OnReadyListener> = mutableListOf()

    var audioMetaData: List<MediaMetadataCompat> = emptyList()

    private var state: AudioSourceState = AudioSourceState.STATE_CREATED
        set(value) {
            if (value == AudioSourceState.STATE_CREATED || value == AudioSourceState.STATE_ERROR) {
                synchronized(onReadyListener) {
                    field = value
                    onReadyListener.forEach { listener ->
                        listener.invoke(isReady)
                    }
                }
            } else {
                field = value
            }
        }

    fun whenReady(listener: OnReadyListener): Boolean {
        return if (state == AudioSourceState.STATE_CREATED || state == AudioSourceState.STATE_INITIALIZING) {
            onReadyListener += listener
            false
        } else {
            listener.invoke(isReady)
            true
        }
    }

    private val isReady: Boolean
        get() = state == AudioSourceState.STATE_INITIALIZED

    suspend fun load() {
        state = AudioSourceState.STATE_INITIALIZING
        val data = repository.getAudioData()
        audioMetaData = data.map { audio ->
            MediaMetadataCompat.Builder()
                .putString(
                    MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                    audio.id.toString()
                ).putString(
                    MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST,
                    audio.artist
                ).putString(
                    MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                    audio.location
                ).putString(
                    MediaMetadataCompat.METADATA_KEY_TITLE,
                    audio.title
                ).putString(
                    MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,
                    audio.displayName,
                ).putLong(
                    MediaMetadataCompat.METADATA_KEY_DURATION,
                    audio.duration.toLong()
                ).build()
        }
        state = AudioSourceState.STATE_INITIALIZED
    }

    fun asMediaSource(dataSource: CacheDataSource.Factory): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()

        audioMetaData.forEach { metaData ->
            val mediaItem = MediaItem.fromUri(
                metaData.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)
            )

            val mediaSource = ProgressiveMediaSource
                .Factory(dataSource)
                .createMediaSource(mediaItem)

            concatenatingMediaSource.addMediaSource(mediaSource)
        }

        return concatenatingMediaSource
    }

    fun asMediaItem() = audioMetaData.map { metaData ->
        val description = MediaDescriptionCompat.Builder()
            .setTitle(metaData.description.title)
            .setMediaId(metaData.description.mediaId)
            .setSubtitle(metaData.description.subtitle)
            .setMediaUri(metaData.description.mediaUri)
            .build()

        MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)

    }.toMutableList()

    fun refresh() {
        onReadyListener.clear()
        state = AudioSourceState.STATE_CREATED
    }
}

enum class AudioSourceState {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR,
}

typealias OnReadyListener = (Boolean) -> Unit