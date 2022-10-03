package com.example.audioplayer.presentation.ui.screens

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.audioplayer.presentation.ui.AudioViewModel
import com.example.audioplayer.presentation.ui.components.RationaleDialog
import com.example.audioplayer.presentation.utils.PermissionManager
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun HomeScreen(
    activity: Activity,
    modifier: Modifier = Modifier,
    viewModel: AudioViewModel,
) {
    val isOpenedRationaleDialog = remember { mutableStateOf(false) }
    AudioListScreen(
        audioList = viewModel.audioList,
        onAudioClick = { audio ->
            PermissionManager.checkStoragePermission(activity = activity,
                permissionGrantedAction = { viewModel.playAudio(audio) },
                rationaleAction = { isOpenedRationaleDialog.value = true })
        },
        modifier = modifier,
        currentAudioFile = viewModel.currentPlayingAudio.value?.toPresentation(),
        onProgressChange = { viewModel.seekTo(it) },
        isAudioPlaying = viewModel.isAudioPlaying,
        onNext = { viewModel.skipToNext() },
        onStart = { viewModel.playAudio(it) },
        progress = viewModel.currentAudioProgress.value
    )

    if (isOpenedRationaleDialog.value) {
        RationaleDialog(
            onConfirm = {
                PermissionManager.requestStoragePermission(activity)
            },
            onDismiss = { isOpenedRationaleDialog.value = false }
        )
        isOpenedRationaleDialog.value = false
    }
}