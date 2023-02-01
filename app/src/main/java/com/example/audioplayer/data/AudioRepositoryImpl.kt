package com.example.audioplayer.data

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.repository.AudioRepository
import com.example.audioplayer.presentation.utils.debugLog
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) :
    AudioRepository {

    companion object {
        const val UNKNOWN_ARTIST_STUB = "<unknown>"
        const val UNKNOWN_ARTIST = "Unknown Artist"
    }

    private val projection: Array<String> = arrayOf(
        MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Albums.ALBUM_ID,
        MediaStore.Audio.Media.TITLE,
    )

    private var selectionClause: String? =
        "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?"

    private var selectionArg = arrayOf("1")

    private val sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC"

    override suspend fun getAudioData(): List<AudioFileDomain> {
        val audioCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selectionClause,
            selectionArg,
            sortOrder
        )
        return if (audioCursor != null && audioCursor.moveToFirst())
            getAudioList(audioCursor)
        else {
            emptyList()
        }
    }

    private fun getAudioList(cursor: Cursor): MutableList<AudioFileDomain> {
        val audioList = mutableListOf<AudioFileDomain>()
        with(cursor) {
            val audioTitle = getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val audioArtist = getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val audioLocation = getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val audioDuration = getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val audioDisplayName = getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val id = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)

            while (cursor.moveToNext()) {
                debugLog("id:$id")
                val songId = getLong(id)
                val uri: Uri = Uri.parse("content://media/external/audio/media/$songId/albumart");
                val audioFile = AudioFileDomain(
                    id = songId,
                    title = getString(audioTitle),
                    artist = checkUnknownArtist(getString(audioArtist)),
                    location = getString(audioLocation),
                    duration = getFloat(audioDuration),
                    displayName = getString(audioDisplayName),
                    coverUri = uri
                )
                audioList.add(audioFile)
            }
            close()
            printAudioList(audioList)
        }
        return audioList
    }

    private fun checkUnknownArtist(artist: String) =
        if (artist.contains(UNKNOWN_ARTIST_STUB)) UNKNOWN_ARTIST else artist

    private fun printAudioList(audioList: List<AudioFileDomain>) {
        println("Songs count: ${audioList.size}")
        audioList.forEach { audio ->
            println("ID: ${audio.id} ${audio.artist} - ${audio.title}")
            println("Location: ${audio.location}")
            println("Uri: ${audio.coverUri}")
            println("_____________________________________________")
        }
    }
}