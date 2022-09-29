package com.example.audioplayer.presentation.ui.screens

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.audioplayer.presentation.ui.SecondAudioViewModel
import com.example.audioplayer.presentation.ui.components.PermissionAlertDialogTest
import com.example.audioplayer.presentation.utils.PermissionManager
import com.example.audioplayer.presentation.utils.toPresentation

@Composable
fun HomeScreen(
    activity: Activity,
    modifier: Modifier = Modifier,
    secondViewModel: SecondAudioViewModel,
) {
    val openDialog = remember { mutableStateOf(false) }

    AudioListScreen(
        audioList = secondViewModel.audioList,
        onAudioClick = { secondViewModel.playAudio(it) },
        modifier = modifier,
        currentAudioFile = secondViewModel.currentPlayingAudio.value?.toPresentation(),
        onProgressChange = { secondViewModel.seekTo(it) },
        isAudioPlaying = secondViewModel.isAudioPlaying,
        onNext = { secondViewModel.skipToNext() },
        onStart = { secondViewModel.playAudio(it) },
        progress = secondViewModel.currentAudioProgress.value
    )

    if (openDialog.value) {
        PermissionAlertDialogTest(
            onConfirm = { PermissionManager.requestStoragePermission(activity) },
            onDismiss = { openDialog.value = false }
        )
    }
}