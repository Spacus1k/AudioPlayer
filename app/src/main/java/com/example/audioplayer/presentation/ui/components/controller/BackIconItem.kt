package com.example.audioplayer.presentation.ui.components.controller

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BackIconItem(
    onRestart: () -> Unit,
    onPrevious: () -> Unit,
    progressInSec: Int,
    border: BorderStroke? = null,
    buttonSize: Int = 35
) {
    Surface(
        shape = CircleShape,
        border = border,
        modifier = Modifier
            .size((buttonSize * 1.5).dp)
            .clip(CircleShape)
            .clickable {
                if (progressInSec < 6) onPrevious() else onRestart()
            },
        contentColor = MaterialTheme.colors.onSurface,
        color = MaterialTheme.colors.secondary.copy(0.1f)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = null,
                modifier = Modifier
                    .size(buttonSize.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBackIconItem() {
    BackIconItem(
        onRestart = {},
        onPrevious = {},
        progressInSec = 4
    )
}