package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsAudioInfo(
    audioFile: AudioFile,
    titleTextSize: TextUnit = 20.sp,
    artistTextSize: TextUnit = 15.sp
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = audioFile.title,
            fontWeight = FontWeight.SemiBold,
            fontSize = titleTextSize,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            modifier = Modifier.basicMarquee()
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
@Composable
@Preview
fun PreviewDetailsAudioInfo(){
    DetailsAudioInfo(getFakeAudioFile())
}