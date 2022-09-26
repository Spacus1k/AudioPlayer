package com.example.audioplayer.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.ui.model.AudioStatus

@Composable
fun AudioFileItem(
    audioFile: AudioFile,
    onAudioClick: (AudioFile) -> Unit,
    modifier: Modifier,
) {
    Button(onClick = {
        when (audioFile.status) {
            AudioStatus.PLAYING -> audioFile.status = AudioStatus.PAUSED
            else -> audioFile.status = AudioStatus.PLAYING
        }
        onAudioClick(audioFile)
    }) {
        Card(modifier = modifier) {

            Row(modifier = modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                    contentDescription = "Song cover",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                )

                Column(modifier = modifier.padding(8.dp)) {
                    Text(text = audioFile.title, fontSize = 16.sp)
                    Text(text = audioFile.artist)
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioFileItem() {
    val audioFile = AudioFile(id = 0, "Labirint", " Face", "", AudioStatus.STOPPED)
    AudioFileItem(audioFile, {}, Modifier)
}