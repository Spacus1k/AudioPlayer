package com.example.audioplayer.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.audioplayer.presentation.ui.model.AudioFile
import com.example.audioplayer.presentation.utils.getFakeAudioFile

@Composable
fun BottomBarPlayer(
    progress: Float,
    audioFile: AudioFile,
    isAudioPlaying: Boolean,
    onPlayerAction: (BottomPlayerAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colors.primary.copy(alpha = 0.3f))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AudioInfo(audioFile = audioFile, modifier = Modifier.weight(1f))

            MediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = { onPlayerAction(BottomPlayerAction.OnStart(audioFile)) },
                onNext = { onPlayerAction(BottomPlayerAction.OnNext) },
                onRestart = { onPlayerAction(BottomPlayerAction.OnRestart) },
                onPrevious = { onPlayerAction(BottomPlayerAction.OnPrevious) },
            )
        }
        AudioSlider(
            progress = progress,
            onProgressChange = { onPlayerAction(BottomPlayerAction.OnProgressChange(it)) }
        )
    }
}

@Composable
fun AudioSlider(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float> = 0f..100f
) {
    var localSliderValue by remember {
        mutableStateOf(progress)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isInteracting = isPressed || isDragged

    val sliderValue by derivedStateOf {
        if (isInteracting) {
            localSliderValue
        } else {
            progress
        }
    }

    Slider(
        valueRange = range,
        value = sliderValue,
        onValueChange = {
            localSliderValue = it
        },
        interactionSource = interactionSource,
        onValueChangeFinished = {
            onProgressChange(sliderValue)
        }
    )
}

@Composable
fun AudioInfo(audioFile: AudioFile, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = audioFile.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = audioFile.artist,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomBarPlayer() {
    BottomBarPlayer(
        progress = 100f,
        audioFile = getFakeAudioFile(),
        isAudioPlaying = true,
        onPlayerAction = {}
    )
}

sealed class BottomPlayerAction {
    object OnNext : BottomPlayerAction()
    object OnPrevious : BottomPlayerAction()
    object OnRestart : BottomPlayerAction()
    class OnStart(val audioFile: AudioFile) : BottomPlayerAction()
    class OnProgressChange(val value: Float) : BottomPlayerAction()
}