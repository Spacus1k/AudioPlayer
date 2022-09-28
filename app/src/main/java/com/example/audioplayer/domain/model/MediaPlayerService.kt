package com.example.audioplayer.domain.model

import android.os.Bundle
import android.service.media.MediaBrowserService
import android.support.v4.media.MediaBrowserCompat
import androidx.media.MediaBrowserServiceCompat

class MediaPlayerService: MediaBrowserServiceCompat() {

    companion object{
        const val MEDIA_ROOT_ID = "media_root_id"
    }
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        TODO("Not yet implemented")
    }
}