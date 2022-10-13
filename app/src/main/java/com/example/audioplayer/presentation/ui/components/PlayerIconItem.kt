package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PlayerIconItem(
    icon: ImageVector,
    border: BorderStroke? = null,
    onClick: () -> Unit,
) {
    Surface(
        shape = CircleShape,
        border = border,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() },
        contentColor = MaterialTheme.colors.onSurface,
        color = MaterialTheme.colors.surface
    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
    }
}