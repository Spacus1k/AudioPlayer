package com.example.audioplayer.data

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.audioplayer.domain.repository.AudioRepository
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.model.AudioStatusDomain
import com.example.audioplayer.presentation.utils.debugLog
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) :
    AudioRepository {

    private val projection: Array<String> = arrayOf(
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.AudioColumns.TITLE,
    )

    private var selectionClause: String? =
        "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?"

    private var selectionArg = arrayOf("1")

    private val sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC"

    fun executeAsFlow(): MutableStateFlow<List<AudioFileDomain>> {

        val audioCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selectionClause,
            selectionArg,
            sortOrder
        )
        return MutableStateFlow(
            if (audioCursor != null && audioCursor.moveToFirst())
                getAudioList(audioCursor)
            else {
                emptyList()
            }
        )
    }

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
            val audioTitle = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val audioArtist = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val audioLocation = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val audioDuration = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val audioDisplayName = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val id = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)

            while (cursor.moveToNext()) {
                debugLog("id:$id")
                val audioFile = AudioFileDomain(
                    id = getLong(id),
                    title = getString(audioTitle),
                    artist = getString(audioArtist),
                    location = getString(audioLocation),
                    duration = getFloat(audioDuration),
                    status = AudioStatusDomain.STOPPED,
                    displayName = getString(audioDisplayName)
                )
                audioList.add(audioFile)
            }
            close()
            printAudioList(audioList)
        }
        return audioList
    }

    private fun printAudioList(audioList: List<AudioFileDomain>) {
        println("Songs count: ${audioList.size}")
        audioList.forEach { audio ->
            println("ID: ${audio.id} ${audio.artist} - ${audio.title}")
            println("Location: ${audio.location}")
        }
    }
}