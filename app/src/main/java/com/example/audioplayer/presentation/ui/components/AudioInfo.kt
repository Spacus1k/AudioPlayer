package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile

@Composable
fun AudioInfo(
    audioFile: AudioFile,
    modifier: Modifier = Modifier,
    titleTextSize: TextUnit = 16.sp,
    artistTextSize: TextUnit = 15.sp
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = audioFile.title,
                fontWeight = FontWeight.Medium,
                fontSize = titleTextSize,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = audioFile.artist,
                fontWeight = FontWeight.Normal,
                fontSize = artistTextSize,
                color = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.4f),
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
@Preview
fun PreviewAudioInfo() {
    AudioInfo(getFakeAudioFile())
}