package com.example.audioplayer.presentation.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.audioplayer.presentation.AudioViewModel
import com.example.audioplayer.presentation.ui.components.PermissionAlertDialogTest
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.PermissionManager
import com.example.audioplayer.presentation.utils.PermissionManager.Companion.checkStoragePermission
import com.example.audioplayer.presentation.utils.extensions.collectAsStateLifecycleAware

@Composable
fun HomeScreen(
    activity: Activity,
    audioViewModel: AudioViewModel,
    modifier: Modifier = Modifier
) {
    val audioList: List<AudioFile> by audioViewModel.audioList.collectAsStateLifecycleAware(initial = emptyList())
    val openDialog = remember { mutableStateOf(false) }

    AudioListScreen(
        audioList = audioList,
        onAudioClick = { audio ->
            checkStoragePermission(
                activity = activity,
                rationaleAction = { openDialog.value = true },
                permissionGrantedAction = { audioViewModel.onAudioClick(audio) }
            )
        },
        modifier = modifier
    )

    if (openDialog.value) {
        PermissionAlertDialogTest(
            onConfirm = { PermissionManager.requestStoragePermission(activity) },
            onDismiss = { openDialog.value = false }
        )
    }
}