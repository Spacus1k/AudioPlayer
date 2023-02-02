package com.example.audioplayer.presentation.ui.components.controller

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.audioplayer.R

const val MUSIC_COVER_DEFAULT = R.drawable.music_cover_default

@Composable
fun CoverImage(
    uri: Uri,
    @DrawableRes defaultCover: Int = MUSIC_COVER_DEFAULT,
    modifier: Modifier = Modifier
        .height(360.dp)
        .fillMaxWidth()
) {
    AsyncImage(
        model = uri,
        contentDescription = null,
        error = painterResource(id = defaultCover),
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}