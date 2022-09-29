package com.example.audioplayer.domain.media

import android.os.SystemClock
import android.support.v4.media.session.PlaybackStateCompat

val PlaybackStateCompat.isPlaying: Boolean
    get() = state == PlaybackStateCompat.STATE_BUFFERING
            || state == PlaybackStateCompat.STATE_PLAYING

val PlaybackStateCompat.currentPosition: Long
    get() = if (state == PlaybackStateCompat.STATE_PLAYING) {
        val timeDelta = SystemClock.elapsedRealtime() - lastPositionUpdateTime
        (position + (timeDelta * playbackSpeed)).toLong()
    } else {
        position
    }