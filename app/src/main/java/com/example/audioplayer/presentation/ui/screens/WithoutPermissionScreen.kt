package com.example.audioplayer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R

@Composable
fun WithoutPermissionScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.not_permissions_text),
                fontSize = 20.sp,
                textAlign = TextAlign.Justify
            )

            Image(
                painter = painterResource(id = R.drawable.ic_broken_cassette_tape_by_vexels),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun WithoutPermissionScreenPreview() {
    WithoutPermissionScreen()
}
