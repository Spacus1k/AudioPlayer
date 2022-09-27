package com.example.audioplayer.domain.usecase

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import com.example.audioplayer.domain.model.AudioFileDomain
import com.example.audioplayer.domain.model.AudioStatusDomain
import com.example.audioplayer.presentation.utils.debugLog
import kotlinx.coroutines.flow.MutableStateFlow

class GetAudioListUseCase(private val contentResolver: ContentResolver) {

    companion object {
        private const val START_ID = 0
    }

    fun execute(): MutableStateFlow<List<AudioFileDomain>> {
        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val audioCursor = contentResolver.query(audioUri, null, null, null, null)
        return MutableStateFlow(
            if (audioCursor != null && audioCursor.moveToFirst())
                getAudioList(audioCursor)
        else{
            emptyList()
            }
        )
    }

    private fun getAudioList(cursor: Cursor): MutableList<AudioFileDomain> {
        var id: Int = START_ID
        val audioList = mutableListOf<AudioFileDomain>()
        with(cursor) {
            val audioTitle = getColumnIndex(MediaStore.Audio.Media.TITLE)
            val audioArtist = getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val audioLocation = getColumnIndex(MediaStore.Audio.Media.DATA)
            val audioDuration = getColumnIndex(MediaStore.Audio.Media.DURATION)

            do {
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
                id++
            } while (cursor.moveToNext())
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