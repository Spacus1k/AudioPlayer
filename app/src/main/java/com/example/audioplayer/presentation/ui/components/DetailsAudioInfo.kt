package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.audioplayer.presentation.ui.model.AudioFile

@Composable
fun DetailsAudioInfo(
    audioFile: AudioFile,
    titleTextSize: TextUnit = 20.sp,
    artistTextSize: TextUnit = 15.sp
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = audioFile.title,
            fontWeight = FontWeight.SemiBold,
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