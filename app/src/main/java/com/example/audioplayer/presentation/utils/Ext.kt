package com.example.audioplayer.presentation.utils

import android.view.WindowInsets
import android.view.WindowInsets.Type.ime
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.view.WindowInsetsCompat.Type.ime
import java.util.concurrent.ThreadLocalRandom.current

//fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
//    var isFocused by remember { mutableStateOf(false) }
//    var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }
//    if (isFocused) {
//        val imeIsVisible = WindowInsets.ime.isVisible
//        val focusManager = LocalFocusManager.current
//        LaunchedEffect(imeIsVisible) {
//            if (imeIsVisible) {
//                keyboardAppearedSinceLastFocused = true
//            } else if (keyboardAppearedSinceLastFocused) {
//                focusManager.clearFocus()
//            }
//        }
//    }
//    onFocusEvent {
//        if (isFocused != it.isFocused) {
//            isFocused = it.isFocused
//            if (isFocused) {
//                keyboardAppearedSinceLastFocused = false
//            }
//        }
//    }
//}