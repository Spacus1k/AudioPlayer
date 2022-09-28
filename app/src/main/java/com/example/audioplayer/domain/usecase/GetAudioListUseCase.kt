package com.example.audioplayer.domain.usecase

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.model.AudioStatusDomain
import com.example.audioplayer.presentation.utils.debugLog
import kotlinx.coroutines.flow.MutableStateFlow

class GetAudioListUseCase(private val contentResolver: ContentResolver) {

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

    fun execute(): MutableStateFlow<List<AudioFileDomain>> {

        val audioCursor = contentResolver.query(
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

    private fun getAudioList(cursor: Cursor): MutableList<AudioFileDomain> {
        val audioList = mutableListOf<AudioFileDomain>()
        with(cursor) {
            val audioTitle = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val audioArtist = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val audioLocation = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val audioDuration = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val id = getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)

            while (cursor.moveToNext()) {
                debugLog("id:$id")
                val audioFile = AudioFileDomain(
                    id = id,
                    title = getString(audioTitle),
                    artist = getString(audioArtist),
                    location = getString(audioLocation),
                    duration = getFloat(audioDuration),
                    status = AudioStatusDomain.STOPPED
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