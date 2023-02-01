package com.example.audioplayer.presentation.ui.components.controller

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R
import com.example.audioplayer.presentation.utils.loadPicture

const val MUSIC_COVER_DEFAULT = R.drawable.music_cover_default

@Composable
fun CoverImage(
    uri: Uri,
    @DrawableRes defaultCover: Int = MUSIC_COVER_DEFAULT,
    modifier: Modifier = Modifier
        .height(360.dp)
        .fillMaxWidth()
) {
    val image = loadPicture(uri = uri,defaultCover  ).value
    image?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            contentDescription = "Song cover",
            modifier = modifier
        )
    }
}