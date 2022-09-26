package com.example.audioplayer.domain.usecase

import android.content.ContentResolver
import android.provider.MediaStore
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.ui.model.AudioStatus
import com.example.audioplayer.presentation.utils.debugLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GetAudioListUseCase(private val contentResolver: ContentResolver) {

    companion object{
        private const val START_ID = 0
    }
    fun execute(): StateFlow<List<AudioFile>> {
        val audioList = mutableListOf<AudioFile>()
        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val audioCursor = contentResolver.query(audioUri, null, null, null, null)

        if (audioCursor != null && audioCursor.moveToFirst()) {
            var id: Int  = START_ID
            val audioTitle = audioCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val audioArtist = audioCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val audioLocation = audioCursor.getColumnIndex(MediaStore.Audio.Media.DATA)

            do {
                debugLog("id:$id")
                val audioFile = AudioFile(
                    id = id,
                    title = audioCursor.getString(audioTitle),
                    artist = audioCursor.getString(audioArtist),
                    location = audioCursor.getString(audioLocation),
                    status = AudioStatus.STOPPED
                )

                audioList.add(audioFile)
                id++
            } while (audioCursor.moveToNext())
        }
        println("Songs count: ${audioList.size}")
        audioList.forEach { audio ->
            println("ID: ${audio.id} ${audio.artist} - ${audio.title}")
            println("Location: ${audio.location}")
        }
        audioCursor?.close()
        return MutableStateFlow(audioList)
    }
}