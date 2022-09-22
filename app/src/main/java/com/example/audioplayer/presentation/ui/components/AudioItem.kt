package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R

@Composable
fun AudioFileItem(title: String, artist: String, modifier: Modifier) {
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
                Text(text = title, fontSize = 16.sp)
                Text(text = artist)
            }
        }
    }
}

@Composable
@Preview
fun PreviewAudioFileItem() {
    AudioFileItem(title = "Лабиринт", artist = "Face", Modifier)
}