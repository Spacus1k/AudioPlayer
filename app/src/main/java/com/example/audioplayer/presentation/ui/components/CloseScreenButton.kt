package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.audioplayer.R

@Composable
fun CloseScreenButton(
    onBackPress: () -> Unit,
    border: BorderStroke? = null,
    buttonSize: Int = 35,
    contentColor: Color = MaterialTheme.colors.onSurface,
    backgroundColor: Color = MaterialTheme.colors.primary,
    transparencyBackground: Float = 0f
) {
    Surface(
        shape = CircleShape,
        border = border,
        modifier = Modifier
            .size((buttonSize * 1.5).dp)
            .clip(CircleShape)
            .clickable { onBackPress() },
        contentColor = contentColor,
        color = backgroundColor.copy(alpha = transparencyBackground)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_close_screen_24),
                contentDescription = null,
                modifier = Modifier.size(buttonSize.dp)
            )
        }
    }
}