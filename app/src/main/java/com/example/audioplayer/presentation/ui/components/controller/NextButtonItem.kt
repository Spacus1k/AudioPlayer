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
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NextButtonItem(
    onNext: () -> Unit,
    border: BorderStroke? = null,
    buttonSize: Int = 35,
    contentColor: Color = MaterialTheme.colors.onSurface,
    backgroundColor: Color = MaterialTheme.colors.secondary.copy(0.1f)
) {
    Surface(
        shape = CircleShape,
        border = border,
        modifier = Modifier
            .size((buttonSize * 1.5).dp)
            .clip(CircleShape)
            .clickable { onNext() },
        contentColor = contentColor,
        color = backgroundColor
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
                contentDescription = null,
                modifier = Modifier
                    .size(buttonSize.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewNextIconItem() {
    NextButtonItem(
        onNext = {}
    )
}