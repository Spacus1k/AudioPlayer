package com.example.audioplayer.presentation.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.audioplayer.R

@Composable
fun RationaleDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.permission_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.permission_dialog_text)) },
        onDismissRequest = { onDismiss() },
        confirmButton = { TextButton(onClick = { onConfirm() }) { Text(text = stringResource(id = R.string.ok)) } },
        dismissButton = { TextButton(onClick = { onDismiss() }) { Text(text = stringResource(id = R.string.cancel)) } },
    )
}

@Composable
fun AttentionDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.permission_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.permission_dialog_text)) },
        onDismissRequest = { onDismiss() },
        confirmButton = { TextButton(onClick = { onConfirm() }) { Text(text = stringResource(id = R.string.ok)) } },
        dismissButton = { TextButton(onClick = { onDismiss() }) { Text(text = stringResource(id = R.string.cancel)) } },
    )
}