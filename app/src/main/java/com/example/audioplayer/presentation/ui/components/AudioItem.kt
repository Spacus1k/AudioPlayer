package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R
import com.example.audioplayer.presentation.ui.model.AudioFile

@Composable
fun AudioFileItem(
    audioFile: AudioFile,
    onAudioClick: (id: Long) -> Unit,
    modifier: Modifier,
) {
    Card(modifier = modifier) {
        Button(
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            onClick = { onAudioClick(audioFile.id) }) {

            Row(modifier = modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                    contentDescription = "Song cover",
                    modifier = Modifier
                        .size(50.dp)
                )

                Column(modifier = modifier.padding(8.dp)) {
                    Text(text = audioFile.displayName, fontSize = 16.sp)
                    Text(text = audioFile.artist)
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioFileItem() {
    val audioFile = AudioFile(
        id = 0,
        title = "Labirint",
        artist = " Face",
        location = "",
        duration = 0f,
        displayName = " Lab"
    )
    AudioFileItem(audioFile, {}, Modifier)
}