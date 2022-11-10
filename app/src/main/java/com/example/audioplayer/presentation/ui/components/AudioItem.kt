package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.audioplayer.presentation.theme.CyanBlue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile

@Composable
fun AudioFileItem(
    audioFile: AudioFile,
    onAudioClick: (id: Long) -> Unit,
    isPlayingCurrentAudio: Boolean
) {
    val itemBackgroundColor = if (isPlayingCurrentAudio) {
       CyanBlue
    } else {
        MaterialTheme.colors.surface
    }
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = itemBackgroundColor
        ),
        onClick = { onAudioClick(audioFile.id) }) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                contentDescription = "Song cover",
                modifier = Modifier
                    .size(50.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = audioFile.title,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = audioFile.artist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.4f)
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioFileItem() {
    AudioFileItem(audioFile = getFakeAudioFile(), onAudioClick = {}, isPlayingCurrentAudio = false)
}